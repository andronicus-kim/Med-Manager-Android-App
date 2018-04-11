package com.andronicus.med_manager.editprofile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.appconfig.MedManagerApplication;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EditProfileActivity extends AppCompatActivity implements EditProfileContract.View{
    private static final String TAG = "EditProfileActivity";
    private static final int RC_LOAD_IMAGE = 200;

    @Inject
    EditProfilePresenter mPresenter;

    private Unbinder mUnbinder;
    private Uri mUri;
    @BindView(R.id.iv_profile_pic)
    ImageView mImageViewProfilePic;
    @BindView(R.id.et_name)
    EditText mEditTextName;
    private ProgressDialog mProgressDialog;
    /*
    * Helper method to start this activity
    * */
    public static Intent newIntent(@NonNull Context context){
        return new Intent(context,EditProfileActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        /*
        * Initialize dagger modules and inject this activity
        * */
        DaggerEditProfileComponent.builder()
                .applicationComponent(MedManagerApplication.getApplicationComponent())
                .editProfileModule(new EditProfileModule(this))
                .build()
                .inject(this);
        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }
        mUnbinder = ButterKnife.bind(this);

        mImageViewProfilePic.setOnClickListener((view) -> {
            /*
            * The intent to allow user pick/choose a photo
            * */
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,RC_LOAD_IMAGE);
        });
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Uploading...");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
        * Results from the intent to pick a photo
        * */
        if (requestCode == RC_LOAD_IMAGE && resultCode == Activity.RESULT_OK){
            mUri = data.getData();
            mImageViewProfilePic.setImageURI(mUri);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_save :
                if (mUri != null && !mEditTextName.getText().toString().trim().equals("")){
                    mPresenter.updateProfile(mUri,mEditTextName.getText().toString().trim());
                }else {
                    Toast.makeText(EditProfileActivity.this, "Error, Try again!", Toast.LENGTH_SHORT).show();
                    return false;
                }
                break;
            case android.R.id.home :
                NavUtils.navigateUpFromSameTask(EditProfileActivity.this);
                break;
        }
        return true;
    }

    @Override
    public void showSuccessMessage() {
        Toast.makeText(this, "Update was Successful!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(this, "Error, Try again!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
}
