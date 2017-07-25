package com.example.jaeho.productmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jaeho.productmanagement.DAOS.FireBaseDAO;
import com.example.jaeho.productmanagement.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.DAOS.NowUsingDAO;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnSignin;
    EditText edtID, edtPW;
    InformationDAO myDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDao = new NowUsingDAO(this);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignin = (Button) findViewById(R.id.btnSignin);
        edtID = (EditText) findViewById(R.id.edtID);
        edtPW = (EditText) findViewById(R.id.edtPW);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDao.checkSignIn(edtID.getText().toString(),edtPW.getText().toString());
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDao.makeAccount(edtID.getText().toString(),edtPW.getText().toString());
            }
        });
    }
}
