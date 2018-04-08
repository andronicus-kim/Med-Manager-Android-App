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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.data.Medication;
import com.andronicus.med_manager.medication.MedicationActivity;
import com.andronicus.med_manager.util.DatePickerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddMedicationFragment extends Fragment implements AdapterView.OnItemSelectedListener{

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
    @BindView(R.id.spinner_frequency)
    Spinner mSpinnerFrequency;
    @BindView(R.id.et_start_date)
    EditText mEditTextStartDate;
    @BindView(R.id.et_end_date)
    EditText mEditTextEndDate;
    @BindView(R.id.et_tablets)
    EditText mEditTextNumberOfTablets;


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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.frequencies, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
        mSpinnerFrequency.setAdapter(adapter);
        mSpinnerFrequency.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] strings = getActivity().getResources().getStringArray(R.array.frequencies);
        String selectedString = strings[position];
        Toast.makeText(getActivity(), selectedString, Toast.LENGTH_SHORT).show();
        if (selectedString.equals(R.string.select_frequency)){
            Toast.makeText(getActivity(), "Nothing selected", Toast.LENGTH_SHORT).show();
        }else if (selectedString.equals("Once a day")){
            Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
        }else if (selectedString.equals(R.string.twice_a_day)){
            Toast.makeText(getActivity(), "2", Toast.LENGTH_SHORT).show();
        }else if (selectedString.equals(R.string.thrice_a_day)){
            Toast.makeText(getActivity(), "3", Toast.LENGTH_SHORT).show();
        }else if (selectedString.equals(R.string.four_times_a_day)){
            Toast.makeText(getActivity(), "4", Toast.LENGTH_SHORT).show();
        }else if (selectedString.equals(R.string.five_times_a_day)){
            Toast.makeText(getActivity(), "5", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
            String no_of_tablets = mEditTextNumberOfTablets.getText().toString().trim();
            String start_date = mEditTextStartDate.getText().toString().trim();
            String end_date = mEditTextEndDate.getText().toString().trim();

            if (name.equals("")){
                mEditTextName.setError("Name required!");
                return false;
            }else if (description.equals("")){
                mEditTextDescription.setError("Description required!");
                return false;
            }else if (no_of_tablets.equals("")){
                mEditTextNumberOfTablets.setError("Number of tablets required!");
                return false;
            }else if (start_date.equals("")){
                mEditTextStartDate.setError("Start date required!");
                return false;
            }else if (end_date.equals("")){
                mEditTextEndDate.setError("End date required!");
                return false;
            }

            DatabaseReference medicationReference = mDatabaseReference.child(mAuth.getCurrentUser().getUid()).child("medication");
            String id = medicationReference.push().getKey();
            Medication medication = new Medication(id,name,description,no_of_tablets,"3",start_date,end_date);
            medicationReference.child(id).setValue(medication);
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
