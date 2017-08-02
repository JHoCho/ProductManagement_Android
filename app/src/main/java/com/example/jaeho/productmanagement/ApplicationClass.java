package com.example.jaeho.productmanagement;

import android.app.Application;

/**
 * Created by jaeho on 2017. 8. 2..
 */

public class ApplicationClass extends Application {

    //스테틱 인트 변수로 스스로 선언해 줘야하는 부분입니다 리퀘스트 코드이며 onRequestPermissionResult에서 사용됩니다 이것은 사용자가 어떤 선택을 했는지 넘겨줍니다 숫자는 무엇을 쓰던 상관이 없습니다
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1122;
}
