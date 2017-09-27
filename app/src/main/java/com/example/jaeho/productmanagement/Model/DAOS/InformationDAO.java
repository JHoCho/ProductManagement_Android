package com.example.jaeho.productmanagement.Model.DAOS;

import android.content.Context;

import com.example.jaeho.productmanagement.Model.DAOS.Mysql.SQLiteDB;
import com.example.jaeho.productmanagement.utils.QNAActivitys.CustomQNAAdapter;
import com.example.jaeho.productmanagement.Model.DO.QNADO;
import com.example.jaeho.productmanagement.Model.DO.QRDO;
import com.example.jaeho.productmanagement.Model.DO.InformationQRDO;
import com.example.jaeho.productmanagement.Model.DO.UserDO;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 7. 20..
 */

public interface InformationDAO {//앱스트렉으로 바꾼다음 인스턴스로 넣어주는걸 둘다 넣고 만들어놓고 주석처리할것.
    void insert(InformationQRDO qr);
    void deleteInformation(int id);
    ArrayList<InformationQRDO> getInformation();
    ArrayList<InformationQRDO> getInformationByQR(InformationQRDO qr);
    CustomQNAAdapter getAdapter();
    CustomQNAAdapter getMyAdapter();
    void checkSignIn(final String id, final String pw);//아이디 비번으로 로그인 여부
    void checkSignUp(final String id, final String pw);//아이디 비번으로 회원가입여부
    void updateInformation(InformationQRDO qr);
    void makeAccount(final String id,final String pw);
    void accessUserInform(final String id,final String pw);
    void onStop();
    void addQna(QNADO qnado);
    void readQna(QNADO qnado);
    void deleteQna(QNADO qnado);
    void addQR(QRDO qrdo);
    ArrayList getTopLevelLocation();
    ArrayList getMiddleLevelLocation(String building);
    ArrayList getLowLevelLocation(String building,String floor);
    ArrayList<String> getTopLevelPname();
    ArrayList<String> getMiddleLevelPname(String productName);
    ArrayList<String> getLowLevelPname(String productName,String detailedProductName);
    UserDO getCurrentUser();
    String getUserName();
    String getUserEmail();
    int getNumOfRow();
}
