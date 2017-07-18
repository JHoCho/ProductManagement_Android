package com.example.jaeho.productmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    Button btnProduct,btnCheck,btnQnA,btnCalendar,btnAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
    }
}
