package com.andronicus.med_manager.signin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.data.User;
import com.andronicus.med_manager.medication.MedicationActivity;
import com.andronicus.med_manager.util.ActivityUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    public static final int RC_SIGN_IN = 100;
    private GoogleSignInClient mSignInClient;
    private FirebaseAuth mAuth;
    private Unbinder mUnbinder;
    private DatabaseReference mDatabaseReference;
    private ProgressDialog mProgressDialog;

    @BindView(R.id.btn_sign_in)
    SignInButton mButtonSignIn;

    public static Intent newIntent(@NonNull Context context){
        return new Intent(context,SignInActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        mUnbinder = ButterKnife.bind(this);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient(this,signInOptions);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading...");
    }
    @OnClick(R.id.btn_sign_in)public void onSignInClick(){
        signIn();
    }
    private void signIn() {
        mProgressDialog.show();
        Intent signInIntent = mSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }else {
                mProgressDialog.show();
            }
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            if (mProgressDialog != null && mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
            Snackbar.make(mButtonSignIn,"Sign in error!",Snackbar.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this,(task -> {
                    if (task.isSuccessful()){
                        checkIfUserExists(account);
                    }else {
                        if (mProgressDialog != null && mProgressDialog.isShowing()){
                            mProgressDialog.dismiss();
                        }
                        Snackbar.make(mButtonSignIn,task.getException().getMessage(),Snackbar.LENGTH_SHORT).show();
                    }
                }));
    }
    private void checkIfUserExists(GoogleSignInAccount account){
        mDatabaseReference.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    //User exists
                    launchMedicationActivity(mAuth.getCurrentUser().getUid());
                }else {
                    saveUser(new User(mAuth.getCurrentUser().getUid(),account.getDisplayName(),account.getEmail(),account.getPhotoUrl().toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveUser(User user) {
        DatabaseReference userReference = mDatabaseReference.child(user.getId());
        userReference.setValue(user);
        launchMedicationActivity(user.getId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!= null){
            launchMedicationActivity(user.getUid());
        }
    }

    private void launchMedicationActivity(String userId) {
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        startActivity(MedicationActivity.newIntent(this,userId));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        mUnbinder.unbind();
    }
}
