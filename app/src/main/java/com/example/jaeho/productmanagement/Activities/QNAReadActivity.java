package com.example.jaeho.productmanagement.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jaeho.productmanagement.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.QNAActivitys.QNADO;
import com.example.jaeho.productmanagement.R;

public class QNAReadActivity extends AppCompatActivity {
    TextView qnaContentsTv,qnaSubjectTv;
    Button qnaDeleteBtn;
    QNADO nowData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qnaread);
        final InformationDAO myDao = new NowUsingDAO(this);
        qnaContentsTv = (TextView)findViewById(R.id.qnaContentsTv);
        qnaSubjectTv = (TextView)findViewById(R.id.qnaSubjectTv);
        qnaDeleteBtn = (Button)findViewById(R.id.qnaDeleteBtn);

        init();
        qnaContentsTv.setText(nowData.getContents());
        qnaSubjectTv.setText(nowData.getSubject());
        qnaDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDao.deleteQna(nowData);
            }
        });
    }
    private void init(){
        Intent intent = getIntent();//qnaActivity로 부터(DatabaseFromFirebase.java)
        nowData = new QNADO();
        nowData.setContents(intent.getExtras().getString("contents"));
        nowData.setName(intent.getExtras().getString("name"));
        nowData.setDate(intent.getExtras().getString("date"));
        nowData.setEmail(intent.getExtras().getString("email"));
        nowData.setKey(intent.getExtras().getString("key"));
        nowData.setSubject(intent.getExtras().getString("subject"));
        setDelbtnVisibility(intent.getExtras().getBoolean("isMine"));
    }
    private void setDelbtnVisibility(boolean a){
        if(a){
            qnaDeleteBtn.setVisibility(View.VISIBLE);
        }
    }

}
