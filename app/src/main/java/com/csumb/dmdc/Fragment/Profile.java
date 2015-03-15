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
import java.util.List;

public class Profile extends Fragment {
    private String mParam1;
    private String mParam2;

    ImageView img;
    TextView name,email,pay,current,lastdeploy,position;
    ListView mission;
    View header;
    View v;
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
        v = inflater.inflate(R.layout.fragment_profile,container,false);
        header= getActivity().getLayoutInflater().inflate(R.layout.fragment_profile, null);

        name = (TextView) v.findViewById(R.id.name);
        email = (TextView) v.findViewById(R.id.email);
        pay = (TextView) v.findViewById(R.id.pay);
        current =(TextView) v.findViewById(R.id.position);
        lastdeploy = (TextView)v.findViewById(R.id.last);
        img = (ImageView) v.findViewById(R.id.imageView);
        //mission =(ListView) header.findViewById(R.id.profile_list);
        //mission.addHeaderView(v);
        currentuser =ParseUser.getCurrentUser();
        currentuser.fetchInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    name.setText(""+currentuser.getString("name"));
                    email.setText("Email : "+currentuser.getEmail());
                    pay.setText("Pay : "+currentuser.getString("pay"));
                    current.setText("Location : "+currentuser.getString("Currently"));
                    lastdeploy.setText("Last Affectation :"+currentuser.getString("Last_deployment"));
                    Picasso.with(getActivity()).load(currentuser.getParseFile("profile_pic").getUrl()).into(img);
                } else {
                    // Failure!
                }
            }
        });
        //startRemoteDataTask();
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

                query.orderByDescending("createdAt");
                query.whereEqualTo("user",ParseUser.getCurrentUser());
                ob = query.find();
                for(ParseObject info : ob)
                {

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

        }
    }

}
