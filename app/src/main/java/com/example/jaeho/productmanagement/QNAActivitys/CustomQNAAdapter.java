package com.example.jaeho.productmanagement.QNAActivitys;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 7. 31..
 */

public class CustomQNAAdapter extends BaseAdapter {

    Context context;
    ArrayList<QNADO> qnaList;
    //동적으로 할당하기 세번째 단계인 어답터 만들기입니다. DO와 VIEW만들어놓은것을 이용하여 함수세팅을 해줍니다.
    public CustomQNAAdapter(){}
    public CustomQNAAdapter(Context context, ArrayList<QNADO> qnaList) {
        this.context = context;
        this.qnaList = qnaList;
    }
    public void add(QNADO board){
        qnaList.add(board);
        notifyDataSetChanged();//이부분에서 알려주지않으면 뷰가 리프레시 되지 않습니다.
    }
    public void remove(QNADO board){
        for(int i =0; i<qnaList.size();i++){
            if(board.getKey().equals(qnaList.get(i).getKey())){
                qnaList.remove(i);
                notifyDataSetChanged();
            };
        }
    }
    @Override
    public int getCount() {
        return qnaList.size();
    }

    @Override
    public QNADO getItem(int i) {
        return qnaList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomQNAView tempView;
        if(convertView==null) {
            tempView = new CustomQNAView(context);
        }else {
            tempView = (CustomQNAView) convertView;
        }
        tempView.setData(getItem(position));
        return tempView;
    }
}
