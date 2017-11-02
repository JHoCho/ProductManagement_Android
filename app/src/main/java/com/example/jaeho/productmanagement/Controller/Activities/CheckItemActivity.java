package com.example.jaeho.productmanagement.Controller.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.R;

public class CheckItemActivity extends AppCompatActivity {
    LinearLayout btnGoCheckQR,btnSelectedCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_item);
        InformationDAO myDao = new NowUsingDAO(this);
        if(myDao.getCurrentUser().getCompanyName().equals(null)){
            Toast.makeText(getApplicationContext(), "세션이 종료되었습니다. 재접속 부탁 드립니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CheckItemActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        btnGoCheckQR = (LinearLayout)findViewById(R.id.btnGoCheckQR);
        btnSelectedCheck = (LinearLayout)findViewById(R.id.btnSelectedCheck);
        btnGoCheckQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckItemActivity.this,CheckQRActivity.class);
                startActivity(intent);
            }
        });
        btnSelectedCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckItemActivity.this,SelectLocationActivityForOne.class);
                startActivity(intent);
            }
        });
    }
}
