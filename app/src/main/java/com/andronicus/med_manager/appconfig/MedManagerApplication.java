package com.andronicus.med_manager.appconfig;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by andronicus on 3/23/2018.
 */
public class MedManagerApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        /*
        * Enable firebase offline capability
        * */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}