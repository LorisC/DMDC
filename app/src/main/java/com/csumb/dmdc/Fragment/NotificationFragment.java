package com.csumb.dmdc.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.csumb.dmdc.Adapter.NotificationAdapter;
import com.csumb.dmdc.Asynctask.GetParseObject;
import com.csumb.dmdc.R;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    View v;
    ListView listView;
    NotificationAdapter notificationAdapter;


    public NotificationFragment() {
        // Required empty public constructor

        GetParseObject getParseObject = new GetParseObject("notifications","message");
        try {
            List<ParseObject> list = getParseObject.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_notification, container, false);
        listView = (ListView) v.findViewById(R.id.listView);
        ArrayList<String> list = new ArrayList<>();
        notificationAdapter = new  NotificationAdapter(getActivity(),list);
        listView.setAdapter(notificationAdapter);
        return v;
    }


}
