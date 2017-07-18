package com.example.jaeho.productmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CheckItemActivity extends AppCompatActivity {
    Button btnGoCheckQR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_item);
        btnGoCheckQR = (Button)findViewById(R.id.btnGoCheckQR);
        btnGoCheckQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckItemActivity.this,CheckQRActivity.class);
                startActivity(intent);
            }
        });
    }
}
