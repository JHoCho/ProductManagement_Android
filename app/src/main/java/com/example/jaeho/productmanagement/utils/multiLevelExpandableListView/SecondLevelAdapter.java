package com.example.jaeho.productmanagement.utils.multiLevelExpandableListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.jaeho.productmanagement.Model.DAOS.Mysql.SQLiteDB;
import com.example.jaeho.productmanagement.R;

/**
 * Created by jaeho on 2017. 9. 13..
 */

public class SecondLevelAdapter extends BaseExpandableListAdapter {

    private Context context;
    private int type;
    SQLiteDB sqLiteDB = new SQLiteDB(context);
    int THIRD_LEVEL_COUNT;
    String hi;
    public SecondLevelAdapter(Context context,int type,String hi) {
        this.context = context;
        if(type==1){
            this.type=type;
            THIRD_LEVEL_COUNT =sqLiteDB.getTopLevelLocation().size();
            this.hi = hi;
        }else {
            this.type=type;
            THIRD_LEVEL_COUNT = sqLiteDB.getTopLevelPname().size();
            this.hi = hi;
        }
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
        switch(this.type){
            case 1:
                THIRD_LEVEL_COUNT = sqLiteDB.getLowLevelLocation(hi,getGroup(groupPosition).toString()).size();
                break;
            case 2:
                THIRD_LEVEL_COUNT = sqLiteDB.getLowLevelPname(hi,getGroup(groupPosition).toString()).size();
                break;
        }
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
