package com.example.jaeho.productmanagement.DAOS.Firebase;

import android.content.Context;

import com.example.jaeho.productmanagement.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.QNAActivitys.QNADO;
import com.example.jaeho.productmanagement.jheaders.InformationQR;
import com.google.android.gms.wearable.DataApi;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 7. 20..
 */

public abstract class FireBaseDAO implements InformationDAO {
    AuthForFirebase auth;
    DatabaseFromFirebase ref;
    public FireBaseDAO(){auth = new AuthForFirebase();}

    public FireBaseDAO(Context context){ auth = new AuthForFirebase(context);}

    public ArrayList<QNADO> getInformation(String type){
        ref = new DatabaseFromFirebase(type);

        return null;
    };
    public void addQna(String subject,String contents){
        ref = new DatabaseFromFirebase("QNA");
        ref.addQna(subject,contents,auth.user.getEmail());
    }

    public void onStop(){auth.onStop();}

    public ArrayList getLast10QNAs(){
        ref = new DatabaseFromFirebase("QNA");
        return ref.getLast10QNAs();}

    @Override
    public void makeAccount(final String email,final String pw) { auth.makeAccount(email,pw);}

    @Override
    public void checkSignIn(final String email, final String pw) {auth.checkSignIn(email,pw);}

    @Override
    public void accessUserInform(final String id,final String pw){auth.accessUserInform(id,pw);};

    @Override
    public void insert(InformationQR qr) {}

    @Override
    public void deleteInformation(int id) {}

    @Override
    public ArrayList<InformationQR> getInformation() {
        return null;
    }

    @Override
    public ArrayList<InformationQR> getInformationByQR(InformationQR qr) {
        return null;
    }


    @Override
    public void checkSignUp(final String id, final String pw) {
        return;
    }
    @Override
    public void updateInformation(InformationQR qr) {}


}
