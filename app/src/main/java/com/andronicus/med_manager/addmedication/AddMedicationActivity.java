package com.andronicus.med_manager.addmedication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.util.ActivityUtil;

public class AddMedicationActivity extends AppCompatActivity {
    private static final String TAG = "AddMedicationActivity";

    /*
   * Helper method to start this activity
   * */
    public static Intent newIntent(@NonNull Context context){
        return new Intent(context,AddMedicationActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        try{
            getSupportActionBar().setHomeButtonEnabled(true);
        }catch (NullPointerException e){
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null){
            fragment = AddMedicationFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),R.id.fragment_container,fragment);
        }
    }
}
