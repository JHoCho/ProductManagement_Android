package com.example.jaeho.productmanagement;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;


import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;


public class CheckQRActivity extends AppCompatActivity {
    SurfaceView cameraView;//화면 업데이트를 백그라운드로 처리해주는 서페이스뷰를 사용 할 예정
    CameraSource cameraSource;
    TextView barcodeInfo;
    BarcodeDetector barcodeDetector;
    //스테틱 인트 변수로 스스로 선언해 줘야하는 부분입니다 리퀘스트 코드이며 onRequestPermissionResult에서 사용됩니다 이것은 사용자가 어떤 선택을 했는지 넘겨줍니다 숫자는 무엇을 쓰던 상관이 없습니다
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1122;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_qr);
        cameraView = (SurfaceView)findViewById(R.id.camera_view);
        barcodeInfo = (TextView)findViewById(R.id.code_info);


        barcodeDetector = new BarcodeDetector.Builder(this)
                            .setBarcodeFormats(Barcode.QR_CODE)
                            .build();//바코드 디텍터에 빌더패턴으로 QR코드 등을 삽입

        cameraSource = new CameraSource.Builder(this,barcodeDetector)
                                    .setRequestedPreviewSize(640,480)
                                    .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                usingCamera();
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
    private void checkPermission(){
        //Activity에서 실행되는 경
        // 이부분이 API설명부에서는 그냥 Menifast로 되어있지만 실제코딩은 Android.Menifest.permission (3번쨰꺼)해야 인식됨
        if(ContextCompat.checkSelfPermission(CheckQRActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            if(android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(CheckQRActivity.this,Manifest.permission.READ_CONTACTS)){
                //동기적으로 유저에게 보일 설명을 쓰는 곳 이 쓰레드는 유저의 응답을 기다리며 유저가 설명을 본후 다시 퍼미션을 주는것을 물어봄

            }else{
                android.support.v4.app.ActivityCompat.requestPermissions(CheckQRActivity.this,new String[]{Manifest.permission.CAMERA},MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }

    private void usingCamera(){
        try {
            checkPermission();
            cameraSource.start(cameraView.getHolder());
        } catch (IOException ie) {
            Log.e("CAMERA SOURCE", ie.getMessage());
        }
    }

    public void onRequestPermissionsPresult(int requestCode, String permissions[],int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CAMERA:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //퍼미션이 그렌트 됐을때 나올 펑션
                    usingCamera();
                }else{
                    //파미션이 디나이 됐을때 나올 펑션
                   // Log.d("Permission always denyed");
                    Intent intent = new Intent(CheckQRActivity.this,CheckQRActivity.class);
                    startActivity(intent);
                    finish();
                }
                return;
            }
            //다른 퍼미션을 체크할 부분
        }
    }

}
