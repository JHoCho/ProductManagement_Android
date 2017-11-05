package com.example.jaeho.productmanagement.Model.DAOS.Firebase;

import android.content.Context;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DO.QNADO;
import com.example.jaeho.productmanagement.Model.DO.QRDO;
import com.example.jaeho.productmanagement.Model.DO.UserDO;
import com.example.jaeho.productmanagement.utils.CurentUser;
import com.example.jaeho.productmanagement.utils.QNAActivitys.CustomQNAAdapter;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 7. 20..
 */

public abstract class FireBaseDAO implements InformationDAO {
    AuthForFirebase auth;
    DatabaseFromFirebase ref;
    Context context;

    public FireBaseDAO() {
        auth = new AuthForFirebase();
    }

    public FireBaseDAO(Context context) {
        auth = new AuthForFirebase(context);
        this.context = context;
    }

    public FireBaseDAO(Context context, String type) {
        auth = new AuthForFirebase(context);
        this.context = context;
    }

    public String getUserEmail() {
        return auth.getUserEmail();
    }

    public String getUserName() {
        return auth.getUserName();
    }

    @Override
    public void makeAccount(final String email, final String pw) {
        auth.makeAccount(email, pw);
    }

    @Override
    public void checkSignIn(final String email, final String pw) {
        auth.checkSignIn(email, pw);
    }

    public UserDO getCurrentUser() {
        return CurentUser.getInstance();
    }

    public ArrayList getTopLevelLocation() {
        return auth.getTopLevelLocation();
    }

    public ArrayList getMiddleLevelLocation(String building) {
        return auth.getMiddleLevelLocation(building);
    }

    public ArrayList getLowLevelLocation(String building, String floor) {
        return auth.getLowLevelLocation(building, floor);
    }

    public ArrayList<String> getTopLevelPname() {
        return auth.getTopLevelPname();
    }

    public void deleteListenerForSQLite() {
        auth.deleteListenerForSQLite();
    }

    public ArrayList<String> getMiddleLevelPname(String productName) {
        return auth.getMiddleLevelPname(productName);
    }

    public ArrayList<String> getLowLevelPname(String productName, String detailedProductName) {
        return auth.getLowLevelPname(productName, detailedProductName);
    }

    public int getNumOfRow() {
        return auth.getNumOfRow();
    }

    public ArrayList<String> getRawsForChecking() {
        return auth.getRawsForChecking();
    }

    public QRDO getOneQrdo(String[] st1, String[] st2) {
        return auth.getOneQrdo(st1, st2);
    }

    public void addListenerForSQLite() {
        auth.addListenerForSQLite();
    }


    public void addQna(QNADO qnado) {
        ref = new DatabaseFromFirebase(context, "QNA");
        ref.addQna(qnado);
    }

    public void readQna(QNADO qnado) {
        ref = new DatabaseFromFirebase(context, "QNA");
        ref.readQna(qnado, getUserEmail());
    }

    public void deleteQna(QNADO qnado) {
        ref = new DatabaseFromFirebase(context, "QNA");
        ref.deleteQna(qnado, getUserEmail());
    }

    public void onStop() {
        auth.onStop();
    }

    public CustomQNAAdapter getAdapter() {
        ref = new DatabaseFromFirebase(context, "QNA");
        return ref.getAdapter();
    }

    public CustomQNAAdapter getMyAdapter() {
        ref = new DatabaseFromFirebase(context, "QNA");
        return ref.getAdapter(getUserEmail());
    }

    public void askChange(ArrayList<QRDO> qrdo) {
        ref = new DatabaseFromFirebase(context, "Ask");
        ref.askChange(qrdo);
    }

    public void getSchedule(String year, String month, String day) {
        ref = new DatabaseFromFirebase(context, "Calendar");
        ref.getSchedule(year, month, day);
    }

}
