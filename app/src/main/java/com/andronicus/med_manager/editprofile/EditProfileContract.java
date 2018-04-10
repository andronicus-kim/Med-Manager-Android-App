package com.andronicus.med_manager.editprofile;

/**
 * Created by andronicus on 4/11/2018.
 */

public interface EditProfileContract {

    interface View{

    }
    interface Presenter{

        void updateProfile(String uri, String displayName);
    }
}
