package com.example.jaeho.productmanagement.Controller.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.Model.DO.QRDO;
import com.example.jaeho.productmanagement.R;
import com.example.jaeho.productmanagement.utils.Constants;

import com.example.jaeho.productmanagement.utils.QRStringTokenizer;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import static com.example.jaeho.productmanagement.Controller.Activities.SelectLocationActivity.selectedSt1;
import static com.example.jaeho.productmanagement.Controller.Activities.SelectLocationActivity.selectedSt2;
import static com.example.jaeho.productmanagement.utils.Constants.MESSAGE_DONE;


public class CheckQRActivity extends PermissionActivity {
    SurfaceView cameraView;//화면 업데이트를 백그라운드로 처리해주는 서페이스뷰를 사용 할 예정
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    String prev = "";
    String nowv;
    InformationDAO myDao;
    QRStringTokenizer qst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_qr);
        myDao = new NowUsingDAO(this);
        cameraView = (SurfaceView) findViewById(R.id.camera_view);
        qst = new QRStringTokenizer();

///////////////////////////////////////쓰레드로 보낸 QR 받아 처리하는 부분/////////////////////////////////////////////////

        final Handler mHandler = new Handler(Looper.getMainLooper()) {//메인쓰레드로 처리할 것임. 워커쓰레드로 하면 좋으나 아직 사용법을 모름
            public void handleMessage(Message m) {
                switch (m.what) {
                    case MESSAGE_DONE:
                        setNowv(m.obj.toString());//메세지 던일때는 카메라에서 뭔가를 감지했을때 보내는 것이므로 obj안에 스트링 객체가 있을것을 인지하고 메서드를 이용해 처리해준다.
                        prev = nowv;
                        break;
                }
            }
        };
///////////////////////////////////////바코드 인식 부분/////////////////////////////////////////////////
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();//바코드 디텍터에 빌더패턴으로 포멧 설정을 QR코드로 함. https://developers.google.com/vision/android/multi-tracker-tutorial 튜토리얼, AOS도 가지고있음.
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {// 셋 프로세서는 여러 쓰레드를 만들고 여러장을 한번에 비교합니다.
            @Override
            public void release() {

            }

            @Override
//receiveDetections매서드 안에서 Detector.Detections클래스의 getDetectedItems매서드를 이용해 바코드의 스파스어레이 오브젝트를 불러오는데
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                // 스파스 어레이는 인티져를 객체로 매핑해준다. 이경우는  barcode오브젝트로 Create해주고 Return해준다
                if (barcodes.size() != 0) {
                    if (prev.equals(barcodes.valueAt(0).displayValue.toString())) {
                        //이전것과 같으면 아무런 동작을 하지 않고
                    } else {
                        //쓰레드로 동작하기에 그냥 처리하면 되지않고 메세지로 보내 핸들러에서 큐로 처리해줘야한다.
                        Message message = mHandler.obtainMessage(MESSAGE_DONE);
                        message.obj = barcodes.valueAt(0).displayValue;
                        mHandler.sendMessage(message);
                        //위 부분이 아래부분과 같은 뜻이나 쓰레드이기에 핸들러에서 큐로 처리해준다 (루퍼)
                        //setNowv(barcodes.valueAt(0).displayValue);
                        //prev =nowv;
                    }
                }
            }
        });
//////////////////////////////////////카메라 부분////////////////////////////////////////////////

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1024, 768)//640-480->1024-768
                .setAutoFocusEnabled(true)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (Constants.checkCameraPermission(CheckQRActivity.this)) {
                    //카메라 퍼미션 확인
                    try {

                        cameraSource.start(cameraView.getHolder());


                    } catch (IOException ie) {
                        Log.e("CAMERA SOURCE", ie.getMessage());
                    }
                } else {


                }
            }


            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

    }

    //////////////////////////////////////텍스트 뷰 안에서 사용될 부분////////////////////////////////////////////////
    private void setNowv(String nowv) {
        this.nowv = nowv;
        stringValueChanged();
    }

    private void stringValueChanged() {
        qst.splitQR(nowv);
        if(myDao.getCurrentUser().getCompanyName().equals(null)){
            Toast.makeText(getApplicationContext(), "세션이 종료되었습니다. 재접속 부탁 드립니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CheckQRActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else if (myDao.getCurrentUser().getCompanyName().equals(qst.getSplitedQRDO().getCompanyName())) {
            //이부분에서 QR회사와 내 회사 비교하여 읽을지 말지 결정.if()
            AlertDialog.Builder dlg = new AlertDialog.Builder(CheckQRActivity.this);
            dlg.setTitle("QR내용을 확인 하시겠습니까?");
            dlg.setMessage(qst.getSplitedQRDO().getProductName() + "확인하기");
            dlg.setNegativeButton("취소", null);
            dlg.setPositiveButton("진행", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog.Builder dlg2 = new AlertDialog.Builder(CheckQRActivity.this,R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                            .setTitle(qst.getSplitedQRDO().getProductName() + "의 정보")
                            .setNegativeButton("취소", null);// 이부분에서 나우나 프리브를 초기화해줘야 다시 찍을수 있을거임.
                    LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
                    View view = layoutInflater.inflate(R.layout.dlg_qr_information, null);
                    TextView dlg_qr_product_name_edt = (TextView) view.findViewById(R.id.dlg_qr_product_name_edt);
                    final EditText dlg_qr_location_edt = (EditText) view.findViewById(R.id.dlg_qr_location_edt);
                    TextView dlg_qr_signalNo_edt = (TextView) view.findViewById(R.id.dlg_qr_signalNo_edt);
                    final EditText dlg_qr_date_out_edt = (EditText) view.findViewById(R.id.dlg_qr_date_out_edt);
                    TextView dlg_qr_price_edt = (TextView) view.findViewById(R.id.dlg_qr_price_edt);
                    TextView dlg_qr_product_model_name_edt = (TextView) view.findViewById(R.id.dlg_qr_product_model_name_edt);
                    TextView dlg_qr_product_admin_edt = (TextView) view.findViewById(R.id.dlg_qr_product_admin_edt);
                    TextView dlg_qr_date_tv = (TextView) view.findViewById(R.id.dlg_qr_date_tv);

                    dlg_qr_product_name_edt.setText(qst.getSplitedQRDO().getProductName());
                    dlg_qr_signalNo_edt.setText(qst.getSplitedQRDO().getSerialNumber());
                    dlg_qr_date_out_edt.setText("X");//반출일은 나중에 수정요함.
                    dlg_qr_price_edt.setText(qst.getSplitedQRDO().getPrice());
                    dlg_qr_product_model_name_edt.setText(qst.getSplitedQRDO().getDetailedProductName());
                    dlg_qr_product_admin_edt.setText(qst.getSplitedQRDO().getAdminID());
                    dlg_qr_date_tv.setText(qst.getSplitedQRDO().getAdminID());
                    qst.getSplitedQRDO().setLocation();
                    dlg_qr_location_edt.setText(qst.getSplitedQRDO().getLocation());
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
                        }
                    });
                    dlg2.show();

                }
            });
            dlg.show();
        } else {
            Toast.makeText(getApplicationContext(), "본인회사가 아님" + qst.getSplitedQRDO().getCompanyName().toString() + "회사꺼임", Toast.LENGTH_SHORT).show();
        }
    }
}
