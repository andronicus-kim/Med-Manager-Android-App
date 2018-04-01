package com.andronicus.med_manager.medication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andronicus.med_manager.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicationFragment extends Fragment implements SearchView.OnQueryTextListener {

    private Unbinder mUnbinder;
    @BindView(R.id.recview_medication)
    RecyclerView mMedicationRecyclerView;
    @BindView(R.id.iv_empty_recyclerview)
    ImageView mImageViewEmptyRecyclerview;
    @BindView(R.id.tv_empty_recyclerview)
    TextView mTextViewEmptyRecyclerview;
    private List<String> mStrings;
    private MedicationAdapter mAdapter;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medication, container, false);
        mUnbinder = ButterKnife.bind(this,view);
        mStrings = new ArrayList<>();
        mStrings.add("Methnol");
        mStrings.add("Maramoja");
        mStrings.add("Panadol");
        mStrings.add("Telmi");
        mStrings.add("Syrup");
        mStrings.add("Action");
        if (mStrings.size() == 0){
            mMedicationRecyclerView.setVisibility(View.GONE);
            mImageViewEmptyRecyclerview.setVisibility(View.VISIBLE);
            mTextViewEmptyRecyclerview.setVisibility(View.VISIBLE);
        }else {
            mMedicationRecyclerView.setVisibility(View.VISIBLE);
            mImageViewEmptyRecyclerview.setVisibility(View.GONE);
            mTextViewEmptyRecyclerview.setVisibility(View.GONE);
            mAdapter = new MedicationAdapter(mStrings);
            mMedicationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mMedicationRecyclerView.setAdapter(mAdapter);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.medication,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String name = newText.toLowerCase();
        List<String> strings = new ArrayList<>();
        if (mStrings.size() > 0){
            for (String string:mStrings){
                if (string.toLowerCase().contains(name)){
                    strings.add(string);
                }
            }
            mAdapter.filter(strings);
        }else {
            return false;
        }
        return true;
    }
}
