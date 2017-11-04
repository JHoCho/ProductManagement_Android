package com.example.jaeho.productmanagement.Controller.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.jaeho.productmanagement.utils.CurentUser;

import static com.example.jaeho.productmanagement.utils.Constants.MY_PERMISSIONS_REQUEST_CAMERA;

/**
 * Created by jaeho on 2017. 8. 2..
 */

abstract class PermissionActivity extends AppCompatActivity{

    //사용자가 무얼 선택하던 무조건 타는 메서드.
    public void onRequestPermissionsResult(int requestCode, String permissions[],int[] grantResults){
        try{
            CurentUser.getInstance().getCompanyName();
        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(), "세션이 종료되었습니다. 재접속 부탁 드립니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CAMERA:{
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //해당 퍼미션이 그렌트된 경우
                    Intent intent = new Intent(getApplicationContext(),getClass());
                    startActivity(intent);
                    finish();
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

}
