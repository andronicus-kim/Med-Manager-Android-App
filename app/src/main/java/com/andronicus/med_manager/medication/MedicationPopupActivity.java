package com.andronicus.med_manager.medication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.data.Medication;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicationPopupActivity extends AppCompatActivity {

    private static final String TAG = "MedicationPopupActivity";
    public static final String MEDICATION = "MEDICATION";

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

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        String medicationInital = mMedication.getName().substring(0,1);
        mMedicationInitialPopUp.setText(medicationInital);
        mMedicationNamePopUp.setText(mMedication.getName());
        mTextViewDescription.setText(mMedication.getDescription());
        mTextViewPrescription.setText("1 * " + mMedication.getFrequency());
        mTextViewStartDate.setText(mMedication.getStart_date());
        mTextViewEndDate.setText(mMedication.getEnd_date());
        mMedicationInitialPopUp.setBackgroundColor(this.getResources().getColor(colors[position]));

    }
}
