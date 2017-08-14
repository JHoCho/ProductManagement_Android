package com.example.jaeho.productmanagement.DAOS.Firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.jaeho.productmanagement.Activities.QNAReadActivity;
import com.example.jaeho.productmanagement.QNAActivitys.CustomQNAAdapter;
import com.example.jaeho.productmanagement.QNAActivitys.QNADO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jaeho on 2017. 8. 3..
 */

public class DatabaseFromFirebase {
    DatabaseReference mRootRef;
    DatabaseReference mRef;//이곳에 해당 참조의 변화를 감지하는 addValueEventListener 등을 만들어 변화가 있는지 감시할 수 있다
    ArrayList<QNADO> qnaList;
    Context context;
    CustomQNAAdapter mAdapter;
    ProgressDialog prdlg;
    //이때 데이터 스냅샷은 바뀐값을 가지고 있고 이를 띄우거나 가지고 놀 수 있다
    DatabaseFromFirebase(Context context,String type){
        this.context = context;
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mRef = mRootRef.child(type);
        qnaList = new ArrayList<QNADO>();
    }
    DatabaseFromFirebase(Context context){
        this.context = context;
        mRootRef = FirebaseDatabase.getInstance().getReference();

        qnaList = new ArrayList<QNADO>();

    }
    DatabaseFromFirebase() {
        mRootRef = FirebaseDatabase.getInstance().getReference();
        qnaList = new ArrayList<QNADO>();
    }

    public DatabaseReference getRef() {
        return mRef;
    }
    public void setFirstChild(String type){
        mRef = mRootRef.child(type);
    }

    public void listen10QNAs(){
        mRootRef.child("QNA").orderByValue().limitToLast(10).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //이부분 매핑을 제대로 해줘야 할듯.
                HashMap<String,String> json = (HashMap)dataSnapshot.getValue();
                QNADO qnado = new QNADO();
                qnado.setEmail(json.get("email"));
                qnado.setContents(json.get("contents"));
                qnado.setSubject(json.get("subject"));
                qnado.setName(json.get("name"));
                qnado.setDate(json.get("date"));
                qnado.setKey(dataSnapshot.getKey());
                qnaList.add(qnado);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public ArrayList getLast10QNAs(){
        listen10QNAs();
        return this.qnaList;
    }

    public CustomQNAAdapter getAdapter(){
        qnaList= this.getLast10QNAs( );
        mAdapter = new CustomQNAAdapter(context,qnaList);
        return mAdapter;
    }


    public void addQna(QNADO qnado){
        showProgressDialog();
        mRootRef.child("QNA").push().setValue(qnado).addOnCompleteListener((AppCompatActivity) context, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    hidProgressDialog();
                    tostost("QNA를 등록하였습니다.");
                    ((AppCompatActivity) context).finish();
                } else {
                    tostost("QNA등록에 실패하였습니다.");
                    ((AppCompatActivity) context).finish();
                }
                hidProgressDialog();
            }
        });
    }
    public void readQna(QNADO qnado){
        //qnado(로컬정보)에서 받은 qnado인스턴스의 정보를 이용하여 파이어베이스 서버에서 내용을 읽어 QNAReadActivity로 이동시켜줌
        showProgressDialog();
        mRootRef.child("QNA").child(qnado.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,String> json = (HashMap)dataSnapshot.getValue();
                Intent intent = new Intent(context, QNAReadActivity.class);
                intent.putExtra("subject",json.get("subject").toString());
                intent.putExtra("contents",json.get("contents").toString());
                context.startActivity(intent);
                hidProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hidProgressDialog();
            }
        });
    }
    public void showProgressDialog() {
        prdlg = ProgressDialog.show(this.context, "잠시만 기다려주세요", "서버와 통신중 입니다.", true);
    }
    public void tostost(String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
    public void hidProgressDialog() {
        prdlg.dismiss();
    }
}
