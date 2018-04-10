package com.andronicus.med_manager.addmedication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.util.ActivityUtil;

public class AddMedicationActivity extends AppCompatActivity {
    private static final String TAG = "AddMedicationActivity";
    public static final String USER_ID = "USER_ID";

    private String mUserId;

    /*
   * Helper method to start this activity
   * */
    public static Intent newIntent(@NonNull Context context,String userId){
        Intent intent =  new Intent(context,AddMedicationActivity.class);
        intent.putExtra(USER_ID,userId);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null){
            fragment = AddMedicationFragment.newInstance(getIntent().getStringExtra(USER_ID));
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),R.id.fragment_container,fragment);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
