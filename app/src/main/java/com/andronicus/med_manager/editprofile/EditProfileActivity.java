package com.andronicus.med_manager.editprofile;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.andronicus.med_manager.R;

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = "EditProfileActivity";

    /*
    * Helper method to start this activity
    * */
    public static Intent newIntent(@NonNull Context context){
        return new Intent(context,EditProfileActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        try{
            getSupportActionBar().setHomeButtonEnabled(true);
        }catch (NullPointerException e){
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }
    }
}
