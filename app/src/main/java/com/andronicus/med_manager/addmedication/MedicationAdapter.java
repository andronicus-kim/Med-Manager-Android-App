package com.andronicus.med_manager.addmedication;

import android.icu.util.ValueIterator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andronicus.med_manager.R;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andronicus on 3/25/2018.
 */

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder>{

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

    private List<String> mStrings;
    public MedicationAdapter(List<String> strings){
        mStrings = strings;
    }

    @Override
    public MedicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medication,parent,false);
        return new MedicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicationViewHolder holder, int position) {
        holder.bind(mStrings.get(position));
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    public class MedicationViewHolder extends RecyclerView.ViewHolder{

        TextView mMedicationInitial;
        TextView mMedicationName;
        public MedicationViewHolder(View view) {
            super(view);
            mMedicationInitial = view.findViewById(R.id.tv_medication_initial);
            mMedicationName = view.findViewById(R.id.tv_medication_name);

        }
        private void bind(String name){
            String medicationInital = name.substring(0,1);
            Random random = new Random();
            int position = random.nextInt(colors.length);
            mMedicationInitial.setBackgroundColor(colors[position]);
            mMedicationInitial.setText(medicationInital);
            mMedicationName.setText(name);
        }
    }
}
