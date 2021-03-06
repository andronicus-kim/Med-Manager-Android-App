package com.andronicus.med_manager.addmedication;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.data.Medication;
import com.andronicus.med_manager.medication.MedicationActivity;
import com.andronicus.med_manager.util.AlarmReceiver;
import com.andronicus.med_manager.util.DatePickerFragment;
import com.andronicus.med_manager.util.TimerPickerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddMedicationFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener{

    public static final String USER_ID = "USER_ID";
    private Unbinder mUnbinder;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private String mFrequency;
    private List<String> mReminders;
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
    @BindView(R.id.tv_reminder_1)
    TextView mTextViewReminder1;
    @BindView(R.id.tv_reminder_2)
    TextView mTextViewReminder2;
    @BindView(R.id.tv_reminder_3)
    TextView mTextViewReminder3;
    @BindView(R.id.tv_reminder_4)
    TextView mTextViewReminder4;
    @BindView(R.id.tv_reminder_5)
    TextView mTextViewReminder5;

    private String mUserId;


    public static AddMedicationFragment newInstance(String userId) {
        
        Bundle args = new Bundle();
        args.putString(USER_ID,userId);
        
        AddMedicationFragment fragment = new AddMedicationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mUserId = getArguments().getString(USER_ID);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_medication, container, false);
        mUnbinder = ButterKnife.bind(this,view);
        clearAllReminders();
        setOnClickListeners();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.frequencies, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
        mSpinnerFrequency.setAdapter(adapter);
        mSpinnerFrequency.setOnItemSelectedListener(this);
        return view;
    }
    private void clearAllReminders(){
        /*
        * Clear any reminders that may have been set initially
        * */
        mTextViewReminder1.setVisibility(View.GONE);
        mTextViewReminder2.setVisibility(View.GONE);
        mTextViewReminder3.setVisibility(View.GONE);
        mTextViewReminder4.setVisibility(View.GONE);
        mTextViewReminder5.setVisibility(View.GONE);

        mTextViewReminder1.setText(R.string.set_reminder);
        mTextViewReminder2.setText(R.string.set_reminder);
        mTextViewReminder3.setText(R.string.set_reminder);
        mTextViewReminder4.setText(R.string.set_reminder);
        mTextViewReminder5.setText(R.string.set_reminder);
    }
    private void setOnClickListeners(){
        /*
        * Set click event listeners for each text view
        * */
        mTextViewReminder1.setOnClickListener(this);
        mTextViewReminder2.setOnClickListener(this);
        mTextViewReminder3.setOnClickListener(this);
        mTextViewReminder4.setOnClickListener(this);
        mTextViewReminder5.setOnClickListener(this);
    }

    private void launchTimePickerFragment(@NonNull TextView textView){
        /*
        * Helper method to launch Time Picker fragment
        * */
        TimerPickerFragment newFragment = new TimerPickerFragment();
        newFragment.passClickedTextView(textView);
        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onClick(View v) {
        /*
        * Launch time picker fragment and pass it the text view that triggered the event
        * */
        switch (v.getId()){
            case R.id.tv_reminder_1 :
                launchTimePickerFragment(mTextViewReminder1);
                break;
            case R.id.tv_reminder_2 :
                launchTimePickerFragment(mTextViewReminder2);
                break;
            case R.id.tv_reminder_3 :
                launchTimePickerFragment(mTextViewReminder3);
                break;
            case R.id.tv_reminder_4 :
                launchTimePickerFragment(mTextViewReminder4);
                break;
            case R.id.tv_reminder_5 :
                launchTimePickerFragment(mTextViewReminder5);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*
        * show different number of text views depending on the frequency selected by user
        * */
        switch (position){
            case 0 :
                clearAllReminders();
                mFrequency = "0";
                break;
            case 1 :
                clearAllReminders();
                mFrequency = "1";
                mTextViewReminder1.setVisibility(View.VISIBLE);
                break;
            case 2 :
                clearAllReminders();
                mFrequency = "2";
                mTextViewReminder1.setVisibility(View.VISIBLE);
                mTextViewReminder2.setVisibility(View.VISIBLE);
                break;
            case 3 :
                clearAllReminders();
                mFrequency = "3";
                mTextViewReminder1.setVisibility(View.VISIBLE);
                mTextViewReminder2.setVisibility(View.VISIBLE);
                mTextViewReminder3.setVisibility(View.VISIBLE);
                break;
            case 4 :
                clearAllReminders();
                mFrequency = "4";
                mTextViewReminder1.setVisibility(View.VISIBLE);
                mTextViewReminder2.setVisibility(View.VISIBLE);
                mTextViewReminder3.setVisibility(View.VISIBLE);
                mTextViewReminder4.setVisibility(View.VISIBLE);
                break;
            case 5 :
                clearAllReminders();
                mFrequency = "5";
                mTextViewReminder1.setVisibility(View.VISIBLE);
                mTextViewReminder2.setVisibility(View.VISIBLE);
                mTextViewReminder3.setVisibility(View.VISIBLE);
                mTextViewReminder4.setVisibility(View.VISIBLE);
                mTextViewReminder5.setVisibility(View.VISIBLE);
                break;
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
            /*
            * get text from edit texts
            * */
            String name = mEditTextName.getText().toString().trim();
            String description = mEditTextDescription.getText().toString().trim();
            String no_of_tablets = mEditTextNumberOfTablets.getText().toString().trim();
            String start_date = mEditTextStartDate.getText().toString().trim();
            String end_date = mEditTextEndDate.getText().toString().trim();

            //Issue appropriate errors
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
            }else if (mFrequency == null || mFrequency.equals("0")){
                Toast.makeText(getActivity(), "You've not selected Frequency!", Toast.LENGTH_LONG).show();
            }

            //get set reminders
            mReminders = getReminders(mFrequency);

            //save medication
            DatabaseReference medicationReference = mDatabaseReference.child(mUserId).child("medication");
            String id = medicationReference.push().getKey();
            Medication medication = new Medication(id,name,description,no_of_tablets,mFrequency,mReminders,start_date,end_date);
            medicationReference.child(id).setValue(medication);
            startActivity(MedicationActivity.newIntent(getActivity(),mUserId));
            getActivity().finish();
        }
        return true;
    }

    //Get reminders set by user then use them to trigger alarms
    private List<String> getReminders(String frequency){
        List<String> reminders = new ArrayList<>();
        switch (Integer.parseInt(frequency)) {
            case 1 :
                reminders.add(mTextViewReminder1.getText().toString().trim());
                setReminder(mTextViewReminder1.getText().toString().trim(),1);
                break;
            case 2 :
                reminders.add(mTextViewReminder1.getText().toString().trim());
                setReminder(mTextViewReminder1.getText().toString().trim(),2);
                reminders.add(mTextViewReminder2.getText().toString().trim());
                setReminder(mTextViewReminder2.getText().toString().trim(),3);
                break;
            case 3 :
                reminders.add(mTextViewReminder1.getText().toString().trim());
                setReminder(mTextViewReminder1.getText().toString().trim(),4);
                reminders.add(mTextViewReminder2.getText().toString().trim());
                setReminder(mTextViewReminder2.getText().toString().trim(),5);
                reminders.add(mTextViewReminder3.getText().toString().trim());
                setReminder(mTextViewReminder3.getText().toString().trim(),6);
                break;
            case 4 :
                reminders.add(mTextViewReminder1.getText().toString().trim());
                setReminder(mTextViewReminder1.getText().toString().trim(),7);
                reminders.add(mTextViewReminder2.getText().toString().trim());
                setReminder(mTextViewReminder2.getText().toString().trim(),8);
                reminders.add(mTextViewReminder3.getText().toString().trim());
                setReminder(mTextViewReminder3.getText().toString().trim(),9);
                reminders.add(mTextViewReminder4.getText().toString().trim());
                setReminder(mTextViewReminder4.getText().toString().trim(),10);
                break;
            case 5 :
                reminders.add(mTextViewReminder1.getText().toString().trim());
                setReminder(mTextViewReminder1.getText().toString().trim(),11);
                reminders.add(mTextViewReminder2.getText().toString().trim());
                setReminder(mTextViewReminder2.getText().toString().trim(),12);
                reminders.add(mTextViewReminder3.getText().toString().trim());
                setReminder(mTextViewReminder3.getText().toString().trim(),13);
                reminders.add(mTextViewReminder4.getText().toString().trim());
                setReminder(mTextViewReminder4.getText().toString().trim(),14);
                reminders.add(mTextViewReminder5.getText().toString().trim());
                setReminder(mTextViewReminder5.getText().toString().trim(),15);
                break;
        }
        return reminders;
    }

    /*
    * Trigger alarms/Reminders
    * */
    private void setReminder(String reminder,int requestCode){
        Calendar calendar = Calendar.getInstance();
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),requestCode,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        String[] time = reminder.split(":");
        int hour = Integer.parseInt(time[0].trim());
        int minute = Integer.parseInt(time[1].trim());
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
