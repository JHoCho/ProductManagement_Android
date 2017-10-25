package com.example.jaeho.productmanagement.Model.DAOS;

import com.example.jaeho.productmanagement.Model.DO.QNADO;
import com.example.jaeho.productmanagement.Model.DO.QRDO;
import com.example.jaeho.productmanagement.Model.DO.UserDO;
import com.example.jaeho.productmanagement.utils.QNAActivitys.CustomQNAAdapter;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 7. 20..
 */

public interface InformationDAO {
    CustomQNAAdapter getAdapter();
    CustomQNAAdapter getMyAdapter();
    void checkSignIn(final String id, final String pw);
    void makeAccount(final String id,final String pw);
    void onStop();
    void addQna(QNADO qnado);
    void readQna(QNADO qnado);
    void deleteQna(QNADO qnado);
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
    ArrayList<String> getRawsForChecking();
    QRDO getOneQrdo(String[] st1,String[] st2);
    void askChange(QRDO qrdo);
    void getSchedule(String year,String month,String day);
    void addListenerForSQLite();
}
