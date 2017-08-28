package com.example.jaeho.productmanagement.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.jaeho.productmanagement.R;

public class CheckItemActivity extends AppCompatActivity {
    Button btnGoCheckQR,btnSelectedCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_item);
        btnGoCheckQR = (Button)findViewById(R.id.btnGoCheckQR);
        btnSelectedCheck = (Button)findViewById(R.id.btnSelectedCheck);
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
                Intent intent = new Intent(CheckItemActivity.this,SelectedItemCheckActivity.class);
                startActivity(intent);
            }
        });
    }
}
