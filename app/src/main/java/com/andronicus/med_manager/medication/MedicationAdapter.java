package com.andronicus.med_manager.medication;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.data.Medication;
import com.andronicus.med_manager.editmedication.EditMedicationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by andronicus on 3/25/2018.
 */

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder>{

    /*
    * Different colors for medication background
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

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private Context mContext;
    private List<Medication> mMedications;
    private String mUserId;
    public MedicationAdapter(List<Medication> medications,String userId){
        this.mMedications = medications;
        this.mAuth = FirebaseAuth.getInstance();
        mUserId = userId;
        this.mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mUserId).child("medication");
    }

    @Override
    public MedicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medication,parent,false);
        return new MedicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicationViewHolder holder, int position) {
        holder.bind(mMedications.get(position));
    }

    @Override
    public int getItemCount() {
        return mMedications.size();
    }

    public void filter(@NonNull List<Medication> medications){
        mMedications = new ArrayList<>();
        mMedications.addAll(medications);
        notifyDataSetChanged();
    }

    public class MedicationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,PopupMenu.OnMenuItemClickListener{

        TextView mMedicationInitial;
        TextView mMedicationName;
        TextView mPrescription;
        TextView mEndDate;
        ImageView mEditMedication;
        public MedicationViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mMedicationInitial = view.findViewById(R.id.tv_medication_initial);
            mMedicationName = view.findViewById(R.id.tv_medication_name);
            mPrescription = view.findViewById(R.id.tv_prescription);
            mEndDate = view.findViewById(R.id.tv_end_date);
            mEditMedication = view.findViewById(R.id.imageview_edit_medication);
            mEditMedication.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(mContext,mEditMedication);
                popupMenu.getMenuInflater().inflate(R.menu.medication_pop_up,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(MedicationViewHolder.this);
                popupMenu.show();
            });
        }
        private void bind(Medication medication){
            String medicationInitial = medication.getName().substring(0,1);
            String prescription = medication.getNo_of_tablets() + " * " + medication.getFrequency();
            Random random = new Random();
            int position = random.nextInt(colors.length);
            mMedicationInitial.setBackgroundColor(mContext.getResources().getColor(colors[position]));
            mMedicationInitial.setText(medicationInitial);
            mMedicationName.setText(medication.getName());
            mPrescription.setText(prescription);
            mEndDate.setText(medication.getEnd_date());
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Medication medication = mMedications.get(getAdapterPosition());
            switch (item.getItemId()) {
                case R.id.action_edit_medication :
                    //Start Edit Medication activity
                    mContext.startActivity(EditMedicationActivity.newIntent(mContext,medication,mUserId));
                    break;
                case R.id.action_delete_medication :
                    /*
                    * Alert dialog to ask the user if they really want to delete
                    * */
                    new AlertDialog.Builder(mContext)
                            .setMessage("Delete " + medication.getName() + " ?")
                            .setNegativeButton("CANCEL", (dialog1, which) -> dialog1.dismiss())
                            .setPositiveButton("OK", (dialog, which) -> {
                                /*
                                * delete medication from database
                                * */
                                mDatabaseReference.child(medication.getId()).removeValue();
                            }).show();
                    break;
            }
            return true;
        }

        @Override
        public void onClick(View v) {
            Medication medication = mMedications.get(getAdapterPosition());
            mContext.startActivity(MedicationPopupActivity.newIntent(mContext,medication));
        }
    }
}
