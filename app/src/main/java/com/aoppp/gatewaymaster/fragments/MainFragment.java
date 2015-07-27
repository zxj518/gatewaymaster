package com.aoppp.gatewaymaster.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aoppp.gatewaymaster.R;
import com.aoppp.gatewaymaster.base.BaseFragment;
import com.aoppp.gatewaymaster.widget.circleprogress.ArcProgress;
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

//    private Timer timer;
//    private Timer timer2;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        mContext = getActivity();

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
        // TODO Auto-generated method stub
//        timer = null;
//        timer2 = null;
//        timer = new Timer();
//        timer2 = new Timer();
//
//
//        long l = AppUtil.getAvailMemory(mContext);
//        long y = AppUtil.getTotalMemory(mContext);
//        final double x = (((y - l) / (double) y) * 100);
//        //   arcProcess.setProgress((int) x);
//
//        arcProcess.setProgress(0);
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                        if (arcProcess.getProgress() >= (int) x) {
//                            timer.cancel();
//                        } else {
//                            arcProcess.setProgress(arcProcess.getProgress() + 1);
//                        }
//
//                    }
//                });
//            }
//        }, 50, 20);
//
//        SDCardInfo mSDCardInfo = StorageUtil.getSDCardInfo();
//        SDCardInfo mSystemInfo = StorageUtil.getSystemSpaceInfo(mContext);
//
//        long nAvailaBlock;
//        long TotalBlocks;
//        if (mSDCardInfo != null) {
//            nAvailaBlock = mSDCardInfo.free + mSystemInfo.free;
//            TotalBlocks = mSDCardInfo.total + mSystemInfo.total;
//        } else {
//            nAvailaBlock = mSystemInfo.free;
//            TotalBlocks = mSystemInfo.total;
//        }
//
//        final double percentStore = (((TotalBlocks - nAvailaBlock) / (double) TotalBlocks) * 100);

        capacity.setText("");
        arcStore.setProgress(0);

//        timer2.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                        if (arcStore.getProgress() >= (int) percentStore) {
//                            timer2.cancel();
//                        } else {
//                            arcStore.setProgress(arcStore.getProgress() + 1);
//                        }
//
//                    }
//                });
//            }
//        }, 50, 20);


    }

    @OnClick(R.id.card1)
    void speedUp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i< 100 ;i++){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int process = i;
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            arcStore.setProgress(process);
                        }
                    });

                }
            }
        }).start();

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
}
