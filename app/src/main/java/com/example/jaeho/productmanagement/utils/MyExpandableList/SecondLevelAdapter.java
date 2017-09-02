package com.example.jaeho.productmanagement.utils.MyExpandableList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.jaeho.productmanagement.R;

import static com.example.jaeho.productmanagement.utils.Constants.THIRD_LEVEL_COUNT;

/**
 * Created by jaeho on 2017. 9. 2..
 */

public class SecondLevelAdapter extends BaseExpandableListAdapter {
    private Context context;

    public SecondLevelAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_third, null);
            TextView text = (TextView) convertView.findViewById(R.id.eventsListEventRowText);
            text.setText("SECOND LEVEL");
        }
        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_second, null);
            TextView text = (TextView) convertView.findViewById(R.id.eventsListEventRowText);
            text.setText("THIRD LEVEL");
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return THIRD_LEVEL_COUNT;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
