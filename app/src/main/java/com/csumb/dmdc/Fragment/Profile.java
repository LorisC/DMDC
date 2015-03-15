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
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView img;

    List<ParseObject> ob;
    private List<Profile> Profile = null;
    private WeakReference<RemoteDataTask> asyncTaskWeakRef;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile,container,false);
        startRemoteDataTask();
        img = (ImageView) v.findViewById(R.id.imageView);
        ParseFile file = ParseUser.getCurrentUser().getParseFile("profile_pic");
        String str = ParseUser.getCurrentUser().getUsername();
        Toast.makeText(getActivity(),str,Toast.LENGTH_LONG);
        if(file!=null){
        Picasso.with(getActivity()).load(file.getUrl()).into(img);}
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
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");

                query.orderByDescending("createdAt");
                query.whereEqualTo("_User",ParseUser.getCurrentUser());
                ob = query.find();
                for(ParseObject info : ob)
                {
                    ParseFile username = (ParseFile) info.get("name");
                    ParseFile email = (ParseFile) info.get("email");
                    ParseFile profile_pic = (ParseFile) info.get("profile_pic");
                    if(profile_pic!=null){
                        Picasso.with(getActivity()).load(profile_pic.getUrl())
                                .centerCrop().into(img);}
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
            TextView user_text = (TextView) getView().findViewById(R.id.username);
            TextView email_text = (TextView) getView().findViewById(R.id.email);
            ParseUser current = ParseUser.getCurrentUser();
            String username = current.getString("name");
            String email = current.getEmail();
            user_text.setText(username);
            email_text.setText(email);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
