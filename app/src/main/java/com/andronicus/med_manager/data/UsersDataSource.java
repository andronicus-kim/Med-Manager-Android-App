package com.andronicus.med_manager.data;

/**
 * Created by andronicus on 4/11/2018.
 */

public interface UsersDataSource {
    interface UserProfileUpdate{
        void onSuccess();

        void onFailure();
    }
    void updateProfile(String uri,String displayName,UserProfileUpdate profileUpdate);
}
