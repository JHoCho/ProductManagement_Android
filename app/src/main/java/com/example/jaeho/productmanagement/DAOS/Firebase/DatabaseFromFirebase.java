package com.example.jaeho.productmanagement.DAOS.Firebase;

import com.example.jaeho.productmanagement.QNAActivitys.QNADO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 8. 3..
 */

public class DatabaseFromFirebase {
    DatabaseReference mRootRef;
    DatabaseReference mRef;//이곳에 해당 참조의 변화를 감지하는 addValueEventListener 등을 만들어 변화가 있는지 감시할 수 있다
    ArrayList<QNADO> qnaList;
    //이때 데이터 스냅샷은 바뀐값을 가지고 있고 이를 띄우거나 가지고 놀 수 있다
    DatabaseFromFirebase(){
        mRootRef = FirebaseDatabase.getInstance().getReference();
    }
    DatabaseFromFirebase(String type) {
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mRef = mRootRef.child(type);
    }

    public DatabaseReference getRef() {
        return mRef;
    }
    public void setFirstChild(String type){
        mRef = mRootRef.child(type);
    }
    public void getLast10Items(){
        mRef.orderByChild("idx").limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void addQna(String subject, String contents,String email){
        QNADO a = new QNADO();
        a.setSubject(subject);
        a.setContents(contents);
        a.setEmail(email);
        mRootRef.child("QNA").push().setValue(a);
    }
}
