package com.andronicus.med_manager.addmedication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andronicus.med_manager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicationFragment extends Fragment {


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
        return inflater.inflate(R.layout.fragment_medication, container, false);
    }

}
