package com.andronicus.med_manager.data;

import android.net.Uri;

/**
 * Created by andronicus on 4/11/2018.
 */

public interface UsersDataSource {
    interface UserProfileUpdate{
        void onSuccess();

        void onFailure();
    }
    void updateProfile(Uri uri, String displayName, UserProfileUpdate profileUpdate);
}
