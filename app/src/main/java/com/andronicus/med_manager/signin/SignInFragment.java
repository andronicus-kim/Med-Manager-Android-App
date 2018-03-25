package com.andronicus.med_manager.signin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.addmedication.MedicationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private Unbinder mUnbinder;
    /*
    * Helper method to create an instance of this Fragment
    * */
    public static SignInFragment newInstance() {

        Bundle args = new Bundle();

        SignInFragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        /*
        * Pass the target view that butterknife will bind it the views to
        * */
        mUnbinder = ButterKnife.bind(this,view);
        return view;
    }
    @OnClick(R.id.button_sign_in) void signIn(){
        startActivity(MedicationActivity.newIntent(getActivity()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*
        * Release all bindings in order to free up memory
        * */
        mUnbinder.unbind();
    }
}
