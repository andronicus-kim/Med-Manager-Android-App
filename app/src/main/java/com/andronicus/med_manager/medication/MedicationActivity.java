package com.andronicus.med_manager.medication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.addmedication.AddMedicationActivity;
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

public class MedicationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mSignInClient;
    private TextView mTextViewUserName;
    private TextView mTextViewEmail;
    /*
    * Helper method to start this activity
    * */
    public static Intent newIntent(@NonNull Context context){
        return new Intent(context,MedicationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient(this,signInOptions);
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        * Reference the layout that contains the fragment in
        * Sign In activity layout
        * */
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null){
            //If the layout contains no fragment then create a new instance
            fragment = MedicationFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),R.id.fragment_container,fragment);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( (view) -> {
                startActivity(AddMedicationActivity.newIntent(this));
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

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            if (user.getDisplayName() != null){
                mTextViewUserName.setText(user.getDisplayName());
            }else {
                mTextViewUserName.setText("UserName");
            }
            if (user.getEmail() != null){
                mTextViewEmail.setText(user.getEmail());
            }else {
                mTextViewEmail.setText("user@email.com");
            }
        }
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
                FirebaseAuth.getInstance().signOut();
                mSignInClient.signOut();
                startActivity(SignInActivity.newIntent(MedicationActivity.this));
                finish();
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
