package com.example.jaeho.productmanagement;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import static com.example.jaeho.productmanagement.ApplicationClass.MY_PERMISSIONS_REQUEST_CAMERA;


public class CheckQRActivity extends AppCompatActivity
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
    private void checkCameraPermission(){
        //Activity에서 실행되는 경
        // 이부분이 API설명부에서는 그냥 Menifast로 되어있지만 실제코딩은 Android.Menifest.permission (3번쨰꺼)해야 인식됩니다
        if(ContextCompat.checkSelfPermission(CheckQRActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            //패키지에 대한 퍼미션이 없는경우(거절 상태일때)
            // 사용자가 다이얼로그를 보고 승낙을 누르지 않았고 거절을 누른 경우를 캐치하기 위한 매서드입니다
            // 다이얼로그의 경우 경우의수가 1.승낙 2.거부 3.다시 보지 않기 체크후 거절 로 나눌 수 있고 2인 경우
            // 2번의 경우 다시 앱을 켤때 ActivityCompat.shouldShowRequestPermissionRationale()메서드를 사용하면 트루가 리턴이되어
            // 사용자가 거절한 이력이 있나 없나를 알 수 있다 최초앱 접속시 뜨는 다이얼로그는 거절 이력이 없기 때문에 false가 리턴된다
            // 3번의 경우에는 requestPermission 메서드를 쓰더라도 구글이 만든 권한설정 다이얼로그가 뜨지 않으므로 프로그래머가 유도해야한다.

            if(android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(CheckQRActivity.this,Manifest.permission.CAMERA)){
                //동기적으로 유저에게 보일 설명을 쓰는 곳 이 쓰레드는 유저의 응답을 기다리며 유저가 설명을 본후 다시 퍼미션을 주는것을 물어봄
                // 거절한 이력이 있으나 다시보지 않기에 체크가 안 되어 있는 경우

            }else{
                //다시보지 않기에 체크한 후 거절을 한 경우.
                //사용자에게 접근권한 설정을 요구하는 다이얼로그를 띄웁니다.
                 //requestPermission 메서드는 매개변수로써 Context String int변수를원하며 int 변수는 후에 onRequestPermissionsResult()메서드에 결과물이 전달될 시 결과물들을 구분짓는 idx번호입니다
            }
            android.support.v4.app.ActivityCompat.requestPermissions(CheckQRActivity.this,new String[]{Manifest.permission.CAMERA},MY_PERMISSIONS_REQUEST_CAMERA);
            //사용자에게 접근 권한 설정을 요구하는 다이얼로그를 띄우며 만약 사용자가 다시보지 않기에 체크를 했을경우 권한 설정 다이얼로그가 뜨지 않고 곧바로 onRequestPermissionResult가 실행 된다

        }else{
            //패키지에대한 퍼미션이 있는경우 카메라를 사용합니다.
            try {
                cameraSource.start(cameraView.getHolder());

            } catch (IOException ie) {
                Log.e("CAMERA SOURCE", ie.getMessage());
            }
        }

    }
    //사용자가 무얼 선택하던 무조건 타는 메서드.
    public void onRequestPermissionsResult(int requestCode, String permissions[],int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CAMERA:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //해당 퍼미션이 그렌트된 경우
                    Intent intent = new Intent(CheckQRActivity.this,CheckQRActivity.class);
                    startActivity(intent);
                }else{
                    // 퍼미션이 디나이된 경우
                    // 퍼미션이 디나이된 경우 프로그램의 권한부분으로 바로 넘겨 권한 설정을 유도.
                   // Log.d("Permission always denyed");
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        .setData(Uri.parse("package:"+ getApplicationContext().getPackageName()));
                    startActivity(intent);
                }
                return;
            }
            //다른 퍼미션을 체크할 부분
        }
    }
    private void usingCamera(){
        checkCameraPermission();//체크안에 퍼미션이있다면 카메라 소스가 돌아가고 아니라면 허가하는 다이얼로그를 띄우거나 세팅의 권한쪽으로 넘겨버리도록 코딩되어있음 읽기 편하도록 유징 카메라라는 메서드로 감싼것.
    }
}
