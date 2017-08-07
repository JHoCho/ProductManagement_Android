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

import com.example.jaeho.productmanagement.Activities.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;


public class AuthForFirebase {
        private com.google.firebase.auth.FirebaseAuth mAuth;
        private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthListener;
        public Context context;
        FirebaseUser user;
        private static final String TAG = "FireBaseDAO";
        ProgressDialog prdlg;

        public AuthForFirebase() {
        }

        public AuthForFirebase(Context context) {

            mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
            this.context = context;
            mAuthListener = new com.google.firebase.auth.FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull com.google.firebase.auth.FirebaseAuth firebaseAuth) {
                    user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        //User가 이미 로그인 ( 사인인) 한 상태
                    } else {
                        //User 로그아웃 (사인아웃)상태
                    }
                }
            };
            onStart();
        }

        public void onStart() {
            mAuth.addAuthStateListener(mAuthListener);
        }

        public void onStop() {
            if (mAuthListener != null) {
                mAuth.removeAuthStateListener(mAuthListener);
            }
        }

        public void makeAccount(final String email, final String pw) {
            Log.d(TAG, "SignIn" + email);
            if (!validateForm(email, pw)) {
                return;
            }
            showProgressDialog();
            mAuth.createUserWithEmailAndPassword(email, pw)
                    .addOnCompleteListener((AppCompatActivity) context, new OnCompleteListener<AuthResult>() {
                        //쓰레드 동작하며 리스너를 만들어줍니다.
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //뭔가 값이 들어오면 성공인지 실패인지 isMade에 저장해줍니다.
                            if (task.isSuccessful()) {
                                hidProgressDialog();
                                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                                dlg.setMessage("회원가입을 완료하려면 이메일 인증을 해야합니다.");
                                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        signOut();//취소일때 삭제 요청 보내야함. 이게 삭제요청이라는 확신은 없음.
                                    }
                                });
                                dlg.setPositiveButton("보내기", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sendEmailVerification();
                                    }
                                });
                                dlg.show();

                            } else {
                                Toast.makeText(context, "회원가입 실패", Toast.LENGTH_SHORT).show();
                            }
                            hidProgressDialog();
                        }
                    });
            return;
        }

        public void checkSignIn(final String email, final String pw) {
            Log.d(TAG, "SignIn" + email);
            if (!validateForm(email, pw)) {
                return;
            }
            showProgressDialog();
            mAuth.signInWithEmailAndPassword(email, pw)
                    .addOnCompleteListener((AppCompatActivity) context, new OnCompleteListener<AuthResult>() {
                        //여기선 쓰레드가 동작하며 리스너를 만듭니다
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //성공시 로그 띄우는 양식Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                            //리스너부분에서 온컴플리트로 들어온순간 뭔가 성공이던 실패던 값을 받은 것이고
                            if (!task.isSuccessful()) {
                                //실패시 로그 띄우는 양Log.w(TAG, "signInWithEmail:failed", task.getException());
                                //실패시 isSignIn을 변경하지않음.
                                hidProgressDialog();
                                Toast.makeText(context, "인증에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                hidProgressDialog();
                                Intent intent = new Intent(context, HomeActivity.class);
                                context.startActivity(intent);
                                ((AppCompatActivity) context).finish();
                                //성공시 FirebaseUser user에 mAuth.getCurrentUser()을 이용해 유저정보를 가져옴
                                Toast.makeText(context, "인증에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            return;
        }

        public void accessUserInform(final String id, final String pw) {
            user = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser();
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
        }

        ;

        private void sendEmailVerification() {
            user = mAuth.getCurrentUser();
            user.sendEmailVerification()
                    .addOnCompleteListener((AppCompatActivity) context, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // [START_EXCLUDE]
                            // Re-enable button

                            if (task.isSuccessful()) {
                                Toast.makeText(context, "E-mail이 " + user.getEmail()+"로 전송 되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "sendEmailVerification", task.getException());
                                toastoast("이메일 인증에 실패하였습니다.");
                            }
                            // [END_EXCLUDE]
                        }
                    });
            // [END send_email_verification]
        }

        private void toastoast(String a) {
            Toast.makeText(context, a, Toast.LENGTH_SHORT).show();
        }

        private boolean validateForm(final String email_, final String pw) {
            boolean valid = true;
            if (TextUtils.isEmpty(email_))
            {
                //edit에 setError false
                toastoast("아이디 칸이 비어있습니다.");
                valid = false;
            }
            else if (Pattern.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", email_) != true)
            {
                toastoast("아이디가 이메일 형식과 맞지 않습니다.");
                valid = false;
            }
            else if (TextUtils.isEmpty(pw))
            {
                //edit에 setError false
                toastoast("비밀 번호 란이 비어있으면 안됩니다.");
                valid = false;
            }

            return valid;
        }

        public void showProgressDialog() {
            prdlg = ProgressDialog.show(context, "잠시만 기다려주세요", "서버와 통신중 입니다.", true);
        }

        public void hidProgressDialog() {
            prdlg.dismiss();
        }
        private void signOut() {
            mAuth.signOut();
        }

    }
