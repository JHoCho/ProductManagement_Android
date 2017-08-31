package com.example.jaeho.productmanagement.View.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

import com.example.jaeho.productmanagement.R;

import static com.example.jaeho.productmanagement.utils.Constants.tostost;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(CalendarActivity.this)
                        .setNegativeButton("취소",null)
                        .setTitle(Integer.toString(year)+"년"+Integer.toString(month)+"월"+Integer.toString(dayOfMonth)+"일 의 할일");
                dlg.setPositiveButton("확인 ",null);//null부분에 클릭되었을시 부분 추가가능
                //dlg.setView();  하여 연월일로 받은정보를이용 그날 할일 체크후 바로 가능하도록 할 예정.
                dlg.show();
            }
        });
    }
}
