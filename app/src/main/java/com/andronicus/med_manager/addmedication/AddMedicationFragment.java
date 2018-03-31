package com.andronicus.med_manager.addmedication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
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
import com.andronicus.med_manager.util.DatePickerListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddMedicationFragment extends Fragment{

    private Unbinder mUnbinder;
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
            if (mEditTextName.getText().toString().equals("")){
                mEditTextName.setError("Name cannot be Blank!");
                return false;
            }else if (mEditTextDescription.getText().toString().equals("")){
                mEditTextDescription.setError("Description cannot be Blank!");
                return false;
            }else if (mEditTextFrequency.getText().toString().equals("")){
                mEditTextFrequency.setError("Frequency cannot be Blank!");
                return false;
            }else if (mEditTextStartDate.getText().toString().equals("")){
                mEditTextStartDate.setError("Start date cannot be Blank!");
                return false;
            }else if (mEditTextEndDate.getText().toString().equals("")){
                mEditTextEndDate.setError("End date cannot be Blank!");
                return false;
            }
            Toast.makeText(getActivity(), "Saving...", Toast.LENGTH_SHORT).show();
            startActivity(MedicationActivity.newIntent(getActivity()));
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
