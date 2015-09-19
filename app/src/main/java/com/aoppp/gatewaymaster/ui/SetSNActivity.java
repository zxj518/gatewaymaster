package com.aoppp.gatewaymaster.ui;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.aoppp.gatewaymaster.R;
import com.aoppp.gatewaymaster.base.BaseSwipeBackActivity;
import com.aoppp.gatewaymaster.utils.SystemBarTintManager;
import com.aoppp.gatewaymaster.utils.UIElementsHelper;
import com.aoppp.gatewaymaster.utils.Utils;
import com.aoppp.gatewaysdk.domain.DeviceProvider;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import butterknife.InjectView;
import butterknife.OnClick;


public class SetSNActivity extends BaseSwipeBackActivity implements OnDismissCallback {


    @InjectView(R.id.setsn_provider_option)
    Button providerButton;

    @InjectView(R.id.setsn_txt_sn)
    EditText txtSN;


    @InjectView(R.id.setsn_submit_btn)
    Button commitButton;

    ProgressDialog progressDialog;

    private String[] providerArray;

    private DeviceProvider currentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_activity_set_sn);
        setContentView(R.layout.activity_set_sn);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        providerArray = mContext.getResources().getStringArray(
                R.array.providers);
        providerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProviderDialog();
            }
        });
        progressDialog = Utils.showProgressDialog(this, "正在设置SN..");
        initData();
    }

    private void initData(){

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


    @OnClick(R.id.setsn_submit_btn)
    public void onSubmit() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void showProviderDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.pick_privoder)
                .setItems(R.array.providers, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        String provider_pre = getResources().getString(R.string.login_device_provider);
                        String provider = which > -1 ? providerArray[which] : "";
                        providerButton.setText(provider_pre + "   " + provider);
                        currentProvider = DeviceProvider.toProviderByCnName(provider);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
