package com.andronicus.med_manager.editprofile;

import android.net.Uri;

/**
 * Created by andronicus on 4/11/2018.
 */

public interface EditProfileContract {

    interface View{
        void showSuccessMessage();

        void showErrorMessage();

        void showProgressDialog();

        void dismissProgressDialog();
    }
    interface Presenter{

        void updateProfile(Uri uri, String displayName);
    }
}
