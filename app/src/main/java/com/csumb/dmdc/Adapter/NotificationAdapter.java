package com.csumb.dmdc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csumb.dmdc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 3/14/15.
 */
public class NotificationAdapter extends BaseAdapter {

    ArrayList<String> list;
    Context context;
    LayoutInflater inflater;
    public NotificationAdapter(Context context,ArrayList<String> list){
        this.context = context;
        this.list =list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.notification_list_item,null);
        TextView textView = (TextView) v.findViewById(R.id.textView2);
        textView.setText(list.get(position));

        return v;
    }
}
