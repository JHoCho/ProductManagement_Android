package com.example.jaeho.productmanagement.Controller.Activities;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.utils.QNAActivitys.CustomQNAAdapter;
import com.example.jaeho.productmanagement.Model.DO.QNADO;
import com.example.jaeho.productmanagement.R;

public class QNAActivity extends AppCompatActivity {
    ListView qnaListView;
    CustomQNAAdapter mAdapter;
    InformationDAO myDao;
    Button toQNAActivitybtn;
    RadioGroup QNARadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna);
        qnaListView = (ListView)findViewById(R.id.qnaListView);
        toQNAActivitybtn = (Button)findViewById(R.id.toQNAActivitybtn);
        QNARadioGroup = (RadioGroup)findViewById(R.id.QNARadioGroup);
        myDao = new NowUsingDAO(this);
        if(myDao.getCurrentUser().getCompanyName().equals(null)){
            Toast.makeText(getApplicationContext(), "세션이 종료되었습니다. 재접속 부탁 드립니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        toQNAActivitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),QNAAddActivity.class);
                startActivity(intent);
            }
        });
        QNARadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i==R.id.seeAllQNA){
                    initAdapter();//어레이 리스트에 파이어베이스의 데이터들을 가져오고 QNADO모양으로 넣도록함.
                    qnaListView.setAdapter(mAdapter);
                    qnaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            QNADO selectedItem =(QNADO)qnaListView.getItemAtPosition(position);
                            myDao.readQna(selectedItem);
                        }
                    });
                }else if(i == R.id.seeMyQNA){
                    initMyQNAAdapter();
                    qnaListView.setAdapter(mAdapter);
                    qnaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            QNADO selectedItem =(QNADO)qnaListView.getItemAtPosition(position);
                            myDao.readQna(selectedItem);
                        }
                    });
                }
            }
        });
        RadioButton seeAllQNA = (RadioButton)findViewById(R.id.seeAllQNA);
        seeAllQNA.setChecked(true);
    }
    public void initAdapter(){
        mAdapter = myDao.getAdapter();
    }
    public void initMyQNAAdapter(){mAdapter=myDao.getMyAdapter();}

}
