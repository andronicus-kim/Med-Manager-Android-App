package com.andronicus.med_manager.addmedication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
    @BindView(R.id.et_start_date)
    EditText mStartDate;
    @BindView(R.id.et_end_date)
    EditText mEndDate;

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
        datePickerFragment.passClickedEditText(mStartDate);
        datePickerFragment.show(getActivity().getSupportFragmentManager(),"date-picker");
    }
    @OnClick(R.id.et_end_date) public void onEndDateClick(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.passClickedEditText(mEndDate);
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
