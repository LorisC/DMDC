package com.csumb.dmdc.Asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loris on 3/14/2015.
 */
public class GetParseObject extends AsyncTask<Void, Void, List<ParseObject>> {

    public List<ParseObject> getList_parse() {
        return list_parse;
    }

    private List<ParseObject> list_parse = new ArrayList<>();
    String table_name , col_name;
    int switch_value = 0 ;
    ParseUser user;

    public void populate(List<ParseObject> list)
    {
        for (int i = 0 ; i< list.size(); i++) {
            this.list_parse.add(list.get(i));
            Log.d("merde", list_parse.get(i).toString());
        }
        //Log.d("Ah putain de merde", String.valueOf(list_parse.get(1).get("birthcertif")))         ;
    }

    public GetParseObject(String table_name, String col_name ) {
        this.table_name = table_name;
        this .col_name = col_name;
    }

    public GetParseObject(String table_name, String col_name, int switch_value, ParseUser user  ) {
        this.table_name = table_name;
        this .col_name = col_name;
        this.switch_value = switch_value;
        this.user = user;
    }
    /*private void getobject ()
    {ParseQuery<ParseObject> query = ParseQuery.getQuery(this.table_name);
        query.whereEqualTo(this.col_name, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    String test = scoreList.toString();
                    Log.d("Test", test);
                    populate(scoreList);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });}*/

    @Override
    protected List<ParseObject> doInBackground(Void... params) {
        //getobject();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(this.table_name) ;
        try {
            switch (switch_value)
            {
                case 1:
                    //ParseUser usero =(ParseUser) ParseObject.createWithoutData("_User",user);
                    query.whereEqualTo(this.col_name,user );
                    break;
                default:
                    query.whereNotEqualTo(this.col_name, "");
                    break;

            }
            List<ParseObject> list;
            list = query.find();
            populate(list);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list_parse;
    }

    @Override
    protected void onPostExecute(List<ParseObject> parseObjects) {
        super.onPostExecute(parseObjects);
    }
}
