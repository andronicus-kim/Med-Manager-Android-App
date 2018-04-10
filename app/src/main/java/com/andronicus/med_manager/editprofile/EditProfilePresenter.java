package com.andronicus.med_manager.editprofile;

import android.support.annotation.NonNull;

import com.andronicus.med_manager.data.User;
import com.andronicus.med_manager.data.UsersDataSource;
import com.andronicus.med_manager.data.UsersRepository;

import javax.inject.Inject;

/**
 * Created by andronicus on 4/11/2018.
 */

public class EditProfilePresenter implements EditProfileContract.Presenter {

    private UsersRepository mUsersRepository;
    private EditProfileContract.View mView;

    @Inject
    EditProfilePresenter(@NonNull UsersRepository usersRepository,
                         @NonNull EditProfileContract.View view){
       this.mUsersRepository = usersRepository;
       this.mView = view;
    }

    @Override
    public void updateProfile(String uri,String displayName) {
        mUsersRepository.updateProfile(uri,displayName,new UsersDataSource.UserProfileUpdate() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }
        });
    }
}
