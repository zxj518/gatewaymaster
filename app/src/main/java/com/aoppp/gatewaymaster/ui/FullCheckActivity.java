package com.aoppp.gatewaymaster.ui;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aoppp.gatewaymaster.R;
import com.aoppp.gatewaymaster.adapter.CheckResultAdapter;
import com.aoppp.gatewaymaster.base.BaseSwipeBackActivity;
import com.aoppp.gatewaymaster.utils.SystemBarTintManager;
import com.aoppp.gatewaymaster.utils.UIElementsHelper;
import com.aoppp.gatewaysdk.domain.CheckItem;
import com.aoppp.gatewaysdk.domain.CheckManager;
import com.aoppp.gatewaysdk.domain.CheckResult;
import com.aoppp.gatewaysdk.domain.DeviceBasicInfo;
import com.john.waveview.WaveView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;


public class FullCheckActivity extends BaseSwipeBackActivity implements OnDismissCallback {

    ActionBar ab;

    @InjectView(R.id.listview)
    ListView mListView;

    @InjectView(R.id.wave_view)
    WaveView mwaveView;

    @InjectView(R.id.textDeviceName)
    TextView txtDeviceName;

    @InjectView(R.id.textDeviceSN)
    TextView txtDeviceSN;

    @InjectView(R.id.header)
    RelativeLayout header;
//    List<AppProcessInfo> mAppProcessInfos = new ArrayList<>();
    CheckResultAdapter checkResultAdapter;


    @InjectView(R.id.bottom_lin)
    LinearLayout bottom_lin;

    @InjectView(R.id.progressBar)
    View mProgressBar;
//    @InjectView(R.id.progressBarText)
//    TextView mProgressBarText;

    @InjectView(R.id.clear_button)
    Button clearButton;
    private static final int INITIAL_DELAY_MILLIS = 300;
    SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_activity_check_reuslt);
        setContentView(R.layout.activity_full_check);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //  applyKitKatTranslucency();
        CheckResult checkResult = CheckManager.getLastCheckResult();
        List<CheckItem> checkItems = checkResult.getCheckItemList();
        checkResultAdapter = new CheckResultAdapter(mContext, checkItems);
        mListView.setAdapter(checkResultAdapter);

        DeviceBasicInfo basicInfo = CheckManager.instance().getGateway().getBasicInfo();
        txtDeviceName.setText(checkResult.getDeviceProfile().getProvider() + " " + basicInfo.getDeviceType());
        txtDeviceSN.setText("SN:" + basicInfo.getDeviceSN());

//TODO 下面的按钮
//        int footerHeight = mContext.getResources().getDimensionPixelSize(R.dimen.footer_height);

//        mListView.setOnScrollListener(new QuickReturnListViewOnScrollListener(QuickReturnViewType.FOOTER, null, 0, bottom_lin, footerHeight));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Apply KitKat specific translucency.
     */
    private void applyKitKatTranslucency() {

        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setNavigationBarTintEnabled(true);
            // mTintManager.setTintColor(0xF00099CC);

            mTintManager.setTintDrawable(UIElementsHelper
                    .getGeneralActionBarBackground(this));

            getActionBar().setBackgroundDrawable(
                    UIElementsHelper.getGeneralActionBarBackground(this));

        }

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onDismiss(@NonNull ViewGroup viewGroup, @NonNull int[] ints) {

    }


    @OnClick(R.id.clear_button)
    public void onClickClear() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
