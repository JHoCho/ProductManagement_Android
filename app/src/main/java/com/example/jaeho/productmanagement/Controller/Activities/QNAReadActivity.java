package com.example.jaeho.productmanagement.Controller.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.Model.DO.QNADO;
import com.example.jaeho.productmanagement.R;
import com.example.jaeho.productmanagement.utils.CurentUser;

public class QNAReadActivity extends AppCompatActivity {
    TextView qnaContentsTv,qnaSubjectTv;
    Button qnaDeleteBtn;
    QNADO nowData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qnaread);
        final InformationDAO myDao = new NowUsingDAO(this);
        try{
            CurentUser.getInstance().getCompanyName();
        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(), "세션이 종료되었습니다. 재접속 부탁 드립니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
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
