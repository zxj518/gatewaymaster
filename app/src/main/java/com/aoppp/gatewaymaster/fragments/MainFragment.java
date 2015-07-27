package com.aoppp.gatewaymaster.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aoppp.gatewaymaster.R;
import com.aoppp.gatewaymaster.base.BaseFragment;
import com.aoppp.gatewaymaster.ui.MainActivity;
import com.aoppp.gatewaymaster.ui.MemoryCleanActivity;
import com.aoppp.gatewaymaster.widget.circleprogress.ArcProgress;
import com.aoppp.gatewaysdk.MessageConst;
import com.aoppp.gatewaysdk.domain.CheckManager;
import com.aoppp.gatewaysdk.domain.CheckResult;
import com.aoppp.gatewaysdk.domain.DeviceProfile;
import com.aoppp.gatewaysdk.domain.RouterCheckConf;
import com.aoppp.webviewdom.internal.WebViewJs;
import com.umeng.update.UmengUpdateAgent;


import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainFragment extends BaseFragment {

    @InjectView(R.id.arc_store)
    ArcProgress arcStore;

//    @InjectView(R.id.arc_process)
//    ArcProgress arcProcess;

    @InjectView(R.id.capacity)
    TextView capacity;

    Context mContext;
    @InjectView(R.id.webView)
    WebViewJs webView;

//    private Timer timer;
//    private Timer timer2;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        mContext = getActivity();
        CheckManager.instance().loadCheckConf(mContext);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        fillData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        UmengUpdateAgent.update(getActivity());
    }

    private void fillData() {
        arcStore.setProgress(0);

    }

    @OnClick(R.id.card1)
    void speedUp() {
        onCheckStart();
        //startActivity(MemoryCleanActivity.class);
    }


    @OnClick(R.id.card2)
    void rubbishClean() {
        //startActivity(RubbishCleanActivity.class);
    }


    @OnClick(R.id.card3)
    void AutoStartManage() {
        ///startActivity(AutoStartManageActivity.class);
    }

    @OnClick(R.id.card4)
    void SoftwareManage() {
        //startActivity(SoftwareManageActivity.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @Override
    public void onDestroy() {
//        timer.cancel();
//        timer2.cancel();
        super.onDestroy();
    }

    private void onCheckStart() {

        RouterCheckConf conf = RouterCheckConf.loadConf(this.getActivity());
        capacity.setText("正在检测..");

        new Thread(new Runnable() {
            @Override
            public void run() {
                final DeviceProfile profile = CheckManager.instance().getDefaultProfile();
                long timeBegin = System.currentTimeMillis();
                try {
                    CheckManager.instance().willCheckDevice(profile);
                    sendOutputMessage(MessageConst.CHECKING_OUTPUT, "正在登录", 3);
                    CheckManager.instance().login();
                    sendOutputMessage(MessageConst.CHECKING_OUTPUT, "开始执行检查..", 5);
                    final CheckResult result = CheckManager.instance().check(MainFragment.this.getActivity(), MainFragment.this.mHandler, webView);
                    sendOutputMessage(MessageConst.CHECKING_OUTPUT, "完成检查..", 90);
                    sendOutputMessage(MessageConst.CHECKING_OUTPUT, "注销登录..", 95);
                    CheckManager.instance().logout();
                    sendOutputMessage(MessageConst.CHECKING_OUTPUT, "注销完成", 98);


                } catch (Exception ex) {
                    Log.e(MainFragment.this.getClass().getCanonicalName(), "ERROR", ex);
                    CheckResult result = CheckManager.getLastCheckResult();
                    long timeUsed = System.currentTimeMillis() - timeBegin;
                    if (result == null) {
                        result = new CheckResult(-1, ex.getMessage(), CheckManager.instance().getAllCheckItems(), timeUsed);
                        CheckManager.setLastCheckResult(result);
                    } else {
                        result.setErrorCode(-1);
                        result.setErrorMessage(ex.getMessage());
                    }
                } finally {
                    CheckManager.instance().clear(getActivity());
                }


                sendOutputMessage(MessageConst.CHECK_DONE, "", 100);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        checkCompleted(CheckManager.getLastCheckResult());
                    }
                });
            }
        }).start();
    }

    public void checkCompleted(CheckResult result) {

        if (result.getErrorCode() < 0) {
            AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage(result.getErrorMessage()).create();
            dialog.show();
        } else {
            Intent intent = new Intent();
            intent.putExtra("result", result.getErrorCode());
            intent.setClass(getActivity(), MemoryCleanActivity.class);
            startActivityForResult(intent, 2);
        }
    }

    public void sendOutputMessage(int code, String msgInfo, int process) {
        Message msg = new Message();
        //msg.set
        msg.what = code;
        Bundle bundle = new Bundle();
        bundle.putCharSequence("msg", msgInfo);
        bundle.putInt("process", process);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            //更新UI
            switch (msg.what) {
                case MessageConst.CHECK_DONE:
                    capacity.setText("检测完成");
                    arcStore.setProgress(100);
                    break;
                case MessageConst.CHECKING_OUTPUT:
                    capacity.setText(msg.getData().getCharSequence("msg"));
                    arcStore.setProgress(msg.getData().getInt("process"));
                    break;
            }
            super.handleMessage(msg);
        }

        ;
    };
}
