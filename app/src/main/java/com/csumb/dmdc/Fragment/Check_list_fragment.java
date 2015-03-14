package com.csumb.dmdc.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.csumb.dmdc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Check_list_fragment extends Fragment {


    public View v;
    String test;
    public Check_list_fragment() {
        // Required empty public constructor
    }

    Button test_button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_check_list_fragment, container, false);
        test_button = (Button)v.findViewById(R.id.check_test_button);
        return v;

    }


}
