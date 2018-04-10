package com.andronicus.med_manager.appconfig;

import android.content.Context;
import android.support.annotation.NonNull;

import com.andronicus.med_manager.util.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andronicus on 4/11/2018.
 */
@Module
public class ApplicationModule {

    private Context mContext;

    ApplicationModule(@NonNull Context context){
        this.mContext = context;
    }

    @Provides
    @ApplicationScope
    Context provideApplicationContext(){
        return mContext;
    }
}
