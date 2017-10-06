package com.example.jaeho.productmanagement.utils;

import android.*;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by jaeho on 2017. 8. 2..
 */

public class Constants extends Application {

    public static ProgressDialog prdlg;
    //스테틱 인트 변수로 스스로 선언해 줘야하는 부분입니다 리퀘스트 코드이며 onRequestPermissionResult에서 사용됩니다 이것은 사용자가 어떤 선택을 했는지 넘겨줍니다 숫자는 무엇을 쓰던 상관이 없습니다
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1122;
    public static final int MESSAGE_DONE = 15885588;

    public static boolean checkCameraPermission(AppCompatActivity activity){
        //Activity에서 실행되는 경
        // 이부분이 API설명부에서는 그냥 Menifast로 되어있지만 실제코딩은 Android.Menifest.permission (3번쨰꺼)해야 인식됩니다
        if(ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            //패키지에 대한 퍼미션이 없는경우(거절 상태일때)
            // 사용자가 다이얼로그를 보고 승낙을 누르지 않았고 거절을 누른 경우를 캐치하기 위한 매서드입니다
            // 다이얼로그의 경우 경우의수가 1.승낙 2.거부 3.다시 보지 않기 체크후 거절 로 나눌 수 있고 2인 경우
            // 2번의 경우 다시 앱을 켤때 ActivityCompat.shouldShowRequestPermissionRationale()메서드를 사용하면 트루가 리턴이되어
            // 사용자가 거절한 이력이 있나 없나를 알 수 있다 최초앱 접속시 뜨는 다이얼로그는 거절 이력이 없기 때문에 false가 리턴된다
            // 3번의 경우에는 requestPermission 메서드를 쓰더라도 구글이 만든 권한설정 다이얼로그가 뜨지 않으므로 프로그래머가 유도해야한다.

            if(android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.CAMERA)){
                //동기적으로 유저에게 보일 설명을 쓰는 곳 이 쓰레드는 유저의 응답을 기다리며 유저가 설명을 본후 다시 퍼미션을 주는것을 물어봄
                // 거절한 이력이 있으나 다시보지 않기에 체크가 안 되어 있는 경우

            }else{
                //다시보지 않기에 체크한 후 거절을 한 경우.
                //사용자에게 접근권한 설정을 요구하는 다이얼로그를 띄웁니다.
                //requestPermission 메서드는 매개변수로써 Context String int변수를원하며 int 변수는 후에 onRequestPermissionsResult()메서드에 결과물이 전달될 시 결과물들을 구분짓는 idx번호입니다
            }
            android.support.v4.app.ActivityCompat.requestPermissions(activity,new String[]{android.Manifest.permission.CAMERA},MY_PERMISSIONS_REQUEST_CAMERA);
            //사용자에게 접근 권한 설정을 요구하는 다이얼로그를 띄우며 만약 사용자가 다시보지 않기에 체크를 했을경우 권한 설정 다이얼로그가 뜨지 않고 곧바로 onRequestPermissionResult가 실행 된다
            return false;
        }else{
            //패키지에대한 퍼미션이 있는경우 카메라를 사용합니다.
            return true;

        }

    }

    public static void showProgressDialog(Context context) {
        prdlg = ProgressDialog.show(context, "잠시만 기다려주세요", "서버와 통신중 입니다.", true);
    }
    public static void tostost(String s,Context context){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
    public static void hidProgressDialog() {
        prdlg.dismiss();
    }

}
