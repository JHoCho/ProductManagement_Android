package com.example.jaeho.productmanagement.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.jaeho.productmanagement.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.QNAActivitys.CustomQNAAdapter;
import com.example.jaeho.productmanagement.QNAActivitys.QNADO;
import com.example.jaeho.productmanagement.R;

import java.util.ArrayList;

public class QNAActivity extends AppCompatActivity {
    ListView qnaListView;
    CustomQNAAdapter mAdapter;
    ArrayList<QNADO> qnaFromDatabase;
    InformationDAO myDao;
    Button toQNAActivitybtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna);
        qnaListView = (ListView)findViewById(R.id.qnaListView);
        toQNAActivitybtn = (Button)findViewById(R.id.toQNAActivitybtn);
        myDao = new NowUsingDAO(this);
        //qnaFromFirebase부분 코딩해야함 어레이 리스트에 파이어베이스의 데이터들을 가져오고 QNADO모양으로 넣도록함.
        qnaFromDatabase = myDao.getLast10QNAs( );
        mAdapter = new CustomQNAAdapter(this,qnaFromDatabase);
        qnaListView.setAdapter(mAdapter);
        init();

        toQNAActivitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),QNAAddActivity.class);
                startActivity(intent);
            }
        });
    }
    public void init(){
    }
}
