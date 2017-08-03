package com.example.jaeho.productmanagement.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.jaeho.productmanagement.QNAActivitys.CustomQNAAdapter;
import com.example.jaeho.productmanagement.QNAActivitys.QNADO;
import com.example.jaeho.productmanagement.R;

import java.util.ArrayList;

public class QNAActivity extends AppCompatActivity {
    ListView qnaListView;
    CustomQNAAdapter mAdapter;
    ArrayList<QNADO> qnaFromFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna);
        qnaListView = (ListView)findViewById(R.id.qnaListView);
        //qnaFromFirebase부분 코딩해야함 어레이 리스트에 파이어베이스의 데이터들을 가져오고 QNADO모양으로 넣도록함.
        mAdapter = new CustomQNAAdapter(this,qnaFromFirebase);
        qnaListView.setAdapter(mAdapter);
        init();
    }
    public void init(){

    }
}
