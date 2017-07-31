package com.example.jaeho.productmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.jaeho.productmanagement.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.DAOS.NowUsingDAO;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnSignin;
    EditText edtID, edtPW;
    InformationDAO myDao;
    InputMethodManager imm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDao = new NowUsingDAO(this);
        imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE) ;

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignin = (Button) findViewById(R.id.btnSignin);
        edtID = (EditText) findViewById(R.id.edtID);
        edtPW = (EditText) findViewById(R.id.edtPW);
        edtID.setHint(R.string.id_hint);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                myDao.checkSignIn(edtID.getText().toString().trim(),edtPW.getText().toString().trim());
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                myDao.makeAccount(edtID.getText().toString().trim(),edtPW.getText().toString().trim());
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        myDao.onStop();
    }
    private void hideKeyboard(){
        imm.hideSoftInputFromWindow(edtID.getWindowToken(),0);
        imm.hideSoftInputFromWindow(edtPW.getWindowToken(),0);
    }

}
