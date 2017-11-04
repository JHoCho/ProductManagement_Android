package com.example.jaeho.productmanagement.Controller.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.R;
import com.example.jaeho.productmanagement.utils.CurentUser;

import static com.example.jaeho.productmanagement.utils.Constants.hidProgressDialog;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    InformationDAO myDao;
    public static TextView calendarTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hidProgressDialog();
        myDao = new NowUsingDAO(this);
        try{
            CurentUser.getInstance().getCompanyName();
        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(), "세션이 종료되었습니다. 재접속 부탁 드립니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_calendar);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {

                month = month + 1;
                AlertDialog.Builder dlg = new AlertDialog.Builder(CalendarActivity.this)
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                calendarTv=null;
                            }
                        })
                        .setTitle(Integer.toString(year) + "년" + Integer.toString(month) + "월" + Integer.toString(dayOfMonth) + "일 의 할일");
                dlg.setPositiveButton("확인 ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        calendarTv=null;
                    }
                });//null부분에 클릭되었을시 부분 추가가능
                if (calendarTv == null) {
                    calendarTv = new TextView(CalendarActivity.this);
                }
                calendarTv.setText("");
                myDao.getSchedule(Integer.toString(year), Integer.toString(month), Integer.toString(dayOfMonth));
                //여기서 요청
                dlg.setView(calendarTv);
                dlg.show();
            }
        });
    }
}
