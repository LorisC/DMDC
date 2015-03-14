package com.csumb.dmdc.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csumb.dmdc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Check_list_fragment extends Fragment {


    public Check_list_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_list_fragment, container, false);
    }


}
