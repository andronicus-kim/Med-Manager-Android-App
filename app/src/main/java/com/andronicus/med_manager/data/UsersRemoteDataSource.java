package com.andronicus.med_manager.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by andronicus on 4/11/2018.
 */

public class UsersRemoteDataSource implements UsersDataSource {
    private static final String TAG = "UsersRemoteDataSource";

    private Context mContext;
    private FirebaseStorage mStorage;
    private FirebaseAuth mAuth;

    @Inject
    UsersRemoteDataSource(@NonNull Context context){
        this.mContext = context;
        this.mStorage = FirebaseStorage.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void updateProfile(Uri uri, String displayName, UserProfileUpdate profileUpdate) {
        UploadTask uploadTask = uploadImage(uri,displayName);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
        /*
        * Photo was successfully uploaded, respond accordingly
        * */

        profileUpdate.onSuccess();
        Uri downloadUrl = taskSnapshot.getDownloadUrl();
        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .setPhotoUri(downloadUrl)
                .build();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.updateProfile(userProfileChangeRequest);
        }
        });
        uploadTask.addOnFailureListener(e -> {
        /*
        * Photo upload was unsuccessful
        * */
        profileUpdate.onFailure();
        });
    }
    private UploadTask uploadImage(Uri uri,String displayName) {
        /*
        * Helper method to upload the chosen photo to firebase storage
        * */
        StorageReference filePath = mStorage.getReference().child("profile_images").child(mAuth.getCurrentUser().getUid());
        Bitmap bitmap = null;
        try{
            bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(),uri);
        }catch (IOException e){
            Log.e(TAG, "onOptionsItemSelected: " + e.getMessage());
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        return filePath.putBytes(data);
    }
}
