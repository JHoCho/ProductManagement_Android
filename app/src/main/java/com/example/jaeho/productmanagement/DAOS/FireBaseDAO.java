package com.example.jaeho.productmanagement.DAOS;

import android.app.Activity;
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

import com.example.jaeho.productmanagement.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 7. 20..
 */

public abstract class FireBaseDAO implements InformationDAO {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mQrRef = mRootRef.child("MasterID");//이곳에 해당 참조의 변화를 감지하는 addValueEventListener 등을 만들어 변화가 있는지 감시할 수 있다
                                                    //이때 데이터 스냅샷은 바뀐값을 가지고 있고 이를 띄우거나 가지고 놀 수 있다
    //루트의 인스턴스를 참조하는 인스턴스 변수를 생성.
    // FirebaseAuth, AuthStateListenet 오브젝트를 선언.
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public Context context;
    FirebaseUser user;
    private static final String TAG = "FireBaseDAO";
    public FireBaseDAO(){}

    public FireBaseDAO(Context context){
        mAuth = FirebaseAuth.getInstance();
        this.context = context;
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //User가 이미 로그인 ( 사인인) 한 상태
                }else{
                    //User 로그아웃 (사인아웃)상태
                }
            }
        };
        onStart();
    }


    @Override
    public boolean makeAccount(final String email,final String pw) {
        Log.d(TAG,"SignIn"+email);
        if(!validateForm(email,pw)){
            return false;
        }
        mAuth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener((AppCompatActivity)context, new OnCompleteListener<AuthResult>() {
                    //쓰레드 동작하며 리스너를 만들어줍니다.
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //뭔가 값이 들어오면 성공인지 실패인지 isMade에 저장해줍니다.
                        if (task.isSuccessful()) {
                            Toast.makeText(context,"회원가입 성공",Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                            dlg.setMessage("회원가입을 완료하려면 이메일 인증을 해야합니다.");
                            dlg.setNegativeButton("취소", null);
                            dlg.setPositiveButton("보내기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sendEmailVerification();
                                }
                            });
                            dlg.show();
                        }else {
                            Toast.makeText(context,"회원가입 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return false;
    }
    private boolean isSignIn=false;
    private boolean isLoginComplete;
    @Override
    public void checkSignIn(final String email, final String pw) {
        Log.d(TAG,"SignIn"+email);
        if(!validateForm(email,pw)){
            return;
        }
        mAuth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener((AppCompatActivity)context, new OnCompleteListener<AuthResult>() {
                    //여기선 쓰레드가 동작하며 리스너를 만듭니다
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //성공시 로그 띄우는 양식Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                            //리스너부분에서 온컴플리트로 들어온순간 뭔가 성공이던 실패던 값을 받은 것이고
                        if (!task.isSuccessful()) {
                            //실패시 로그 띄우는 양Log.w(TAG, "signInWithEmail:failed", task.getException());
                            //실패시 isSignIn을 변경하지않음.
                            Toast.makeText(context,"Authentication failed",Toast.LENGTH_SHORT).show();
                        }
                        else{
                           //성공시 FirebaseUser user에 mAuth.getCurrentUser()을 이용해 유저정보를 가져옴
                            Toast.makeText(context,"Authentication success",Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                            dlg.setMessage(user.getEmail()+"로 로그인 하시겠습니까?");
                            dlg.setNegativeButton("아니오",null);
                            dlg.setPositiveButton("예",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(context, HomeActivity.class);
                                    context.startActivity(intent);
                                    ((AppCompatActivity) context).finish();
                                }
                            });
                            dlg.show();
                        }
                    }
                });
        return;
    }

    @Override
    public void accessUserInform(final String id,final String pw){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    };

    private void sendEmailVerification(){
        user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener((AppCompatActivity)context, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button

                        if (task.isSuccessful()) {
                            Toast.makeText(context,"Verification email sent to " + user.getEmail(),Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText((AppCompatActivity)context,"Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    private boolean validateForm(final String email_,final String pw) {
        boolean valid = true;

        String email = email_;
        if (TextUtils.isEmpty(email)) {
            //edit에 setError false
            valid = false;
        } else {
            //edit 에 setError null
        }

        String password = pw;
        if (TextUtils.isEmpty(password)) {
            //edit에 setError false
            valid = false;
        } else {
            //edit에 setError null
        }

        return valid;
    }
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

    public void onStart(){
        mAuth.addAuthStateListener(mAuthListener);
    }
    public void onStop(){
        if(mAuthListener !=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
