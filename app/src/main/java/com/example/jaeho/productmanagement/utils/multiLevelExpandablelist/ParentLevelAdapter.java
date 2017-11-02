package com.example.jaeho.productmanagement.utils.multiLevelExpandablelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.R;
import com.example.jaeho.productmanagement.utils.multiLevelExpandablelist.SecondLevelAdapter;
import java.util.ArrayList;

import static com.example.jaeho.productmanagement.Controller.Activities.SelectLocationActivity.selectedSt1;
import static com.example.jaeho.productmanagement.Controller.Activities.SelectLocationActivity.selectedSt2;

/**
 * Created by jaeho on 2017. 9. 18..
 */

public class ParentLevelAdapter extends BaseExpandableListAdapter
{   private Context context;
    private ArrayList topLv,midLv;
    private ArrayList<ArrayList<String>> arr =new ArrayList<>();
    InformationDAO myDao;
    int type =2;
    public ParentLevelAdapter(Context context, ArrayList<String> topLv,int type){
        this.context = context;
        this.topLv = topLv;
        if(type==1) {
            this.type = type;
        }
        if(myDao==null)
        {
        myDao= new NowUsingDAO(context);
        }
        System.out.println(topLv);
        for(int i=0;i<topLv.size();i++){
            if(type==1){
                arr.add(myDao.getMiddleLevelLocation(topLv.get(i).toString()));
            }
            else
                arr.add(myDao.getMiddleLevelPname(topLv.get(i).toString()));
        }
        System.out.println(arr);
    }
    @Override
    public int getGroupCount() {
        return topLv.size();
    }

    @Override
    public int getChildrenCount(int groupid) {
        return arr.get(groupid).size();
    }

    @Override
    public Object getGroup(int i) {
        return topLv.get(i);
    }

    @Override
    public String getChild(int group, int child) {
        return arr.get(group).get(child);
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
        switch (type){
            case 1:
                if(convertView==null){
                    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.row_first,null);
                    TextView text = (TextView)convertView.findViewById(R.id.eventsListEventRowText);
                    text.setText(topLv.get(i).toString());
                }
                break;
            case 2:
                if(convertView==null){
                    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.row_first,null);
                    TextView text = (TextView)convertView.findViewById(R.id.eventsListEventRowText);
                    text.setText(topLv.get(i).toString());
                }
                break;
        }

        return convertView;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null){
            System.out.println(groupPosition);
            CustomExpendableListView secondLvELV = new CustomExpendableListView(context);
            ArrayList<String> mid = new ArrayList<>();
            mid.add(arr.get(groupPosition).get(childPosition));
            switch (type){
                case 1:
                    System.out.println("몇번들어오는거죠");
                    secondLvELV.setAdapter(new SecondLevelAdapter(context,mid,myDao.getLowLevelLocation(topLv.get(groupPosition).toString(),arr.get(groupPosition).get(childPosition)),this.type));
                    secondLvELV.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                            selectedSt1[1]=expandableListView.getItemAtPosition(i).toString();
                            selectedSt1[2]="";
                            return false;
                        }
                    });
                    secondLvELV.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                            selectedSt1[1]=expandableListView.getItemAtPosition(i).toString();
                            selectedSt1[2]=expandableListView.getExpandableListAdapter().getChild(i,i1).toString();
                            return false;
                        }
                    });
                    break;
                case 2:
                    secondLvELV.setAdapter(new SecondLevelAdapter(context,arr.get(groupPosition),myDao.getLowLevelPname(topLv.get(groupPosition).toString(),arr.get(groupPosition).get(childPosition).toString()),this.type));
                    secondLvELV.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                            selectedSt2[1]=expandableListView.getItemAtPosition(i).toString();
                            selectedSt2[2]="";
                            return false;
                        }
                    });
                    secondLvELV.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                            selectedSt2[1]=expandableListView.getItemAtPosition(i).toString();
                            selectedSt2[2]=expandableListView.getExpandableListAdapter().getChild(i,i1).toString();
                            return false;
                        }
                    });
                    break;

            }
            secondLvELV.setGroupIndicator(null);
            convertView = secondLvELV;
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
