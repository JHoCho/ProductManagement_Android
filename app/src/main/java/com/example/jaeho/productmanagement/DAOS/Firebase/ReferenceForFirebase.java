package com.example.jaeho.productmanagement.DAOS.Firebase;

import com.example.jaeho.productmanagement.QNAActivitys.QNABoard;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 8. 2..
 */

public class ReferenceForFirebase {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mChildRef;
    ArrayList<QNABoard> a;
    //이때 데이터 스냅샷은 바뀐값을 가지고 있고 이를 띄우거나 가지고 놀 수 있다
    public ReferenceForFirebase(){


    }
    public ReferenceForFirebase(String type){
        mChildRef = mRootRef.child(type);//이곳에 해당 참조의 변화를 감지하는 addValueEventListener 등을 만들어 변화가 있는지 감시할 수 있다
    }

    public ArrayList<QNABoard> selectAll(){
        mChildRef.orderByChild("idx").limitToLast(10).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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
        return a;
    }
}
