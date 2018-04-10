package com.andronicus.med_manager.medication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.addmedication.AddMedicationActivity;
import com.andronicus.med_manager.data.User;
import com.andronicus.med_manager.editprofile.EditProfileActivity;
import com.andronicus.med_manager.signin.SignInActivity;
import com.andronicus.med_manager.util.ActivityUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MedicationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String USER_ID = "USER_ID";
    private static final String TAG = "MedicationActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private GoogleSignInClient mSignInClient;
    private TextView mTextViewUserName;
    private TextView mTextViewEmail;
    private ImageView mImageViewProfilePic;
    private String mName;
    private String mEmail;
    private String mProfileImageUrl;
    private String mUserId;
    /*
    * Helper method to start this activity
    * */
    public static Intent newIntent(@NonNull Context context,String userId){
        Intent intent = new Intent(context,MedicationActivity.class);
        intent.putExtra(USER_ID,userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        mUserId = getIntent().getStringExtra(USER_ID);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient(this,signInOptions);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        * Reference the layout that contains the fragment in
        * Sign In activity layout
        * */
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null){
            //If the layout contains no fragment then create a new instance
            fragment = MedicationFragment.newInstance(getIntent().getStringExtra(USER_ID));
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),R.id.fragment_container,fragment);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( (view) -> {
                startActivity(AddMedicationActivity.newIntent(this,getIntent().getStringExtra(USER_ID)));
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeader = navigationView.getHeaderView(0);
        mTextViewUserName = navHeader.findViewById(R.id.tv_username);
        mTextViewEmail = navHeader.findViewById(R.id.tv_email);
        mImageViewProfilePic = navHeader.findViewById(R.id.iv_profile_pic);

        displayProfile();
    }
    private void displayProfile(){
            mDatabaseReference.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        mName = user.getDisplayName();
                        mEmail = user.getEmail();
                        mProfileImageUrl = user.getPhotoUrl().toString();
                        if (mName != null && mEmail != null && mProfileImageUrl != null) {
                            mTextViewUserName.setText(mName);
                            mTextViewEmail.setText(mEmail);
                            Picasso.get()
                                    .load(mProfileImageUrl)
                                    .placeholder(R.drawable.user)
                                    .error(R.drawable.user)
                                    .into(mImageViewProfilePic);
                        }
                    }else {
                       showDefaultProfile();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

    private void showDefaultProfile() {
            mTextViewUserName.setText("UserName");
            mTextViewEmail.setText("user@email.com");
            mImageViewProfilePic.setImageDrawable(getResources().getDrawable(R.drawable.user));
//        DatabaseReference userReference = mDatabaseReference.child(user.getUid());
//        User userInfo = new User(user.getUid(),user.getDisplayName(),user.getEmail(),user.getPhotoUrl().toString());
//        userReference.setValue(userInfo);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit_profile) {
            startActivity(EditProfileActivity.newIntent(MedicationActivity.this));
        }else if (id == R.id.nav_sign_out){
            if (mAuth.getCurrentUser() != null){
                mSignInClient.signOut();
                FirebaseAuth.getInstance().signOut();
                startActivity(SignInActivity.newIntent(MedicationActivity.this));
                finish();
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
