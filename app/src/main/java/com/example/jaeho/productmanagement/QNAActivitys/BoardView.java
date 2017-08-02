package com.example.jaeho.productmanagement.QNAActivitys;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.jaeho.productmanagement.R;

/**
 * Created by jaeho on 2017. 8. 2..
 */

public class BoardView extends FrameLayout {
    public BoardView(@NonNull Context context) {
        super(context);
        init();
    }

    public BoardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    TextView idxTv,nameTv,contentsTv;
    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.dlg_custom_qna,this);
        idxTv = (TextView)findViewById(R.id.idxTv);
        nameTv = (TextView)findViewById(R.id.nameTv);
        contentsTv = (TextView)findViewById(R.id.contentsTv);
    }
    public void setBoardData(QNABoard data){
        idxTv.setText(data.getIdx());
        nameTv.setText(data.getName());
        contentsTv.setText(data.getContents());
    }
}
