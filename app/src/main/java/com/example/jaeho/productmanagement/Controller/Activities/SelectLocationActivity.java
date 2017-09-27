package com.example.jaeho.productmanagement.Controller.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.Model.DO.QRDO;
import com.example.jaeho.productmanagement.R;
import com.example.jaeho.productmanagement.utils.multiLevelExpandablelist.ParentLevelAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.jaeho.productmanagement.utils.Constants.hidProgressDialog;
import static com.example.jaeho.productmanagement.utils.Constants.tostost;

public class SelectLocationActivity extends AppCompatActivity {
    Button btnStartCheck;
    ExpandableListView linearListView1,linearListView2;
    InformationDAO myDao;
    boolean isSelected = false;
    public static String[] selectedSt1,selectedSt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        myDao = new NowUsingDAO(this);
        selectedSt1 = new String[]{"","",""};
        selectedSt2 = new String[]{"",""};
        btnStartCheck = (Button)findViewById(R.id.btnStartCheck);
        linearListView1 = (ExpandableListView)findViewById(R.id.linearListView1);
        linearListView2 = (ExpandableListView)findViewById(R.id.linearListView2);
        linearListView2.setVisibility(View.GONE);
        linearListView1.setAdapter(new ParentLevelAdapter(SelectLocationActivity.this,myDao.getTopLevelLocation(),1));
        linearListView1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                selectedSt1[0]=expandableListView.getItemAtPosition(i).toString();
                return false;
            }
        });

        btnStartCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelected==true) {
                    Intent intent = new Intent(SelectLocationActivity.this, CountItemActivity.class);
                    //이부분에서 얻어온부분을 인텐트로 새 엑티비티로 넘겨주어야함.
                    startActivity(intent);
                }else {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(SelectLocationActivity.this)
                            .setNegativeButton("취소",null)
                            .setTitle("진행하시겠습니까?");
                    TextView tv = new TextView(SelectLocationActivity.this);
                    if(selectedSt1[0]==""){
                        tv.setText("전체");
                    }else if(selectedSt1[1]==""){
                        tv.setText(selectedSt1[0]+"만 선택");
                    }else if(selectedSt1[2]==""){
                        tv.setText(selectedSt1[0]+" "+selectedSt1[1]+"선택");
                    }else {
                        tv.setText(selectedSt1[0]+" "+selectedSt1[1]+" "+selectedSt1[2]+"선택");
                    }
                    dlg.setView(tv);
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            tostost(selectedSt1[0]+" "+selectedSt1[1]+" "+selectedSt1[2]+"선택",SelectLocationActivity.this);
                            linearListView1.setVisibility(View.GONE);
                            linearListView2.setAdapter(new ParentLevelAdapter(SelectLocationActivity.this,myDao.getTopLevelPname(),2));
                            linearListView2.setVisibility(View.VISIBLE);
                        }
                    });
                    dlg.show();
                }
            }
        });
    }
}
