package com.andronicus.med_manager.medication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.andronicus.med_manager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicationPopupActivity extends AppCompatActivity {


    public static Intent newIntent(@NonNull Context context){
        return new Intent(context,MedicationPopupActivity.class);
    }

    @BindView(R.id.tv_medication_initial_pop_up)
    TextView mMedicationInitialPopUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_popup);
        ButterKnife.bind(this);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        mMedicationInitialPopUp.setBackgroundColor(this.getResources().getColor(R.color.card_background1));

    }
}
