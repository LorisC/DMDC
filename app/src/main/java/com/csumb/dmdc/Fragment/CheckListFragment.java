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
import android.widget.Button;
import android.widget.ListView;

import com.csumb.dmdc.Activity.CropActivity;
import com.csumb.dmdc.Adapter.CheckListAdapter;
import com.csumb.dmdc.ParseClass.CheckList;
import com.csumb.dmdc.R;
import com.melnykov.fab.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckListFragment extends Fragment {


    public View v;
    View header;
    String test;
    ArrayList<CheckList> checkLists;
    List<ParseObject> ob;
    ListView listView;
    CheckListAdapter adapter;
    private WeakReference<RemoteDataTask> asyncTaskWeakRef;
    public CheckListFragment() {
        // Required empty public constructor
    }

    Button test_button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_check_list_fragment, container, false);
        header = getActivity().getLayoutInflater().inflate(R.layout.header_checklist, null);
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

        private WeakReference<CheckListFragment> fragmentWeakRef;

        public RemoteDataTask(CheckListFragment fragment)
        {
            this.fragmentWeakRef = new WeakReference<CheckListFragment>(fragment);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            checkLists = new ArrayList<CheckList>();
            try {
                // Locate the class table named "Country" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "checklist");
                // Locate the column named "ranknum" in Parse.com and order list
                // by ascending
                query.orderByAscending("createdAt");
                ob = query.find();
                for (ParseObject media : ob) {
                    // Locate images in flag column
                    CheckList map = new CheckList();
                    if(media.getString("message")!=null){
                    map.put("message",media.getString("message"));}
                    if(media.get("completed")!=null)
                    map.put("completed",media.getBoolean("completed"));
                    if(media.get("mandatory")!=null)
                    map.put("mandatory",media.getBoolean("mandatory"));
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
            if (getView().findViewById(R.id.listView)!=null)
                listView = (ListView) getView().findViewById(R.id.listView);
            // Pass the results into ListViewAdapter.java

            adapter = new CheckListAdapter(getActivity(),
                    checkLists);
            adapter.notifyDataSetChanged();
            // Binds the Adapter to the ListView
            listView.addHeaderView(header);
            listView.setAdapter(adapter);
            final FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
            fab.hide(false);
            fab.attachToListView(listView);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), CropActivity.class);
                    getActivity().startActivity(i);
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    fab.show();
                }
            });


        }

    }


}
