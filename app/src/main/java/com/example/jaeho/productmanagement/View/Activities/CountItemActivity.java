package com.example.jaeho.productmanagement.View.Activities;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DAOS.NowUsingDAO;
import com.example.jaeho.productmanagement.R;
import com.example.jaeho.productmanagement.utils.Constants;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import static com.example.jaeho.productmanagement.utils.Constants.MESSAGE_DONE;

public class CountItemActivity extends AppCompatActivity {

    SurfaceView cameraView;//화면 업데이트를 백그라운드로 처리해주는 서페이스뷰를 사용 할 예정
    CameraSource cameraSource;
    TextView countedItemsTv;
    BarcodeDetector barcodeDetector;
    String prev="";
    String nowv;
    InformationDAO myDao;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_item);
        myDao = new NowUsingDAO(this);
        cameraView = (SurfaceView)findViewById(R.id.countCameraView);
        countedItemsTv = (TextView)findViewById(R.id.countedItemsTv);
        countedItemsTv.setText("0");
///////////////////////////////////////쓰레드로 보낸 QR 받아 처리하는 부분/////////////////////////////////////////////////
        final Handler mHandler = new Handler(Looper.getMainLooper()){//메인쓰레드로 처리할 것임. 워커쓰레드로 하면 좋으나 아직 사용법을 모름
            public void handleMessage(Message m){
                switch (m.what){
                    case MESSAGE_DONE:
                        setNowv(m.obj.toString());//메세지 던일때는 카메라에서 뭔가를 감지했을때 보내는 것이므로 obj안에 스트링 객체가 있을것을 인지하고 메서드를 이용해 처리해준다.
                        prev =nowv;
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
            @Override//receiveDetections매서드 안에서 Detector.Detections클래스의 getDetectedItems매서드를 이용해 바코드의 스파스어레이 오브젝트를 불러오는데
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                // 스파스 어레이는 인티져를 객체로 매핑해준다. 이경우는  barcode오브젝트로 Create해주고 Return해준다
                if(barcodes.size()!=0 ){
                    if(prev.equals(barcodes.valueAt(0).displayValue.toString()))
                    {
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
        cameraSource = new CameraSource.Builder(this,barcodeDetector)
                .setRequestedPreviewSize(640,480)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if(Constants.checkCameraPermission(CountItemActivity.this)){
                    //카메라 퍼미션 확인
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

    //////////////////////////////////////핸들러 안에서 사용될 부분////////////////////////////////////////////////
    private void setNowv(String nowv){
        this.nowv = nowv;
        stringValueChanged();
    }
    private void stringValueChanged(){
        AlertDialog.Builder dlg = new AlertDialog.Builder(CountItemActivity.this);
        dlg.setTitle("진행하시겠습니까?");
        dlg.setMessage(nowv);
        dlg.setNegativeButton("취소", null);
        dlg.setPositiveButton("진행", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getAndPlusOne();
            }
        });
        dlg.show();
    }
    public void getAndPlusOne(){
        Integer tmp = Integer.parseInt(countedItemsTv.getText().toString());
        tmp = tmp+1;
        countedItemsTv.setText(tmp.toString());
    }

}
