package com.andronicus.med_manager.editprofile;

import android.app.Activity;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = "EditProfileActivity";
    private static final int RC_LOAD_IMAGE = 200;


    private Unbinder mUnbinder;
    private Uri mUri;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private FirebaseStorage mStorage;
    private String mUserId;
    @BindView(R.id.iv_profile_pic)
    ImageView mImageViewProfilePic;
    @BindView(R.id.et_name)
    EditText mEditTextName;
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
        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            mUserId = mAuth.getCurrentUser().getUid();
        }
        mUnbinder = ButterKnife.bind(this);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mUserId);
        mStorage = FirebaseStorage.getInstance();

        mImageViewProfilePic.setOnClickListener((view) -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,RC_LOAD_IMAGE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                    UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                            .setDisplayName(mEditTextName.getText().toString().trim())
                            .setPhotoUri(mUri)
                            .build();
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null){
                            user.updateProfile(profileUpdate);
                        }
                        finish();
//                    UploadTask uploadTask = uploadImage();
//                    uploadTask.addOnSuccessListener(taskSnapshot -> {
//                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
//                                .setDisplayName(mEditTextName.getText().toString().trim())
//                                .setPhotoUri(downloadUrl)
//                                .build();
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        if (user != null){
//                            user.updateProfile(profileUpdate);
//                        }
//                        finish();
//                    });
//                    uploadTask.addOnFailureListener(e -> {
//                        Log.e(TAG, "onOptionsItemSelected: " + e.getMessage() );
//                        Toast.makeText(EditProfileActivity.this, "Error uploading Image!", Toast.LENGTH_SHORT).show();
//                    });
                }else {
                    Toast.makeText(this, "Error, Try again!", Toast.LENGTH_SHORT).show();
                    return false;
                }
                break;
            case android.R.id.home :
                NavUtils.navigateUpFromSameTask(EditProfileActivity.this);
                break;
        }
        return true;
    }

    private UploadTask uploadImage() {
        StorageReference filePath = mStorage.getReference().child("profile_images").child(mUserId);
        Bitmap bitmap = null;
        try{
            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),mUri);
        }catch (IOException e){
            Log.e(TAG, "onOptionsItemSelected: " + e.getMessage());
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        return filePath.putBytes(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
