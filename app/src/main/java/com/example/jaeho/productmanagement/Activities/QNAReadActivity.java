package com.example.jaeho.productmanagement.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jaeho.productmanagement.R;

public class QNAReadActivity extends AppCompatActivity {
    TextView qnaContentsTv,qnaSubjectTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qnaread);
        qnaContentsTv = (TextView)findViewById(R.id.qnaContentsTv);
        qnaSubjectTv = (TextView)findViewById(R.id.qnaSubjectTv);
        Intent intent = getIntent();
        qnaContentsTv.setText(intent.getExtras().getString("contents"));
        qnaSubjectTv.setText(intent.getExtras().getString("subject"));
    }
}
