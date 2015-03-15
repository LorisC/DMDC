package com.csumb.dmdc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csumb.dmdc.ParseClass.Family;
import com.csumb.dmdc.R;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by banal_a on 3/14/15.
 */
public class FamilyAdapter extends BaseAdapter {

    ArrayList<Family> families;
    Context context;
    LayoutInflater inflater;

    public FamilyAdapter(Context context,ArrayList<Family> families){
        this.families=families;
        this.context=context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return families.size();
    }

    @Override
    public Object getItem(int position) {
        return families.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.family_list_item,null);
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView age = (TextView) v.findViewById(R.id.age);
        TextView link = (TextView) v.findViewById(R.id.link);
        ImageView img = (ImageView) v.findViewById(R.id.imageView3);
        Picasso.with(context).load(families.get(position).getParseFile("picture").getUrl()).into(img);
       name.setText("Name : " +families.get(position).getString("name"));
       age.setText("Age : " + families.get(position).getInt("age"));
       link.setText("Link : " + families.get(position).getString("link"));
        return v;
    }
}
