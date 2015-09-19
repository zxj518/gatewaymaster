package com.aoppp.gatewaymaster.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aoppp.gatewaymaster.R;
import com.aoppp.gatewaymaster.base.BaseActivity;
import com.aoppp.gatewaymaster.utils.Utils;
import com.aoppp.gatewaysdk.domain.CheckManager;
import com.aoppp.gatewaysdk.domain.DeviceProfile;
import com.aoppp.gatewaysdk.domain.DeviceProvider;

/**
 * Created by snowzou on 15/7/27.
 */
public class LoginActivity extends BaseActivity {

    private Button loginButton;

    private Button providerButton;

    private EditText txtPwd;

    private EditText txtIP;

    private EditText txtUser;

    ProgressDialog progressDialog;

    private String[] providerArray;

    private DeviceProvider currentProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        providerArray = mContext.getResources().getStringArray(
                R.array.providers);

        txtIP = (EditText)this.findViewById(R.id.login_device_ip);
        txtPwd = (EditText)this.findViewById(R.id.login_password);
        txtUser = (EditText)this.findViewById(R.id.login_username);

        loginButton = (Button)this.findViewById(R.id.login_commit_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onLogin();
            }
        });

        providerButton = (Button)this.findViewById(R.id.login_provider_option);
        providerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProviderDialog();
            }
        });
        progressDialog = Utils.showProgressDialog(this, "正在登录..");
        initData();

        getActionBar().setHomeButtonEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void initData(){
        txtIP.setText("192.168.1.1");
        txtUser.setText("telecomadmin");
        txtPwd.setText("telecomadmin");
        providerButton.setFocusable(true);
        providerButton.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if(id==R.id.action_setSN){
            onSetSN();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void onLogin(){
        if(!validate()){
            return ;
        }




        DeviceProfile profile = new DeviceProfile(currentProvider.getSimpleName(), txtUser.getText().toString(),
                txtPwd.getText().toString(), txtIP.getText().toString());

        new Thread(new LoginTask(profile)).start();
        progressDialog.show();
    }

    private boolean validate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("请提供更多信息");
        if(currentProvider==null){
            builder.setMessage("请选择厂商信息");
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }
        if(txtIP.getText().length()==0){
            builder.setMessage("IP地址不能为空");
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }

        if(txtUser.getText().length()==0){
            builder.setMessage("管理员账户不能为空");
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }

        if(txtPwd.getText().length()==0){
            builder.setMessage("管理员密码不能为空");
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }
        return true;
    }


    private  class LoginTask implements Runnable {

        DeviceProfile loginProfile;

        public LoginTask(DeviceProfile profile){
            this.loginProfile = profile;
        }
        @Override
        public void run() {
            try {
                CheckManager.instance().loadCheckConf(LoginActivity.this);
                CheckManager.instance().willCheckDevice(loginProfile);
                CheckManager.instance().login();

                CheckManager.instance().logout();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        done(true);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        done(false);
                    }
                });
            }
        }

        private  void done(boolean success){
            progressDialog.hide();
            if(success) {
                startActivity(MainActivity.class);
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("登录失败,请检查提供的信息是否正确");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    };

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


    private void onSetSN(){
        Intent intent = new Intent();

        intent.setClass(this, SetSNActivity.class);
        startActivityForResult(intent, 2);
    }
}
