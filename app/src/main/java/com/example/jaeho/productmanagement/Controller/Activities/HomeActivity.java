package com.example.jaeho.productmanagement.Controller.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.R;

public class HomeActivity extends AppCompatActivity {
    Button btnProduct,btnCheck,btnQnA,btnCalendar,btnAbout;
    InformationDAO myDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myDao = new NowUsingDAO(this);
        if(myDao.getCurrentUser().getCompanyName().equals(null)){
            Toast.makeText(getApplicationContext(), "세션이 종료되었습니다. 재접속 부탁 드립니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        myDao.addListenerForSQLite();
        btnProduct= (Button)findViewById(R.id.btnProduct);
        btnCheck= (Button)findViewById(R.id.btnCheck);
        btnQnA= (Button)findViewById(R.id.btnQnA);
        btnCalendar= (Button)findViewById(R.id.btnCalendar);
        btnAbout= (Button)findViewById(R.id.btnAbout);

        btnProduct.setOnClickListener(new View.OnClickListener() {//checkItem클래스로 인텐트 시키는 부분
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,CheckItemActivity.class);
                startActivity(intent);
            }
        });
        btnQnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,QNAActivity.class);
                startActivity(intent);
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,SelectLocationActivity.class);
                startActivity(intent);
            }
        });
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}
