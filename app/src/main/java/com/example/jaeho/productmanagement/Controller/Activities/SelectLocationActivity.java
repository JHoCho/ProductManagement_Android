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
import android.widget.Toast;

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
import java.util.HashMap;

import static com.example.jaeho.productmanagement.utils.Constants.hidProgressDialog;
import static com.example.jaeho.productmanagement.utils.Constants.tostost;

public class SelectLocationActivity extends AppCompatActivity {
    Button btnStartCheck;
    ExpandableListView linearListView1,linearListView2;
    InformationDAO myDao;
    boolean isSelected = false;
    boolean type1 = false;
    HashMap<String,Boolean> hashMap;
    public static String[] selectedSt1,selectedSt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        myDao = new NowUsingDAO(this);
        if(myDao.getCurrentUser().getCompanyName().equals(null)){
            Toast.makeText(getApplicationContext(), "세션이 종료되었습니다. 재접속 부탁 드립니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SelectLocationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        hashMap = new HashMap<>();
        if (selectedSt1 == null) {
            selectedSt1 = new String[]{"", "", ""};
        }
        if (selectedSt2 == null) {
            selectedSt2 = new String[]{"", "", ""};
        }
        btnStartCheck = (Button)findViewById(R.id.btnStartCheck);
        linearListView1 = (ExpandableListView)findViewById(R.id.linearListView1);
        linearListView2 = (ExpandableListView)findViewById(R.id.linearListView2);
        linearListView2.setVisibility(View.GONE);
        linearListView1.setAdapter(new ParentLevelAdapter(SelectLocationActivity.this,myDao.getTopLevelLocation(),1));
        linearListView1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                selectedSt1[0]=expandableListView.getItemAtPosition(i).toString();
                selectedSt1[1]="";
                selectedSt1[2]="";
                return false;
            }
        });

        btnStartCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type1==false){
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
                            btnStartCheck.setText("개수 세기 시작");
                            linearListView2.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                                @Override
                                public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                                    selectedSt2[0]=expandableListView.getItemAtPosition(i).toString();
                                    selectedSt2[1]="";
                                    selectedSt2[2]="";
                                    return false;
                                }
                            });
                            type1=true;
                        }
                    });
                    dlg.show();
                }else {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(SelectLocationActivity.this)
                            .setNegativeButton("취소",null)
                            .setTitle("진행하시겠습니까?");
                    TextView tv = new TextView(SelectLocationActivity.this);
                    if(selectedSt2[0]==""){
                        tv.setText("전체");
                    }else if(selectedSt2[1]==""){
                        tv.setText(selectedSt2[0]+"만 선택");
                    }else if(selectedSt2[2]==""){
                        tv.setText(selectedSt2[0]+" "+selectedSt2[1]+"선택");
                    }else {
                        tv.setText(selectedSt2[0]+" "+selectedSt2[1]+" "+selectedSt2[2]+"선택");
                    }
                    dlg.setView(tv);
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(SelectLocationActivity.this, CountItemActivity.class);
                            //이부분에서 얻어온부분을 인텐트로 새 엑티비티로 넘겨주어야함.
                            intent.putExtra("numOfProduct",Integer.toString(myDao.getNumOfRow()));//쿼리로 질의해서 얻은 개수를 넘겨 다음페이지에서 세도록 만듬 갯수는 넘겼으나 아직 해당 리스트는 안넘김
                            ArrayList<String> rawsForChecking = myDao.getRawsForChecking();
                            for(int j =0;j<rawsForChecking.size();j++){
                                hashMap.put(rawsForChecking.get(j),false);
                            }
                            intent.putExtra("hashMap",hashMap);
                            startActivity(intent);
                        }
                    });
                    dlg.show();
                }
            }
        });
    }
}
