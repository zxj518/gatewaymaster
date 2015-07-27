package com.aoppp.gatewaymaster.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aoppp.gatewaymaster.R;
import com.aoppp.gatewaymaster.base.BaseActivity;

/**
 * Created by snowzou on 15/7/27.
 */
public class LoginActivity extends BaseActivity {

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button)this.findViewById(R.id.login_commit_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onLogin();
            }
        });
    }

    private void onLogin(){
        startActivity(MainActivity.class);
    }
}
