package com.example.jaeho.productmanagement.Controller.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.Model.DO.QNADO;
import com.example.jaeho.productmanagement.R;

public class QNAAddActivity extends AppCompatActivity {
    EditText qnaSubjectEdt, qnaContentsEdt;
    Button qnaAddbtn;
    InformationDAO myDao;
    QNADO qnado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qnaadd);
        myDao = new NowUsingDAO(this);
        if(myDao.getCurrentUser().getCompanyName().equals(null)){
            Toast.makeText(getApplicationContext(), "세션이 종료되었습니다. 재접속 부탁 드립니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(QNAAddActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        qnaSubjectEdt = (EditText)findViewById(R.id.qnaSubjectEdt);
        qnaContentsEdt = (EditText)findViewById(R.id.qnaContentsEdt);
        qnaAddbtn = (Button)findViewById(R.id.qnaAddbtn);
        qnaAddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qnaSubjectEdt.getText().toString().equals(null)||qnaContentsEdt.getText().toString().equals(null)){
                    Toast.makeText(getApplicationContext(),"하나라도 비어있으면 작성이 불가능합니다",Toast.LENGTH_SHORT).show();
                }else {
                    init();
                    myDao.addQna(qnado);
                }
            }
        });
    }
    private void init(){
        qnado = new QNADO();
        qnado.setSubject(qnaSubjectEdt.getText().toString());
        qnado.setContents(qnaContentsEdt.getText().toString());
        qnado.setName(myDao.getUserName());
        qnado.setEmail(myDao.getUserEmail());
        String inDate = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        qnado.setDate(inDate);
    }
}
