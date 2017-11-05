package com.example.jaeho.productmanagement.Model.DAOS.Firebase;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.jaeho.productmanagement.Controller.Activities.QNAReadActivity;
import com.example.jaeho.productmanagement.Model.DO.QNADO;
import com.example.jaeho.productmanagement.Model.DO.QRDO;
import com.example.jaeho.productmanagement.Model.DO.UserDO;
import com.example.jaeho.productmanagement.utils.CurentUser;
import com.example.jaeho.productmanagement.utils.QNAActivitys.CustomQNAAdapter;
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

import static com.example.jaeho.productmanagement.Controller.Activities.CalendarActivity.calendarTv;
import static com.example.jaeho.productmanagement.utils.Constants.hidProgressDialog;
import static com.example.jaeho.productmanagement.utils.Constants.prdlg;
import static com.example.jaeho.productmanagement.utils.Constants.showProgressDialog;
import static com.example.jaeho.productmanagement.utils.Constants.tostost;

/**
 * Created by jaeho on 2017. 8. 3..
 */

public class DatabaseFromFirebase {
    DatabaseReference mRootRef;
    DatabaseReference mRef;//이곳에 해당 참조의 변화를 감지하는 addValueEventListener 등을 만들어 변화가 있는지 감시할 수 있다
    ArrayList<QNADO> qnaList;
    Context context;
    CustomQNAAdapter mAdapter;


    //이때 데이터 스냅샷은 바뀐값을 가지고 있고 이를 띄우거나 가지고 놀 수 있다
    DatabaseFromFirebase(Context context, String type) {
        this.context = context;
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mRef = mRootRef.child(type);
        qnaList = new ArrayList<QNADO>();
    }

    DatabaseFromFirebase(Context context) {
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

    public void setFirstChild(String type) {
        mRef = mRootRef.child(type);
    }

    public void addPeopleInCompany(String company, String id) {
        mRootRef.child("Company").child(company).child(id).setValue(false);
    }

    public void listen10QNAs() {
        mRootRef.child("QNA").orderByValue().limitToLast(10).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //이부분 매핑을 제대로 해줘야 할듯.
                try {
                    HashMap<String, String> json = (HashMap) dataSnapshot.getValue();
                    QNADO qnado = new QNADO();
                    qnado.setEmail(json.get("email"));
                    qnado.setContents(json.get("contents"));
                    qnado.setSubject(json.get("subject"));
                    qnado.setName(json.get("name"));
                    qnado.setDate(json.get("date"));
                    qnado.setAnswer(json.get("Answer"));
                    qnado.setKey(dataSnapshot.getKey());
                    mAdapter.add(qnado);
                } catch (Exception e) {
                    HashMap<String, String> json = (HashMap) dataSnapshot.getValue();
                    QNADO qnado = new QNADO();
                    qnado.setEmail(json.get("email"));
                    qnado.setContents(json.get("contents"));
                    qnado.setSubject(json.get("subject"));
                    qnado.setName(json.get("name"));
                    qnado.setDate(json.get("date"));
                    qnado.setAnswer("아직 답이 달리지 않았습니다.");
                    qnado.setKey(dataSnapshot.getKey());
                    mAdapter.add(qnado);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                try {
                    HashMap<String, String> json = (HashMap) dataSnapshot.getValue();
                    QNADO qnado = new QNADO();
                    qnado.setEmail(json.get("email"));
                    qnado.setContents(json.get("contents"));
                    qnado.setSubject(json.get("subject"));
                    qnado.setName(json.get("name"));
                    qnado.setDate(json.get("date"));
                    qnado.setAnswer(json.get("Answer"));
                    qnado.setKey(dataSnapshot.getKey());
                    mAdapter.remove(qnado);
                } catch (Exception e) {
                    HashMap<String, String> json = (HashMap) dataSnapshot.getValue();
                    QNADO qnado = new QNADO();
                    qnado.setEmail(json.get("email"));
                    qnado.setContents(json.get("contents"));
                    qnado.setSubject(json.get("subject"));
                    qnado.setName(json.get("name"));
                    qnado.setDate(json.get("date"));
                    qnado.setAnswer("아직 답이 달리지 않았습니다.");
                    qnado.setKey(dataSnapshot.getKey());
                    mAdapter.remove(qnado);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void listenMy10QNAs(String myEmail) {
        mRootRef.child("QNA").orderByChild("email").equalTo(myEmail).limitToLast(10).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //이부분 매핑을 제대로 해줘야 할듯.
                try {
                    HashMap<String, String> json = (HashMap) dataSnapshot.getValue();
                    QNADO qnado = new QNADO();
                    qnado.setEmail(json.get("email"));
                    qnado.setContents(json.get("contents"));
                    qnado.setSubject(json.get("subject"));
                    qnado.setName(json.get("name"));
                    qnado.setDate(json.get("date"));
                    qnado.setAnswer(json.get("answer"));
                    qnado.setKey(dataSnapshot.getKey());
                    mAdapter.add(qnado);
                } catch (Exception e) {
                    HashMap<String, String> json = (HashMap) dataSnapshot.getValue();
                    QNADO qnado = new QNADO();
                    qnado.setEmail(json.get("email"));
                    qnado.setContents(json.get("contents"));
                    qnado.setSubject(json.get("subject"));
                    qnado.setName(json.get("name"));
                    qnado.setDate(json.get("date"));
                    qnado.setAnswer("아직 답이 달리지 않았습니다.");
                    qnado.setKey(dataSnapshot.getKey());
                    mAdapter.add(qnado);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                HashMap<String, String> json = (HashMap) dataSnapshot.getValue();
                QNADO qnado = new QNADO();
                qnado.setEmail(json.get("email"));
                qnado.setContents(json.get("contents"));
                qnado.setSubject(json.get("subject"));
                qnado.setName(json.get("name"));
                qnado.setDate(json.get("date"));
                qnado.setKey(dataSnapshot.getKey());
                mAdapter.remove(qnado);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public ArrayList getLast10QNAs() {
        listen10QNAs();
        return this.qnaList;
    }

    public ArrayList getMyLast10QNAs(String myEmail) {
        listenMy10QNAs(myEmail);
        return this.qnaList;
    }

    public CustomQNAAdapter getAdapter() {
        qnaList = this.getLast10QNAs();
        mAdapter = new CustomQNAAdapter(context, qnaList);
        return mAdapter;
    }

    public CustomQNAAdapter getAdapter(String myEmail) {
        qnaList = this.getMyLast10QNAs(myEmail);
        mAdapter = new CustomQNAAdapter(context, qnaList);
        return mAdapter;
    }


    public void addQna(QNADO qnado) {
        showProgressDialog(context);
        mRootRef.child("QNA").push().setValue(qnado).addOnCompleteListener((AppCompatActivity) context, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    tostost("QNA를 등록하였습니다.", context);
                    ((AppCompatActivity) context).finish();
                } else {
                    tostost("QNA등록에 실패하였습니다.", context);
                    ((AppCompatActivity) context).finish();
                }
                hidProgressDialog();
            }
        });
    }

    public void deleteQna(final QNADO qnado, String email) {
        showProgressDialog(context);
        if (qnado.getEmail().equals(email)) {
            mRootRef.child("QNA").child(qnado.getKey()).removeValue().addOnCompleteListener((AppCompatActivity) context, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        hidProgressDialog();
                        tostost("QNA를 삭제하였습니다.", context);

                        ((AppCompatActivity) context).finish();
                    } else {
                        tostost("QNA삭제에 실패하였습니다.", context);
                        ((AppCompatActivity) context).finish();
                    }
                    hidProgressDialog();
                }
            });
        } else {
            tostost("권한없음", context);
            hidProgressDialog();
        }
    }

