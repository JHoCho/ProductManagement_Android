package com.example.jaeho.productmanagement.Controller.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.R;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    InformationDAO myDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDao = new NowUsingDAO(this);
        if (myDao.getCurrentUser().getCompanyName().equals(null)) {
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
                        .setNegativeButton("취소", null)
                        .setTitle(Integer.toString(year) + "년" + Integer.toString(month) + "월" + Integer.toString(dayOfMonth) + "일 의 할일");
                dlg.setPositiveButton("확인 ", null);//null부분에 클릭되었을시 부분 추가가능
                //dlg.setView();  하여 연월일로 받은정보를이용 그날 할일 체크후 바로 가능하도록 할 예정. 뷰에
                dlg.show();
            }
        });
    }
}
