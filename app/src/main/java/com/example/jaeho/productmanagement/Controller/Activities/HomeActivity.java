package com.example.jaeho.productmanagement.Controller.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.R;
import com.example.jaeho.productmanagement.utils.CurentUser;

import static com.example.jaeho.productmanagement.utils.Constants.showProgressDialog;

public class HomeActivity extends AppCompatActivity {
    LinearLayout btnProduct,btnCheck,btnQnA,btnCalendar,btnAbout;
    InformationDAO myDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myDao = new NowUsingDAO(this);
        try{
            CurentUser.getInstance().getCompanyName();
            myDao.addListenerForSQLite();
        }catch (NullPointerException e){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        btnProduct= (LinearLayout) findViewById(R.id.btnProduct);
        btnCheck= (LinearLayout)findViewById(R.id.btnCheck);
        btnQnA= (LinearLayout)findViewById(R.id.btnQnA);
        btnCalendar= (LinearLayout)findViewById(R.id.btnCalendar);
        btnAbout= (LinearLayout)findViewById(R.id.btnAbout);

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
                showProgressDialog(HomeActivity.this);
                Intent intent = new Intent(HomeActivity.this,CalendarActivity.class);
                startActivity(intent);
            }
        });
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDao.deleteListenerForSQLite();
    }
}
