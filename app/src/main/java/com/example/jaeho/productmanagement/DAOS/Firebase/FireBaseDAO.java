package com.example.jaeho.productmanagement.DAOS.Firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.jaeho.productmanagement.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.HomeActivity;
import com.example.jaeho.productmanagement.jheaders.InformationQR;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by jaeho on 2017. 7. 20..
 */

public abstract class FireBaseDAO implements InformationDAO {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mQrRef = mRootRef.child("MasterID");//이곳에 해당 참조의 변화를 감지하는 addValueEventListener 등을 만들어 변화가 있는지 감시할 수 있다
                                                    //이때 데이터 스냅샷은 바뀐값을 가지고 있고 이를 띄우거나 가지고 놀 수 있다

    AuthForFirebase auth;

    public FireBaseDAO(){auth = new AuthForFirebase(); }

    public FireBaseDAO(Context context){ auth = new AuthForFirebase(context);}

    public void onStop(){auth.onStop();}

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
