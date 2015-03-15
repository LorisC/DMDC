package com.csumb.dmdc.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.csumb.dmdc.Adapter.NotificationAdapter;
import com.csumb.dmdc.Asynctask.GetParseObject;
import com.csumb.dmdc.R;
import com.parse.ParseObject;
import com.parse.ParseUser;

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
    ParseUser  current;
    List<ParseObject> list  =  new ArrayList<>();

    public NotificationFragment() {
        // Required empty public constructor
        current = ParseUser.getCurrentUser();
        String userId = current.getObjectId();
        GetParseObject getParseObject = new GetParseObject("notifications" ,"user", 1 , current);


        try {
           list = getParseObject.execute().get();
            for (int i = 0; i < list.size(); i++) {
                list.get(i).getString("message");
                Log.d("teste",list.get(i).getString("message") );
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_notification, container, false);
        listView = (ListView) v.findViewById(R.id.listView);
        ArrayList<String> notificationList = new ArrayList<>();
        notificationAdapter = new  NotificationAdapter(getActivity(),notificationList);
        for (int i = 0; i < list.size(); i++){
            boolean iscompleted = list.get(i).getBoolean("iscompleted");
            if (!iscompleted){
                notificationList.add(list.get(i).getString("message"));
            }
        }

        listView.setAdapter(notificationAdapter);




        return v;
    }


}
