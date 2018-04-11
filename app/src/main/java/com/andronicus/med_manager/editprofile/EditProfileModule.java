package com.andronicus.med_manager.editprofile;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andronicus on 4/11/2018.
 */
@Module
public class EditProfileModule {

    private EditProfileContract.View mView;

    EditProfileModule(EditProfileContract.View view){
        this.mView = view;
    }

    @Provides
    EditProfileContract.View provideView(){
        return mView;
    }
}
