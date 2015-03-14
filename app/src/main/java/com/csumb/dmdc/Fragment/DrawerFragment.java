package com.csumb.dmdc.Fragment;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.csumb.dmdc.Activity.LoginActivity;
import com.csumb.dmdc.Adapter.DrawerAdapter;
import com.csumb.dmdc.R;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment {
    LinearLayout linearLayout;
    private ListView drawerlist;
    private View drawerview;
    private DrawerLayout mDrawerLayout;
    private TextView loggout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private EditText review;
    private String streview;

    private String[] navMenuTitles;

    private ArrayList<String> navDrawerItems;
    private DrawerAdapter adapter;

    public DrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_drawer, container, false);
        mDrawerList = (ListView) v.findViewById(R.id.left_drawer);
        navMenuTitles = getResources().getStringArray(R.array.navdrav_array);
        linearLayout = (LinearLayout) v.findViewById(R.id.relativedrawer);
        setData();
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerList.setItemChecked(position, true);
                displayView(position);
                //Toast.makeText(getActivity().getApplicationContext(), "Clicked..."+String.valueOf(id), Toast.LENGTH_LONG).show();
            }
        });
        mDrawerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerList.setItemChecked(position, true);
                displayView(position);
                //Toast.makeText(getActivity().getApplicationContext(), "Clicked..."+String.valueOf(id), Toast.LENGTH_LONG).show();
                return false;
            }
        });

        return v;
    }
    public void setData(){
        navDrawerItems = new ArrayList<String>();

        // Home
        navDrawerItems.add(navMenuTitles[0]);
        // Profile
        navDrawerItems.add(navMenuTitles[1]);
        // Notifications
        navDrawerItems.add(navMenuTitles[2]);
        // Settings
        navDrawerItems.add(navMenuTitles[3]);
        // Crew
        navDrawerItems.add(navMenuTitles[4]);
        // Whishlist
        navDrawerItems.add(navMenuTitles[5]);
        navDrawerItems.add(navMenuTitles[6]);



        adapter = new DrawerAdapter(getActivity().getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
    }
    public void setUp(Bundle savedInstanceState,int FragmentId,DrawerLayout layout, Toolbar toolbar) {
        drawerview=getActivity().findViewById(FragmentId);
        mDrawerLayout=layout;
        mDrawerToggle=new ActionBarDrawerToggle(getActivity(),layout,toolbar,R.string.app_name,
                R.string.app_name){
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActivity().invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerList.bringToFront();
                mDrawerLayout.requestLayout();
                getActivity().invalidateOptionsMenu();
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }
    public void displayView(int position) {
        // update the main content by replacing fragments
        android.app.Fragment fragment = null;
        switch (position) {
            case 0:
                //fragment = new HomeFragment();
                break;
            case 1:
                //fragment = new ProfileFragment();
                break;
            case 2:
                //fragment = new NotificationFragment();
                break;
            case 3:
                //fragment = new FindSkaterFragment();
                break;
            case 4:

                break;
            case 5:
                ParseUser.logOut();
                Toast.makeText(getActivity().getApplicationContext(), "Disconnected...", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }


}
