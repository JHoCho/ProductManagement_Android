package com.example.jaeho.productmanagement.utils.multiLevelExpandablelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.jaeho.productmanagement.R;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 9. 18..
 */

public class SecondLevelAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<String> midLv,lowLv;
    int type=2;
    public SecondLevelAdapter(Context context,ArrayList midLv,ArrayList lowLv,int type) {
        this.context = context;
        this.midLv = midLv;
        this.lowLv = lowLv;
        if(type==1) {
            this.type = type;
        }
        System.out.println("인생:"+midLv.toString());
    }
    @Override
    public int getGroupCount() {
        return midLv.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return lowLv.size();
    }

    @Override
    public Object getGroup(int i) {
        return midLv.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return lowLv.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_second,null);
            TextView text = (TextView)convertView.findViewById(R.id.eventsListEventRowText);
            text.setText(midLv.get(i).toString());
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_third, null);
            TextView text = (TextView) convertView.findViewById(R.id.textViewItemName);
            text.setText(lowLv.get(childPosition).toString());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
