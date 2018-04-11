package com.andronicus.med_manager.editprofile;

import com.andronicus.med_manager.appconfig.ApplicationComponent;
import com.andronicus.med_manager.util.ActivityScope;

import dagger.Component;

/**
 * Created by andronicus on 4/11/2018.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = EditProfileModule.class)
public interface EditProfileComponent {

    void inject(EditProfileActivity activity);
}