    public boolean isMine(String email, String jemail) {
        if (email.equals(jemail)) {
            return true;
        } else return false;
    }

    public void readQna(QNADO qnado, final String email) {
        //qnado(로컬정보)에서 받은 qnado인스턴스의 정보를 이용하여 파이어베이스 서버에서 내용을 읽어 QNAReadActivity로 이동시켜줌
        showProgressDialog(context);
        mRootRef.child("QNA").child(qnado.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    HashMap<String, String> json = (HashMap) dataSnapshot.getValue();
                    Intent intent = new Intent(context, QNAReadActivity.class);
                    intent.putExtra("contents", json.get("contents").toString());
                    intent.putExtra("subject", json.get("subject").toString());
                    intent.putExtra("email", json.get("email").toString());
                    intent.putExtra("name", json.get("name").toString());
                    intent.putExtra("date", json.get("date").toString());
                    intent.putExtra("key", dataSnapshot.getKey());
                    intent.putExtra("answer", json.get("answer").toString());
                    intent.putExtra("isMine", isMine(email, json.get("email").toString()));
                    context.startActivity(intent);
                } catch (Exception e) {
                    HashMap<String, String> json = (HashMap) dataSnapshot.getValue();
                    Intent intent = new Intent(context, QNAReadActivity.class);
                    intent.putExtra("contents", json.get("contents").toString());
                    intent.putExtra("subject", json.get("subject").toString());
                    intent.putExtra("email", json.get("email").toString());
                    intent.putExtra("name", json.get("name").toString());
                    intent.putExtra("date", json.get("date").toString());
                    intent.putExtra("key", dataSnapshot.getKey());
                    intent.putExtra("answer", "아직 답이 없음.");
                    intent.putExtra("isMine", isMine(email, json.get("email").toString()));
                    context.startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        hidProgressDialog();
    }

    public void addUser(UserDO userDO) {
        showProgressDialog(context);
        mRootRef.child("User").child(userDO.getAdminID()).setValue(userDO).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hidProgressDialog();
            }
        });
    }

    public void askChange(final ArrayList<QRDO> qrdos) {
        for (final QRDO qrdo : qrdos) {
            showProgressDialog(context);
            mRootRef.child("deletedQR").child(CurentUser.getInstance().getCompanyName()).orderByChild("serialNumber").equalTo(qrdo.getSerialNumber().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println("린생" + dataSnapshot.toString());
                    if (dataSnapshot.getValue() == null) {
                        mRootRef.child("Ask").child(CurentUser.getInstance().getCompanyName()).child(qrdo.getSerialNumber()).child("location").setValue(qrdo.getLocation());
                        mRootRef.child("Ask").child(CurentUser.getInstance().getCompanyName()).child(qrdo.getSerialNumber()).child("outDate").setValue(qrdo.getOutDate()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                hidProgressDialog();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        tostost("요청에 성공했습니다", context);
    }

    public void getSchedule(String year, String month, String day) {
        if (month.length() < 2) {
            month = "0" + month;
        }
        if (day.length() < 2) {
            day = "0" + day;
        }
        String content = year + "-" + month + "-" + day;
        mRootRef.child("Calendar").child(CurentUser.getInstance().getCompanyName()).child(content).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    calendarTv.setText(dataSnapshot.getValue().toString());
                } else {
                    calendarTv.setText("스케쥴이 없습니다");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
