package com.andronicus.med_manager.appconfig;

import android.content.Context;

import com.andronicus.med_manager.editprofile.EditProfileModule;
import com.andronicus.med_manager.util.ApplicationScope;

import dagger.Component;

/**
 * Created by andronicus on 4/11/2018.
 */
@ApplicationScope
@Component(modules = {ApplicationModule.class,EditProfileModule.class})
public interface ApplicationComponent {

    Context exposeContext();
}
