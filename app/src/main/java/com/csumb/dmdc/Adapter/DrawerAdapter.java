package com.csumb.dmdc.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csumb.dmdc.R;

import java.util.ArrayList;

/**
 * Created by banal_a on 3/13/15.
 */
public class DrawerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> navDrawerItems;
    LayoutInflater inflater;

    public DrawerAdapter(Context context, ArrayList<String> navDrawerItems){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.navDrawerItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_list_item, null);
        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.textView);
        txtTitle.setText(navDrawerItems.get(position));


        return convertView;
    }
}
