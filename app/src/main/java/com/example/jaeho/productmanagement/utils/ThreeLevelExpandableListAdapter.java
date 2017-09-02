package com.example.jaeho.productmanagement.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.jaeho.productmanagement.R;

import static com.example.jaeho.productmanagement.utils.Constants.FIRST_LEVEL_COUNT;
import static com.example.jaeho.productmanagement.utils.Constants.SECOND_LEVEL_COUNT;

/**
 * Created by jaeho on 2017. 9. 2..
 */

public class ThreeLevelExpandableListAdapter extends BaseExpandableListAdapter{
    private Context context;

    public ThreeLevelExpandableListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object getChild(int arg0, int arg1) {
        return arg1;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context);
        secondLevelELV.setAdapter(new SecondLevelAdapter(context));
        secondLevelELV.setGroupIndicator(null);
        return secondLevelELV;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return SECOND_LEVEL_COUNT;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public int getGroupCount() {
        return FIRST_LEVEL_COUNT;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_first, null);
            TextView text = (TextView) convertView.findViewById(R.id.eventsListEventRowText);
            text.setText("FIRST LEVEL");
        }
        return convertView;
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
