package com.example.jaeho.productmanagement.Model.DAOS.Firebase;

import android.content.Context;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Controller.QNAActivitys.CustomQNAAdapter;
import com.example.jaeho.productmanagement.Model.DO.QNADO;
import com.example.jaeho.productmanagement.Model.DO.InformationQRDO;
import com.example.jaeho.productmanagement.Model.DO.QRDO;
import com.example.jaeho.productmanagement.Model.DO.UserDO;
import com.example.jaeho.productmanagement.utils.CurentUser;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 7. 20..
 */

public abstract class FireBaseDAO implements InformationDAO {
    AuthForFirebase auth;
    DatabaseFromFirebase ref;
    Context context;
    public FireBaseDAO(){auth = new AuthForFirebase();}

    public FireBaseDAO(Context context){ auth = new AuthForFirebase(context);this.context = context;}

    public FireBaseDAO(Context context, String type){
        auth = new AuthForFirebase(context);
        this.context = context;
    }
    public String getUserEmail(){
        return auth.getUserEmail();
    }
    public void addQna(QNADO qnado){
        ref = new DatabaseFromFirebase(context,"QNA");
        ref.addQna(qnado);
    }
    public void readQna(QNADO qnado){
        ref = new DatabaseFromFirebase(context,"QNA");
        ref.readQna(qnado,getUserEmail());
    }
    public void deleteQna(QNADO qnado){
        ref = new DatabaseFromFirebase(context,"QNA");
        ref.deleteQna(qnado,getUserEmail());
    }

    public void onStop(){auth.onStop();}

    public CustomQNAAdapter getAdapter(){
        ref = new DatabaseFromFirebase(context,"QNA");
        return ref.getAdapter();
    };
    public CustomQNAAdapter getMyAdapter(){
        ref = new DatabaseFromFirebase(context,"QNA");
        return ref.getAdapter(getUserEmail());
    }
    public String getUserName(){return auth.getUserName();}
    @Override
    public void makeAccount(final String email,final String pw) { auth.makeAccount(email,pw);}

    @Override
    public void checkSignIn(final String email, final String pw) {auth.checkSignIn(email,pw);}

    @Override
    public void accessUserInform(final String id,final String pw){auth.accessUserInform(id,pw);};

    @Override
    public void insert(InformationQRDO qr) {}

    @Override
    public void deleteInformation(int id) {}

    @Override
    public ArrayList<InformationQRDO> getInformation() {
        return null;
    }

    @Override
    public ArrayList<InformationQRDO> getInformationByQR(InformationQRDO qr) {
        return null;
    }

    @Override
    public void checkSignUp(final String id, final String pw) {
        return;
    }

    @Override
    public void updateInformation(InformationQRDO qr) {}

    public void addQR(QRDO qrdo){}

    public UserDO getCurrentUser(){return  CurentUser.getInstance();}
}
