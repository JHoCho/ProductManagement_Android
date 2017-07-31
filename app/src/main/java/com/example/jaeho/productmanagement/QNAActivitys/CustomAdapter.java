package com.example.jaeho.productmanagement.QNAActivitys;

import android.content.Context;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 7. 31..
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<QNAList> qnaList;
    LayoutInflater inflater;

    public CustomAdapter(){}
    public CustomAdapter(Context context, ArrayList<QNAList> qnaList) {
        this.context = context;
        this.qnaList = qnaList;
    }
    @Override
    public int getCount() {
        return qnaList.size();
    }

    @Override
    public Object getItem(int i) {
        return qnaList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater==null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        return null;
    }
}
