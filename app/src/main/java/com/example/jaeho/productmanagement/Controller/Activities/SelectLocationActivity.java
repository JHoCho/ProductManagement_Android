package com.example.jaeho.productmanagement.Controller.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jaeho.productmanagement.R;

public class SelectLocationActivity extends AppCompatActivity {
    Button btnStartCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        btnStartCheck = (Button)findViewById(R.id.btnStartCheck);

        btnStartCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectLocationActivity.this,CountItemActivity.class);
                //이부분에서 얻어온부분을 인텐트로 새 엑티비티로 넘겨주어야함.
                startActivity(intent);
            }
        });

    }

}
