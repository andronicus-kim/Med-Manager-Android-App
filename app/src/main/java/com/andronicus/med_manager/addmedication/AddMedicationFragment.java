package com.andronicus.med_manager.addmedication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.medication.MedicationActivity;
import com.andronicus.med_manager.util.DatePickerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddMedicationFragment extends Fragment{

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String FREQUENCY = "frequency";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";

    private Unbinder mUnbinder;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    @BindView(R.id.et_name)
    EditText mEditTextName;
    @BindView(R.id.et_description)
    EditText mEditTextDescription;
    @BindView(R.id.et_frequency)
    EditText mEditTextFrequency;
    @BindView(R.id.et_start_date)
    EditText mEditTextStartDate;
    @BindView(R.id.et_end_date)
    EditText mEditTextEndDate;



    public static AddMedicationFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AddMedicationFragment fragment = new AddMedicationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_medication, container, false);
        mUnbinder = ButterKnife.bind(this,view);
        return view;
    }
    @OnClick(R.id.et_start_date) public void onStartDateClick(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.passClickedEditText(mEditTextStartDate);
        datePickerFragment.show(getActivity().getSupportFragmentManager(),"date-picker");
    }
    @OnClick(R.id.et_end_date) public void onEndDateClick(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.passClickedEditText(mEditTextEndDate);
        datePickerFragment.show(getActivity().getSupportFragmentManager(),"date-picker");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_medication,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save){
            String name = mEditTextName.getText().toString().trim();
            String description = mEditTextDescription.getText().toString().trim();
            String frequency = mEditTextFrequency.getText().toString().trim();
            String start_date = mEditTextStartDate.getText().toString().trim();
            String end_date = mEditTextEndDate.getText().toString().trim();
            if (name.equals("")){
                mEditTextName.setError("Name cannot be Blank!");
                return false;
            }else if (description.equals("")){
                mEditTextDescription.setError("Description cannot be Blank!");
                return false;
            }else if (frequency.equals("")){
                mEditTextFrequency.setError("Frequency cannot be Blank!");
                return false;
            }else if (start_date.equals("")){
                mEditTextStartDate.setError("Start date cannot be Blank!");
                return false;
            }else if (end_date.equals("")){
                mEditTextEndDate.setError("End date cannot be Blank!");
                return false;
            }
            DatabaseReference medicationReference = mDatabaseReference.child(mAuth.getCurrentUser().getUid()).child("medication").push();
            medicationReference.child(NAME).setValue(name);
            medicationReference.child(DESCRIPTION).setValue(description);
            medicationReference.child(FREQUENCY).setValue(frequency);
            medicationReference.child(START_DATE).setValue(start_date);
            medicationReference.child(END_DATE).setValue(end_date);
            startActivity(MedicationActivity.newIntent(getActivity()));
            getActivity().finish();
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
