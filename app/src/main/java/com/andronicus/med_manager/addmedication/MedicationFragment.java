package com.andronicus.med_manager.addmedication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andronicus.med_manager.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicationFragment extends Fragment {

    private Unbinder mUnbinder;
    @BindView(R.id.recview_medication)
    RecyclerView mMedicationRecyclerView;
    /*
   * Helper method to create an instance of this fragment
   * */
    public static MedicationFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MedicationFragment fragment = new MedicationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medication, container, false);
        mUnbinder = ButterKnife.bind(this,view);
        List<String> strings = new ArrayList<>();
        strings.add("Methnol");
        strings.add("Maramoja");
        strings.add("Panadol");
        strings.add("Telmi");
        strings.add("Syrup");
        strings.add("Action");
        mMedicationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMedicationRecyclerView.setAdapter(new MedicationAdapter(strings));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
