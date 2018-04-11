package com.andronicus.med_manager;

import android.net.Uri;

import com.andronicus.med_manager.data.UsersDataSource;
import com.andronicus.med_manager.data.UsersRepository;
import com.andronicus.med_manager.editprofile.EditProfileContract;
import com.andronicus.med_manager.editprofile.EditProfilePresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Created by andronicus on 4/11/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class EditProfilePresenterTest {

    @Mock
    UsersRepository mUsersRepository;

    @Mock
    EditProfileContract.View mView;

    @Mock
    UsersDataSource.UserProfileUpdate mUserProfileUpdate;

    EditProfilePresenter mPresenter;

    @Mock
    Uri mUri;


    @Before
    public void setUp(){
        mPresenter = new EditProfilePresenter(mUsersRepository,mView);
    }
    @Test
    public void shouldUpdatingDialog(){
        String displayName = "name";
        doNothing().when(mUsersRepository).updateProfile(mUri,displayName,mUserProfileUpdate);

        mPresenter.updateProfile(mUri,displayName);

        verify(mView).showProgressDialog();

    }
}
