package com.csumb.dmdc.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.csumb.dmdc.Adapter.MissionAdapter;
import com.csumb.dmdc.ParseClass.Mission;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import com.csumb.dmdc.R;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {
    private String mParam1;
    private String mParam2;

    ImageView img;
    TextView name,email,pay,current,lastdeploy,position;
    ListView mission;
    View header;
    View v;
    ArrayList<Mission> missions;
    MissionAdapter missionAdapter;
    ParseUser currentuser;


    List<ParseObject> ob;
    private List<Profile> Profile = null;
    private WeakReference<RemoteDataTask> asyncTaskWeakRef;


    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        header= inflater.inflate(R.layout.profile_mission,container,false);
        v= getActivity().getLayoutInflater().inflate(R.layout.fragment_profile, null);

        name = (TextView) v.findViewById(R.id.name);
        email = (TextView) v.findViewById(R.id.email);
        pay = (TextView) v.findViewById(R.id.pay);
        current =(TextView) v.findViewById(R.id.position);
        lastdeploy = (TextView)v.findViewById(R.id.last);
        img = (ImageView) v.findViewById(R.id.imageView);

        currentuser =ParseUser.getCurrentUser();
        currentuser.fetchInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    name.setText("" + currentuser.getString("name"));
                    email.setText("Email : " + currentuser.getEmail());
                    pay.setText("Pay : " + currentuser.getString("pay"));
                    current.setText("Location : " + currentuser.getString("Currently"));
                    lastdeploy.setText("Last Affectation :" + currentuser.getString("Last_deployment"));
                    Picasso.with(getActivity()).load(currentuser.getParseFile("profile_pic").getUrl()).into(img);
                } else {
                    // Failure!
                }
            }
        });
        startRemoteDataTask();
        return header;



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

    private class RemoteDataTask extends AsyncTask<Void, Void, Void>{
        private WeakReference<Profile> fragmetnWeakRef;

        public RemoteDataTask(Profile fragment)
        {
            this.fragmetnWeakRef = new WeakReference<Profile>(fragment);
        }
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params){
            //create
            try{
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("mission");
                missions = new ArrayList<Mission>();
                query.orderByDescending("createdAt");
                //query.whereEqualTo("user",ParseUser.getCurrentUser());
                ob = query.find();
                for(ParseObject info : ob)
                {
                    Mission map = new Mission();
                    map.put("duration",info.getString("duration"));
                    map.put("where",info.getString("where"));
                    map.put("message",info.getString("message"));
                    map.put("grade",info.getString("grade"));
                    missions.add(map);
                }
            }
            catch(ParseException e)
            {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            mission =(ListView) header.findViewById(R.id.profile_list);
            missionAdapter = new MissionAdapter(getActivity(),missions);
            missionAdapter.notifyDataSetChanged();
            mission.setAdapter(missionAdapter);
            mission.addHeaderView(v);
        }
    }

}
