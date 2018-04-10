package com.andronicus.med_manager.medication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.data.Medication;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicationPopupActivity extends AppCompatActivity {

    private static final String TAG = "MedicationPopupActivity";
    public static final String MEDICATION = "MEDICATION";

    /*
    * This activity gives the user more details
    * about their medication
    * */

    private int[] colors = new int[]{
            R.color.card_background1,
            R.color.card_background2,
            R.color.card_background3,
            R.color.card_background4,
            R.color.card_background5,
            R.color.card_background6,
            R.color.card_background7,
            R.color.card_background8,
            R.color.card_background9,
            R.color.card_background10,
            R.color.card_background11,
    };

    private Medication mMedication;
    int position;

    /*
   * Helper method to start this activity
   * */
    public static Intent newIntent(@NonNull Context context,Medication medication){
        Intent intent = new Intent(context,MedicationPopupActivity.class);
        intent.putExtra(MEDICATION,medication);
        return intent;
    }

    @BindView(R.id.tv_medication_initial_pop_up)
    TextView mMedicationInitialPopUp;
    @BindView(R.id.tv_medication_name_pop_up)
    TextView mMedicationNamePopUp;
    @BindView(R.id.tv_description_pop_up)
    TextView mTextViewDescription;
    @BindView(R.id.tv_prescription_pop_up)
    TextView mTextViewPrescription;
    @BindView(R.id.tv_start_date_pop_up)
    TextView mTextViewStartDate;
    @BindView(R.id.tv_end_date_pop_up)
    TextView mTextViewEndDate;
    @BindView(R.id.tv_pop_up_reminder1)
    TextView mTextViewReminder1;
    @BindView(R.id.tv_pop_up_reminder2)
    TextView mTextViewReminder2;
    @BindView(R.id.tv_pop_up_reminder3)
    TextView mTextViewReminder3;
    @BindView(R.id.tv_pop_up_reminder4)
    TextView mTextViewReminder4;
    @BindView(R.id.tv_pop_up_reminder5)
    TextView mTextViewReminder5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_popup);
        ButterKnife.bind(this);
        
        mMedication = getIntent().getParcelableExtra(MEDICATION);
        Random random = new Random();
        position = random.nextInt(colors.length);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.63));

        /*
        * Fill in medication information in activity views
        *
        * */
        fillLayout();
    }

    private void fillLayout(){
        String medicationInitial = mMedication.getName().substring(0,1);
        String prescription = mMedication.getNo_of_tablets() + " * " + mMedication.getFrequency();
        mMedicationInitialPopUp.setText(medicationInitial);
        mMedicationInitialPopUp.setBackgroundColor(this.getResources().getColor(colors[position]));
        mMedicationNamePopUp.setText(mMedication.getName());
        mTextViewDescription.setText(mMedication.getDescription());
        mTextViewPrescription.setText(prescription);
        mTextViewStartDate.setText(mMedication.getStart_date());
        mTextViewEndDate.setText(mMedication.getEnd_date());
        int size = mMedication.getReminders().size();
        List<String> reminders = mMedication.getReminders();
        setReminders(size,reminders);
    }

    private void setReminders(int size, List<String> reminders){
        switch (size) {
            case 1 :
                mTextViewReminder1.setVisibility(View.VISIBLE);
                mTextViewReminder1.setText(reminders.get(0));
                break;
            case 2 :
                mTextViewReminder1.setVisibility(View.VISIBLE);
                mTextViewReminder2.setVisibility(View.VISIBLE);
                mTextViewReminder1.setText(reminders.get(0));
                mTextViewReminder2.setText(reminders.get(1));
                break;
            case 3 :
                mTextViewReminder1.setVisibility(View.VISIBLE);
                mTextViewReminder2.setVisibility(View.VISIBLE);
                mTextViewReminder3.setVisibility(View.VISIBLE);
                mTextViewReminder1.setText(reminders.get(0));
                mTextViewReminder2.setText(reminders.get(1));
                mTextViewReminder3.setText(reminders.get(2));
                break;
            case 4 :
                mTextViewReminder1.setVisibility(View.VISIBLE);
                mTextViewReminder2.setVisibility(View.VISIBLE);
                mTextViewReminder3.setVisibility(View.VISIBLE);
                mTextViewReminder4.setVisibility(View.VISIBLE);
                mTextViewReminder1.setText(reminders.get(0));
                mTextViewReminder2.setText(reminders.get(1));
                mTextViewReminder3.setText(reminders.get(2));
                mTextViewReminder4.setText(reminders.get(3));
                break;
            case 5 :
                mTextViewReminder1.setVisibility(View.VISIBLE);
                mTextViewReminder2.setVisibility(View.VISIBLE);
                mTextViewReminder3.setVisibility(View.VISIBLE);
                mTextViewReminder4.setVisibility(View.VISIBLE);
                mTextViewReminder5.setVisibility(View.VISIBLE);
                mTextViewReminder1.setText(reminders.get(0));
                mTextViewReminder2.setText(reminders.get(1));
                mTextViewReminder3.setText(reminders.get(2));
                mTextViewReminder4.setText(reminders.get(3));
                mTextViewReminder5.setText(reminders.get(4));
                break;
        }
    }
}
