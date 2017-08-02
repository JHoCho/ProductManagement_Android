package com.example.jaeho.productmanagement.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;


import com.example.jaeho.productmanagement.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.R;
import com.example.jaeho.productmanagement.utils.Constants;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;


public class CheckQRActivity extends PermissionActivity
{
    SurfaceView cameraView;//화면 업데이트를 백그라운드로 처리해주는 서페이스뷰를 사용 할 예정
    CameraSource cameraSource;
    TextView barcodeInfo;
    BarcodeDetector barcodeDetector;
    String prev="";
    InformationDAO myDao;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_qr);
        myDao = new NowUsingDAO(this);
        cameraView = (SurfaceView)findViewById(R.id.camera_view);
        barcodeInfo = (TextView)findViewById(R.id.code_info);
/////////////////////////////////////텍스트박스 체인지 리스너///////////////////////////////////
        barcodeInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                View dlgView = View.inflate(CheckQRActivity.this, R.layout.dlg_check_qr, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(CheckQRActivity.this);
                dlg.setTitle("진행하시겠습니까?");
                dlg.setView(dlgView);
                dlg.setMessage(barcodeInfo.getText().toString());
                dlg.setNegativeButton("취소", null);
                dlg.setPositiveButton("진행", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dlg.show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
///////////////////////////////////////바코드부분/////////////////////////////////////////////////
        barcodeDetector = new BarcodeDetector.Builder(this)
                            .setBarcodeFormats(Barcode.QR_CODE)
                            .build();//바코드 디텍터에 빌더패턴으로 포멧 설정을 QR코드로 함. https://developers.google.com/vision/android/multi-tracker-tutorial 튜토리얼, AOS도 가지고있음.
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {// 셋 프로세서는 여러 쓰레드를 만들고 여러장을 한번에 비교합니다.
            @Override
            public void release() {

            }

            @Override//receiveDetections매서드 안에서 Detector.Detections클래스의 getDetectedItems매서드를 이용해 바코드의 스파스어레이 오브젝트를 불러오는데
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                // 스파스 어레이는 인티져를 객체로 매핑해준다. 이경우는  barcode오브젝트로 Create해주고 Return해준다

                if(barcodes.size()!=0 ){
                    //바코드사이즈가 있을경우 TextView.post형식으
                    barcodeInfo.post(new Runnable() {//텍스트뷰의 포스트 메서드를 이용하여
                        @Override
                        public void run() {
                                if(prev.equals(barcodes.valueAt(0).displayValue.toString()))
                                {
                                   //이전것과 같으면 아무런 동작을 하지 않고
                                } else {
                                    //다르면 receiveDetections가 UI thread에서 돌고 있지 않으므로 셋텍스트가 post안에서 돌아가야한다. post메서드 설명 : https://stackoverflow.com/questions/13840007/what-exactly-does-the-post-method-do
                                    //receiveDetections가 UI thread에서 돌고 있지 않으므로 셋텍스트가 post안에서 돌아가야한다//https://code.tutsplus.com/tutorials/reading-qr-codes-using-the-mobile-vision-api--cms-24680https://code.tutsplus.com/tutorials/reading-qr-codes-using-the-mobile-vision-api--cms-24680 참조
                                    barcodeInfo.setText(barcodes.valueAt(0).displayValue);
                                    prev = barcodeInfo.getText().toString();
                                }
                            }

                    });
                }
            }
        });
//////////////////////////////////////카메라 부분////////////////////////////////////////////////
        cameraSource = new CameraSource.Builder(this,barcodeDetector)
                                    .setRequestedPreviewSize(640,480)
                                    .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if(Constants.checkCameraPermission(CheckQRActivity.this)){
                    //카메라 확인
                    try {
                        cameraSource.start(cameraView.getHolder());

                    } catch (IOException ie) {
                        Log.e("CAMERA SOURCE", ie.getMessage());
                    }
                }
                else{

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
}
