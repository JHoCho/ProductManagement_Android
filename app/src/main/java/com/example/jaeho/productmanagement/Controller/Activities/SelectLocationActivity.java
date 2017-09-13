package com.example.jaeho.productmanagement.Controller.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.example.jaeho.productmanagement.R;
import com.example.jaeho.productmanagement.utils.multiLevelExpandableListView.TopLevel;

public class SelectLocationActivity extends AppCompatActivity {
    Button btnStartCheck;
    ExpandableListView linearListView1,linearListView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        btnStartCheck = (Button)findViewById(R.id.btnStartCheck);
        linearListView1 = (ExpandableListView)findViewById(R.id.linearListView1);
        linearListView2 = (ExpandableListView)findViewById(R.id.linearListView2);
        linearListView1.setAdapter(new TopLevel(this,1));
        linearListView2.setAdapter(new TopLevel(this,2));
        btnStartCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectLocationActivity.this,CountItemActivity.class);
                //이부분에서 얻어온부분을 인텐트로 새 엑티비티로 넘겨주어야함.
                startActivity(intent);
            }
        });ㄴ
    }
}
