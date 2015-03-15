package com.csumb.dmdc.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.csumb.dmdc.ParseClass.Feedback;
import com.csumb.dmdc.R;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {


    public FeedbackFragment() {
        // Required empty public constructor
    }
    View v;
    EditText editText;
    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_feedback, container, false);
        editText = (EditText)v.findViewById(R.id.feedbacktext);
        button = (Button)v.findViewById(R.id.sendfeed);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Feedback feed = new Feedback();
                feed.put("comment",editText.getText().toString());
                feed.put("feedbacker", ParseUser.getCurrentUser());
                feed.saveInBackground();
                editText.setText(R.string.feedbackmessagesended);
            }
        });
        return v;
    }


}
