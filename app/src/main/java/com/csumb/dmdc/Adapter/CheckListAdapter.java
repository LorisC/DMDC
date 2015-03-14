package com.csumb.dmdc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.csumb.dmdc.ParseClass.CheckList;
import com.csumb.dmdc.R;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by banal_a on 3/14/15.
 */
public class CheckListAdapter extends BaseAdapter {

    ArrayList<CheckList> parseObjects;
    Context context;
    LayoutInflater inflater;

    public CheckListAdapter(Context context,ArrayList<CheckList> parseObjects){
        this.context = context;
        this.parseObjects = parseObjects;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return parseObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return  parseObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.checklist_list_item,null);
        TextView text = (TextView) v.findViewById(R.id.checkText);
        text.setText(parseObjects.get(position).getString("message"));
        Boolean checked = parseObjects.get(position).getBoolean("completed");
        CheckBox  check = (CheckBox) v.findViewById(R.id.check);
        check.setChecked(checked);
        return  v;

    }
}
