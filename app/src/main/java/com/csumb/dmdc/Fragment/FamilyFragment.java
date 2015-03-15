package com.csumb.dmdc.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.csumb.dmdc.Activity.AddFActivity;
import com.csumb.dmdc.Activity.CropActivity;
import com.csumb.dmdc.Adapter.CheckListAdapter;
import com.csumb.dmdc.Adapter.FamilyAdapter;
import com.csumb.dmdc.ParseClass.CheckList;
import com.csumb.dmdc.ParseClass.Family;
import com.csumb.dmdc.R;
import com.melnykov.fab.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {

    View v;
    String test;
    ArrayList<Family> checkLists;
    List<ParseObject> ob;
    ListView listView;
    FamilyAdapter adapter;
    private WeakReference<RemoteDataTask> asyncTaskWeakRef;
    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v= inflater.inflate(R.layout.fragment_family, container, false);
        startRemoteDataTask();
        return v;
    }
    private void startRemoteDataTask() {
        RemoteDataTask asyncTask = new RemoteDataTask(this);
        this.asyncTaskWeakRef = new WeakReference<RemoteDataTask >(asyncTask );
        asyncTask.execute();
    }
    private boolean isAsyncTaskPendingOrRunning() {
        return this.asyncTaskWeakRef != null &&
                this.asyncTaskWeakRef.get() != null &&
                !this.asyncTaskWeakRef.get().getStatus().equals(AsyncTask.Status.FINISHED);
    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<FamilyFragment> fragmentWeakRef;

        public RemoteDataTask(FamilyFragment fragment)
        {
            this.fragmentWeakRef = new WeakReference<FamilyFragment>(fragment);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            checkLists = new ArrayList<Family>();
            try {
                // Locate the class table named "Country" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "familly");
                // Locate the column named "ranknum" in Parse.com and order list
                // by ascending
                query.orderByAscending("createdAt");
                query.whereEqualTo("user", ParseUser.getCurrentUser());
                ob = query.find();
                for (ParseObject media : ob) {
                    // Locate images in flag column
                    Family map = new Family();
                    if(media.get("picture")!=null){
                        map.put("picture",media.getParseFile("picture"));}
                    if(media.getString("name")!=null){
                        map.put("name",media.getString("name"));}
                    if(media.getString("insnumber")!=null){
                        map.put("insnumber",media.getString("insnumber"));}
                    if(media.get("age")!=null)
                        map.put("age",media.getInt("age"));
                    if(media.get("link")!=null)
                        map.put("link",media.getString("link"));
                    checkLists.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            if (getView().findViewById(R.id.listView2)!=null)
                listView = (ListView) getView().findViewById(R.id.listView2);
            // Pass the results into ListViewAdapter.java

            adapter = new FamilyAdapter(getActivity(),
                    checkLists);
            adapter.notifyDataSetChanged();
            // Binds the Adapter to the ListView
            listView.setAdapter(adapter);
            final FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
            fab.attachToListView(listView);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), AddFActivity.class);
                    getActivity().startActivity(i);
                }
            });

        }

    }

}
