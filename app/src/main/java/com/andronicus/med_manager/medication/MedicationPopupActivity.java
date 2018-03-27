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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicationPopupActivity extends AppCompatActivity {

    private static final String TAG = "MedicationPopupActivity";
    public static final String NAME = "NAME";
    public static final String COLOR = "COLOR";

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

    String name;
    int color;


    public static Intent newIntent(@NonNull Context context,String name, int color){
        Intent intent = new Intent(context,MedicationPopupActivity.class);
        intent.putExtra(NAME,name);
        intent.putExtra(COLOR,color);
        return intent;
    }

    @BindView(R.id.tv_medication_initial_pop_up)
    TextView mMedicationInitialPopUp;
    @BindView(R.id.tv_medication_name_pop_up)
    TextView mMedicationNamePopUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_popup);
        ButterKnife.bind(this);
        
        name = getIntent().getStringExtra(NAME);
        color = getIntent().getIntExtra(COLOR, 0);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        String medicationInital = name.substring(0,1);
        mMedicationNamePopUp.setText(name);
        mMedicationInitialPopUp.setText(medicationInital);

        mMedicationInitialPopUp.setBackgroundColor(this.getResources().getColor(colors[color]));

    }
}
