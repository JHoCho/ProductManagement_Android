package com.example.jaeho.productmanagement.utils.multiLevelExpandableListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DAOS.Mysql.SQLiteDB;
import com.example.jaeho.productmanagement.Model.DAOS.NodeJS.NodeJSDAO;
import com.example.jaeho.productmanagement.Model.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.R;

/**
 * Created by jaeho on 2017. 9. 13..
 */

public class TopLevel extends BaseExpandableListAdapter {


    private Context context;
    private int type;
    SQLiteDB sqLiteDB = new SQLiteDB(context);
    private int FIRST_LEVEL_COUNT,SECOND_LEVEL_COUNT;
    public TopLevel(Context context,int type) {
        this.context = context;
        if(type==1){
            this.type=type;
            FIRST_LEVEL_COUNT =sqLiteDB.getTopLevelLocation().size();
        }else {
            this.type=type;
            FIRST_LEVEL_COUNT = sqLiteDB.getTopLevelPname().size();
        }
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
        SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context.getApplicationContext());//이부분에 원래 MainActivity.this있었음
        secondLevelELV.setAdapter(new SecondLevelAdapter(context,type,getGroup(groupPosition).toString()));
        secondLevelELV.setGroupIndicator(null);
        return secondLevelELV;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        switch(this.type){
            case 1:
                SECOND_LEVEL_COUNT = sqLiteDB.getMiddleLevelLocation(getGroup(groupPosition).toString()).size();
                break;
            case 2:
                SECOND_LEVEL_COUNT = sqLiteDB.getMiddleLevelPname(getGroup(groupPosition).toString()).size();
                break;
        }
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
