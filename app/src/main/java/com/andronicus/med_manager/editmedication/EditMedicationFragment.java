package com.andronicus.med_manager.editmedication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.medication.MedicationActivity;

public class EditMedicationFragment extends Fragment {


    public static EditMedicationFragment newInstance() {

        Bundle args = new Bundle();

        EditMedicationFragment fragment = new EditMedicationFragment();
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
        return inflater.inflate(R.layout.fragment_edit_medication, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_medication,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save){
            Toast.makeText(getActivity(), "Updating...", Toast.LENGTH_SHORT).show();
            startActivity(MedicationActivity.newIntent(getActivity()));
        }
        return true;
    }
}
