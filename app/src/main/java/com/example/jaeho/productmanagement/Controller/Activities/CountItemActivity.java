package com.example.jaeho.productmanagement.Controller.Activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.example.jaeho.productmanagement.utils.Constants.MESSAGE_DONE;

public class CountItemActivity extends PermissionActivity {

    SurfaceView cameraView;//화면 업데이트를 백그라운드로 처리해주는 서페이스뷰를 사용 할 예정
    CameraSource cameraSource;
    TextView countedItemsTv;
    BarcodeDetector barcodeDetector;
    String prev = "";
    String nowv;
    InformationDAO myDao;
    String numsForCount;
    QRStringTokenizer qst;
    HashMap<String, Boolean> hashMap;
    ArrayList<String> rawsForChecking;
    Button finishBtn;
    int tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_item);
        myDao = new NowUsingDAO(this);
        if (myDao.getCurrentUser().getCompanyName().equals(null)) {
            Toast.makeText(getApplicationContext(), "세션이 종료되었습니다. 재접속 부탁 드립니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CountItemActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        cameraView = (SurfaceView) findViewById(R.id.countCameraView);
        countedItemsTv = (TextView) findViewById(R.id.countedItemsTv);
        finishBtn = (Button) findViewById(R.id.finishBtn);
        qst = new QRStringTokenizer();
        countedItemsTv.setText("0");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterFinished();
            }
        });
        if (bundle != null) {
            numsForCount = (String) bundle.get("numOfProduct");
            hashMap = (HashMap<String, Boolean>) bundle.get("hashMap");
            rawsForChecking = (ArrayList<String>) bundle.get("rawsForChecking");
            System.out.println("CountItemActivity.java hashMap" + hashMap.toString());
            countedItemsTv.setText("0" + "/" + numsForCount);
        }
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
                .setRequestedPreviewSize(640, 480)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (Constants.checkCameraPermission(CountItemActivity.this)) {
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

    //////////////////////////////////////핸들러 안에서 사용될 부분////////////////////////////////////////////////
    private void setNowv(String nowv) {
        this.nowv = nowv;
        stringValueChanged();
    }

    private void stringValueChanged() {
        qst.splitQR(nowv);
        if (myDao.getCurrentUser().getCompanyName().equals(null)) {
            Toast.makeText(getApplicationContext(), "세션이 종료되었습니다. 재접속 부탁 드립니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CountItemActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (myDao.getCurrentUser().getCompanyName().equals(qst.getSplitedQRDO().getCompanyName())) {
            //이부분에서 QR회사와 내 회사 비교하여 읽을지 말지 결정.if()
            if (containsKey(qst.getSplitedQRDO().getSerialNumber())) {
                //키가 멥에 있는지 확인
                if (isChecked(qst.getSplitedQRDO().getSerialNumber())) {
                    //키가 멥에 있어도 체크가 되었는지 확인 체크가 되었으면 무시하고
                } else {
                    //체크가 되지 않았다면 갯수 +1해줌 hashmap도 true로 바꿔줌
                    getAndPlusOne(qst.getSplitedQRDO().getSerialNumber());
                }
            }else {
                Toast.makeText(CountItemActivity.this, "해당위치에 있는 물건이 아닙니다", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "본인회사가 아님" + qst.getSplitedQRDO().getCompanyName().toString() + "회사꺼임", Toast.LENGTH_SHORT).show();
        }
        if (isFinished()) {
            afterFinished();
        } else {
            afterFinished();
        }
    }

    public void afterFinished() {
        if (isFinished()) {
            Toast.makeText(this, "검사가 완료되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            for (int i = 0; i < rawsForChecking.size(); i++) {
                if (hashMap.get(rawsForChecking.get(i)) == false) {
                    QRDO qrdo = new QRDO();
                    qrdo.setLocation("lost");
                    Calendar calendar = Calendar.getInstance();
                    String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
                    String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
                    if (Integer.parseInt(month) < 10) {
                        month = "0" + month;
                    }
                    if (Integer.parseInt(day) < 10) {
                        day = "0" + day;
                    }
                    String retDay = Integer.toString(calendar.get(Calendar.YEAR)) + "-" + month + "-" + day;
                    qrdo.setOutDate(retDay);
                    qrdo.setSerialNumber(rawsForChecking.get(i));
                    myDao.askChange(qrdo);
                }
            }
        }
        finish();
    }

    public boolean isFinished() {
        if (Integer.toString(tmp).equals(countedItemsTv.getText().toString().split("/"))) {
            return true;
        } else {
            return false;
        }
    }

    public void getAndPlusOne(String key) {
        String s[] = countedItemsTv.getText().toString().split("/");
        tmp = Integer.parseInt(s[0]);
        tmp = tmp + 1;
        countedItemsTv.setText(Integer.toString(tmp) + "/" + numsForCount);
        hashMap.put(key, true);
    }

    public boolean isChecked(String key) {
        if (hashMap.get(key) == null | false) {
            return false;
        } else {
            return true;
        }
    }

    public boolean containsKey(String key) {
        if (hashMap.get(key) == null) {
            return false;
        } else {
            return true;
        }
    }
}
