package com.andronicus.med_manager.data;

import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * Created by andronicus on 4/11/2018.
 */

public class UsersRepository implements UsersDataSource{

    private UsersRemoteDataSource mUsersRemoteDataSource;

    @Inject
    UsersRepository(@NonNull UsersRemoteDataSource usersRemoteDataSource){
        this.mUsersRemoteDataSource = usersRemoteDataSource;
    }

    @Override
    public void updateProfile(String uri, String displayName, UserProfileUpdate profileUpdate) {

    }
}
