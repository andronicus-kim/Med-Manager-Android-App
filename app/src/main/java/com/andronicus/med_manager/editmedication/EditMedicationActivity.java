package com.andronicus.med_manager.editmedication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.util.ActivityUtil;

public class EditMedicationActivity extends AppCompatActivity {

    private static final String TAG = "EditMedicationActivity";


    /*
   * Helper method to start this activity
   * */
    public static Intent newIntent(@NonNull Context context){
        return new Intent(context,EditMedicationActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medication);
        try{
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null){
            fragment = EditMedicationFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),R.id.fragment_container,fragment);
        }
    }
}
