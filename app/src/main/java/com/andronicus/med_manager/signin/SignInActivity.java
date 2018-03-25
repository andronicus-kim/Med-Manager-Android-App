package com.andronicus.med_manager.signin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.util.ActivityUtil;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        /*
        * Reference the layout that contains the fragment in
        * Sign In activity layout
        * */
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null){
            //If the layout contains no fragment then create a new instance
            fragment = SignInFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),R.id.fragment_container,fragment);
        }
    }
}
