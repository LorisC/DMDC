package com.csumb.dmdc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csumb.dmdc.ParseClass.Mission;
import com.csumb.dmdc.R;

import java.util.ArrayList;

/**
 * Created by banal_a on 3/15/15.
 */
public class MissionAdapter extends BaseAdapter {

    ArrayList<Mission> missions;
    Context context;
    LayoutInflater inflater;

    public MissionAdapter(Context context,ArrayList<Mission> missions){
        this.missions = missions;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return missions.size();
    }

    @Override
    public Object getItem(int position) {
        return missions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.mission_list_item,null);
        TextView operation = (TextView) v.findViewById(R.id.operation);
        TextView where = (TextView) v.findViewById(R.id.where);
        TextView grade = (TextView) v.findViewById(R.id.grade);
        TextView duration = (TextView) v.findViewById(R.id.duration);

        operation.setText("Operation name : "+ missions.get(position).getString("message"));
        where.setText("Operation country : "+ missions.get(position).getString("where"));
        grade.setText("Grade : " + missions.get(position).getString("grade"));
        duration.setText("Duration : "+ missions.get(position).getString("duration"));


        return v;
    }
}
