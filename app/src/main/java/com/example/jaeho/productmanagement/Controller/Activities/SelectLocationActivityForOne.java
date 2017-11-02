package com.example.jaeho.productmanagement.Controller.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.Model.DO.QRDO;
import com.example.jaeho.productmanagement.R;
import com.example.jaeho.productmanagement.utils.multiLevelExpandablelist.ParentLevelAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.jaeho.productmanagement.Controller.Activities.SelectLocationActivity.selectedSt1;
import static com.example.jaeho.productmanagement.Controller.Activities.SelectLocationActivity.selectedSt2;
import static com.example.jaeho.productmanagement.utils.Constants.tostost;

public class SelectLocationActivityForOne extends AppCompatActivity {
    Button btnStartCheck;
    ExpandableListView linearListView1, linearListView2;
    InformationDAO myDao;
    boolean isSelected = false;
    boolean type1 = false;
    HashMap<String, Boolean> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        myDao = new NowUsingDAO(this);
        if (myDao.getCurrentUser().getCompanyName().equals(null)) {
            Toast.makeText(getApplicationContext(), "세션이 종료되었습니다. 재접속 부탁 드립니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SelectLocationActivityForOne.this, MainActivity.class);
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
        btnStartCheck = (Button) findViewById(R.id.btnStartCheck);
        linearListView1 = (ExpandableListView) findViewById(R.id.linearListView1);
        linearListView2 = (ExpandableListView) findViewById(R.id.linearListView2);
        linearListView2.setVisibility(View.GONE);
        linearListView1.setAdapter(new ParentLevelAdapter(SelectLocationActivityForOne.this, myDao.getTopLevelLocation(), 1));
        linearListView1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                selectedSt1[0] = expandableListView.getItemAtPosition(i).toString();
                selectedSt1[1] = "";
                selectedSt1[2] = "";
                return false;
            }
        });

        btnStartCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type1 == false) {
                    if (selectedSt1[2] == "") {
                        tostost("전부 선택하셔야 합니다.", SelectLocationActivityForOne.this);
                    } else {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(SelectLocationActivityForOne.this)
                                .setNegativeButton("취소", null)
                                .setTitle("진행하시겠습니까?");
                        TextView tv = new TextView(SelectLocationActivityForOne.this);
                        tv.setText(selectedSt1[0] + " " + selectedSt1[1] + " " + selectedSt1[2] + "선택");
                        dlg.setView(tv);
                        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tostost(selectedSt1[0] + " " + selectedSt1[1] + " " + selectedSt1[2] + "선택", SelectLocationActivityForOne.this);
                                linearListView1.setVisibility(View.GONE);
                                linearListView2.setAdapter(new ParentLevelAdapter(SelectLocationActivityForOne.this, myDao.getTopLevelPname(), 2));
                                linearListView2.setVisibility(View.VISIBLE);
                                btnStartCheck.setText("해당 시리얼 확인");
                                linearListView2.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                                    @Override
                                    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                                        selectedSt2[0] = expandableListView.getItemAtPosition(i).toString();
                                        selectedSt2[1] = "";
                                        selectedSt2[2] = "";
                                        return false;
                                    }
                                });
                                type1 = true;
                            }
                        });
                        dlg.show();
                    }
                } else if (selectedSt1[2] == "" || selectedSt2[2] == "") {
                    tostost("전부 선택하셔야 합니다.", SelectLocationActivityForOne.this);
                } else {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(SelectLocationActivityForOne.this)
                            .setNegativeButton("취소", null)
                            .setTitle("아래 정보를 확인 하시겠습니까?");
                    TextView tv = new TextView(SelectLocationActivityForOne.this);

                    tv.setText(selectedSt2[0] + " " + selectedSt2[1] + " " + selectedSt2[2] + "선택");

                    dlg.setView(tv);
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            AlertDialog.Builder dlg = new AlertDialog.Builder(SelectLocationActivityForOne.this);
                            dlg.setTitle("QR내용을 확인 하시겠습니까?");
                            dlg.setMessage(myDao.getOneQrdo(selectedSt1, selectedSt2).getSerialNumber() + "확인하기");
                            dlg.setNegativeButton("취소", null);
                            dlg.setPositiveButton("진행", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AlertDialog.Builder dlg2 = new AlertDialog.Builder(SelectLocationActivityForOne.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                                            .setTitle(myDao.getOneQrdo(selectedSt1, selectedSt2).getProductName() + "의 정보")
                                            .setNegativeButton("취소", null);// 이부분에서 나우나 프리브를 초기화해줘야 다시 찍을수 있을거임.
                                    LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
                                    View view = layoutInflater.inflate(R.layout.dlg_qr_information, null);
                                    TextView dlg_qr_product_name_edt = (TextView) view.findViewById(R.id.dlg_qr_product_name_edt);
                                    final EditText dlg_qr_location_edt = (EditText) view.findViewById(R.id.dlg_qr_location_edt);
                                    final TextView dlg_qr_signalNo_edt = (TextView) view.findViewById(R.id.dlg_qr_signalNo_edt);
                                    final EditText dlg_qr_date_out_edt = (EditText) view.findViewById(R.id.dlg_qr_date_out_edt);
                                    TextView dlg_qr_price_edt = (TextView) view.findViewById(R.id.dlg_qr_price_edt);
                                    TextView dlg_qr_product_model_name_edt = (TextView) view.findViewById(R.id.dlg_qr_product_model_name_edt);
                                    TextView dlg_qr_product_admin_edt = (TextView) view.findViewById(R.id.dlg_qr_product_admin_edt);
                                    TextView dlg_qr_date_tv = (TextView) view.findViewById(R.id.dlg_qr_date_tv);

                                    dlg_qr_product_name_edt.setText(myDao.getOneQrdo(selectedSt1, selectedSt2).getProductName());
                                    dlg_qr_location_edt.setText(myDao.getOneQrdo(selectedSt1, selectedSt2).getLocation());
                                    dlg_qr_signalNo_edt.setText(myDao.getOneQrdo(selectedSt1, selectedSt2).getSerialNumber());
                                    dlg_qr_date_out_edt.setText("X");//반출일은 나중에 수정요함.
                                    dlg_qr_price_edt.setText(myDao.getOneQrdo(selectedSt1, selectedSt2).getPrice());
                                    dlg_qr_product_model_name_edt.setText(myDao.getOneQrdo(selectedSt1, selectedSt2).getDetailedProductName());
                                    dlg_qr_product_admin_edt.setText(myDao.getOneQrdo(selectedSt1, selectedSt2).getAdminID());
                                    dlg_qr_date_tv.setText(myDao.getOneQrdo(selectedSt1, selectedSt2).getAdminID());
                                    dlg2.setView(view);
                                    dlg2.setPositiveButton("수정 요청하기", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //위의정보들을 하나의 객체로 만들어 수정요청해야함 아직 중간관리자 급아이디 구현이 되어있지 않아 미 구현.
                                            QRDO qrdo = new QRDO();
                                            qrdo.setSerialNumber(myDao.getOneQrdo(selectedSt1, selectedSt2).getSerialNumber());
                                            qrdo.setLocation(dlg_qr_location_edt.getText().toString());
                                            qrdo.setOutDate(dlg_qr_date_out_edt.getText().toString());
                                            myDao.askChange(qrdo);
                                            finish();
                                        }
                                    });
                                    dlg2.show();

                                }
                            });
                            dlg.show();
                        }
                    });
                    dlg.show();
                }
            }
        });
    }
}
