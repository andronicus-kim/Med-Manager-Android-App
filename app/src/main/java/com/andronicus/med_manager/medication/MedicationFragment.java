package com.andronicus.med_manager.medication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.data.Medication;
import com.andronicus.med_manager.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicationFragment extends Fragment implements SearchView.OnQueryTextListener {

    public static final String USER_ID = "USER_ID";
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private Unbinder mUnbinder;
    @BindView(R.id.recview_medication)
    RecyclerView mMedicationRecyclerView;
    @BindView(R.id.iv_empty_recyclerview)
    ImageView mImageViewEmptyRecyclerview;
    @BindView(R.id.tv_empty_recyclerview)
    TextView mTextViewEmptyRecyclerview;
    @BindView(R.id.progress_bar_medication)
    ProgressBar mProgressBar;
    private List<Medication> mMedications;
    private MedicationAdapter mAdapter;
    private String mUserId;

    /*
   * Helper method to create an instance of this fragment
   * */
    public static MedicationFragment newInstance(String userId) {
        
        Bundle args = new Bundle();
        args.putString(USER_ID,userId);
        
        MedicationFragment fragment = new MedicationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medication, container, false);
        mUserId = getArguments().getString(USER_ID);
        mUnbinder = ButterKnife.bind(this,view);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        mMedications = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String userId = null;
        if (mProgressBar != null){
            mProgressBar.setVisibility(View.VISIBLE);
        }
        if (mAuth.getCurrentUser() != null){
            userId = mAuth.getCurrentUser().getUid();
        }else {
            return;
        }

        /*
        * Listen for real time changes in medication
        * */
        mDatabaseReference.child(userId).child("medication").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMedications.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Medication medication = snapshot.getValue(Medication.class);
                        if (medication != null){
                            mMedications.add(medication);
                        }
                    }
                }
                if (mMedications.size() == 0){
                    if (mProgressBar != null){
                        mProgressBar.setVisibility(View.GONE);
                    }
                    mMedicationRecyclerView.setVisibility(View.GONE);
                    mImageViewEmptyRecyclerview.setVisibility(View.VISIBLE);
                    mTextViewEmptyRecyclerview.setVisibility(View.VISIBLE);
                }else {
                    if (mProgressBar != null){
                        mProgressBar.setVisibility(View.GONE);
                    }
                    if (mMedicationRecyclerView != null){
                        mMedicationRecyclerView.setVisibility(View.VISIBLE);
                    }
                    mImageViewEmptyRecyclerview.setVisibility(View.GONE);
                    mTextViewEmptyRecyclerview.setVisibility(View.GONE);
                    mAdapter = new MedicationAdapter(mMedications,mUserId);
                    mMedicationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mMedicationRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.medication,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String name = newText.toLowerCase();
        List<Medication> medications = new ArrayList<>();
        if (mMedications.size() > 0){
            for (Medication medication:mMedications){
                if (medication.getName().toLowerCase().contains(name)){
                    medications.add(medication);
                }
            }
            mAdapter.filter(medications);
        }else {
            return false;
        }
        return true;
    }
}
