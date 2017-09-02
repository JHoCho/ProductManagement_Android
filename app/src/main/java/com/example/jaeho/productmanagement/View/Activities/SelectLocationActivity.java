package com.example.jaeho.productmanagement.View.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.jaeho.productmanagement.R;
import com.example.jaeho.productmanagement.utils.MyExpandableList.ThreeLevelExpandableListAdapter;

public class SelectLocationActivity extends AppCompatActivity {
    Button btnStartCheck;
    ExpandableListView expandableLv1,expandableLv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        btnStartCheck = (Button)findViewById(R.id.btnStartCheck);
        expandableLv1 = (ExpandableListView)findViewById(R.id.expandableLv1);
        expandableLv2 = (ExpandableListView)findViewById(R.id.expandableLv2);

        btnStartCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectLocationActivity.this,CountItemActivity.class);
                //파이어베이스에서 얻어온부분을 인텐트로 새 엑티비티에 넘겨주어야함.

                startActivity(intent);
            }
        });
        expandableLv1.setAdapter(new ThreeLevelExpandableListAdapter(this));
        expandableLv2.setAdapter(new ThreeLevelExpandableListAdapter(this));
    }

}
