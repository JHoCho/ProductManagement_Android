package com.example.jaeho.productmanagement.Model.DAOS.Firebase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaeho.productmanagement.Model.DAOS.Mysql.SQLiteDB;
import com.example.jaeho.productmanagement.Model.DO.QRDO;
import com.example.jaeho.productmanagement.Model.DO.UserDO;
import com.example.jaeho.productmanagement.R;
import com.example.jaeho.productmanagement.View.Activities.HomeActivity;
import com.example.jaeho.productmanagement.utils.CurentUser;
import com.google.android.gms.fitness.data.Goal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import static android.R.layout.simple_spinner_item;
import static com.example.jaeho.productmanagement.utils.Constants.hidProgressDialog;
import static com.example.jaeho.productmanagement.utils.Constants.showProgressDialog;


public class AuthForFirebase {
    private com.google.firebase.auth.FirebaseAuth mAuth;
    private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthListener;
    public Context context;
    FirebaseUser user;
    private DatabaseFromFirebase database;
    private static final String TAG = "AUTHFireBaseDAO";
    private ArrayList<String> companies;
    private ArrayAdapter<String> adapter;
    private String strCompany;
    SQLiteDB sqLiteDB;

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

    public void initUser() {
        database = new DatabaseFromFirebase(context);


    }

    public void onStart() {
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void onStop() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public String getUserName() {
        return user.getDisplayName();
    }

    public void makeAccount(final String email, final String pw) {
        Log.d(TAG, "SignIn" + email);
        if (!validateForm(email, pw)) {
            return;
        }
        showProgressDialog(context);
        mAuth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener((AppCompatActivity) context, new OnCompleteListener<AuthResult>() {
                    //쓰레드 동작하며 리스너를 만들어줍니다.
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //뭔가 값이 들어오면 성공인지 실패인지 isMade에 저장해줍니다.
                        if (task.isSuccessful()) {
                            hidProgressDialog();
                            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                            dlg.setMessage("회원가입을 완료하려면 이메일 인증을 완료해야합니다.");
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
        showProgressDialog(context);
        mAuth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener((AppCompatActivity) context, new OnCompleteListener<AuthResult>() {
                    //여기선 백그라운드에서 쓰레드가 동작하며 리스너를 만듭니다
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //성공시 로그 띄우는 양식Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        //리스너부분에서 온컴플리트로 들어온순간 뭔가 성공이던 실패던 값을 받은 것이고
                        if (!task.isSuccessful() || !user.isEmailVerified() || !isNameSet()) {//겟이메일한것을 뜯어 아이디로 써서 접속하여 내부의 컨펌이 있는지 확인하고 컨펌되었으면 접속가능
                            //실패시 로그 띄우는 양Log.w(TAG, "signInWithEmail:failed", task.getException());
                            //실패시 isSignIn을 변경하지않음.
                            if (!user.isEmailVerified()) {
                                toastoast("이메일 인증이 완료되지 않았습니다. 확인해주세요");
                            } else if (!isNameSet()) {
                                setProfileName(pw);
                            }
                            hidProgressDialog();
                            Toast.makeText(context, "인증에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        } else {

                            varifypermit();
                        }//패스됐을경우.
                    }
                });

        return;
    }

    private void varifypermit() {
        database = new DatabaseFromFirebase(context);
        database.mRootRef.child("User").child(emailToId(user.getEmail())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final HashMap<String, Object> json = (HashMap<String, Object>) dataSnapshot.getValue();
                database.mRootRef.child("Company").child(json.get("companyName").toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, Object> json2 = (HashMap<String, Object>) dataSnapshot.getValue();
                        if (json2.get(emailToId(user.getEmail())).toString().equals("false")) {
                            falsetoPermit();
                        } else if (json2.get(emailToId(user.getEmail())).toString().equals("true")) {
                            successPermit();
                        } else {
                            toastoast("이상한값이 들어가있습니다.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void falsetoPermit() {
        hidProgressDialog();
        AlertDialog.Builder dlg = new AlertDialog.Builder(context)
                .setTitle("담담자에게 문의바랍니다.")
                .setMessage("회원가입은 완료된 상태이나. 회사담당자의 허가가 떨어지지 않은 상태 입니다. 담당자에게 문의하십시오")
                .setPositiveButton("확인", null)
                .setNegativeButton("취소", null);
        dlg.show();
    }

    private void successPermit() {
        hidProgressDialog();
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
        ((AppCompatActivity) context).finish();
        //성공시 FirebaseUser user에 mAuth.getCurrentUser()을 이용해 유저정보를 가져옴
        Toast.makeText(context, "인증에 성공하였습니다.", Toast.LENGTH_SHORT).show();
        initCurrentUSer();
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

    private boolean isNameSet() {
        if (user.getDisplayName() == null) {
            // 이름이 없을경우 false 리턴
            return false;
        } else {
            // 이름이 있을경우 true리턴
            return true;
        }
    }

    private void setProfileName(final String pw) {
        hidProgressDialog();
        final AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setMessage("프로필을 설정해주세요.");
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dlg_set_user_profile, null);
        final EditText nameEdt, positionEdt, idNoEdt;
        final Spinner companySpinner;
        nameEdt = (EditText) view.findViewById(R.id.dlg_name_edt);
        companySpinner = (Spinner) view.findViewById(R.id.dlg_company_name_Spinner);
        positionEdt = (EditText) view.findViewById(R.id.dlg_position_edt);
        idNoEdt = (EditText) view.findViewById(R.id.dlg_idNo_edt);
        companies = new ArrayList<String>();//초기화 먼저.
        adapter = new ArrayAdapter<String>(view.getContext(), simple_spinner_item, companies);//초기화 먼저.
        getCompanies();
        companySpinner.setAdapter(adapter);
        companySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strCompany = companySpinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        companySpinner.setSelection(0);
        //final EditText edtText = new EditText(context);
        dlg.setView(view);
        dlg.setNegativeButton("취소", null);
        dlg.setPositiveButton("보내기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder dlg2 = new AlertDialog.Builder(context);
                final String strName, strPosition, strIdNo;
                strName = nameEdt.getText().toString();
                strPosition = positionEdt.getText().toString();
                strIdNo = idNoEdt.getText().toString();

                if (!validateFormName(strName) || !validateFormName(strPosition) || !validateFormNumber(strIdNo)) {
                    //하나라도 형식에 맞지않으면 리턴. vali에서 형식에맞으면 true반환하기에 정상일경우 받은값을 거꾸로 false로 해서 이부분을 무시
                    return;
                } else {
                    View view2;
                    LayoutInflater inflater2 = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    view2 = inflater2.inflate(R.layout.dlg_verify_user_profile, null);
                    final TextView nameTv, companyTv, positionTv, idNoTv;
                    nameTv = (TextView) view2.findViewById(R.id.dlg_name_tv);
                    companyTv = (TextView) view2.findViewById(R.id.dlg_company_name_tv);
                    positionTv = (TextView) view2.findViewById(R.id.dlg_position_tv);
                    idNoTv = (TextView) view2.findViewById(R.id.dlg_idNo_tv);
                    nameTv.setText(strName);
                    companyTv.setText(strCompany);
                    positionTv.setText(strPosition);
                    idNoTv.setText(strIdNo);
                    dlg2.setView(view2);
                }
                //여기에 추가적으로 연락처, 회사명,부서,직급 등을 적게함.
                dlg2.setTitle("다음 프로필로 설정하시겠습니까?");

                dlg2.setPositiveButton("설정", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        database = new DatabaseFromFirebase(context);
                        UserDO userDO = new UserDO();
                        userDO.setName(strName);
                        String temp = emailToId(user.getEmail());
                        userDO.setAdminID(temp);
                        userDO.setCompanyName(strCompany);
                        userDO.setPosition(strPosition);
                        userDO.setIdNo(strIdNo);
                        userDO.setEmail(user.getEmail());
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(userDO.getName()).build();
                        user.updateProfile(profileUpdates);
                        database.addUser(userDO);
                        database.addPeopleInCompany(strCompany, temp);
                        checkSignIn(userDO.getEmail(), pw);
                    }
                });

                dlg2.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        toastoast("취소하셨습니다.");
                    }
                });
                dlg2.show();
            }
        });
        dlg.show();
    }

    private String emailToId(String email) {
        StringTokenizer stringTokenizer = new StringTokenizer(email, "@.");
        String temp = "";
        while (stringTokenizer.hasMoreTokens()) {
            temp += stringTokenizer.nextToken();
        }
        return temp;
    }

    private void getCompanies() {
        database = new DatabaseFromFirebase(context);
        database.mRootRef.child("Company").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                companies.add(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
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

    private void initCurrentUSer() {
        database = new DatabaseFromFirebase(context);
        database.mRootRef.child("User").child(emailToId(user.getEmail())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> json = (HashMap) dataSnapshot.getValue();
                UserDO userDO = new UserDO();
                userDO.setName(json.get("name").toString());
                userDO.setAdminID(json.get("email").toString());
                userDO.setCompanyName(json.get("companyName").toString());
                userDO.setPosition(json.get("position").toString());
                userDO.setIdNo(json.get("idNo").toString());
                userDO.setEmail(json.get("email").toString());
                CurentUser.getInstance(userDO);
                initQRdata(database);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initQRdata(final DatabaseFromFirebase database) {
        sqLiteDB = new SQLiteDB(context);
        showProgressDialog(context);
        database.mRootRef.child("QR").child(CurentUser.getInstance().getCompanyName().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //할일
                //여기서 데이터가 들어왔을 때 들어온 값의 회사 부분이 다르다면 초기화하고 init
                //같다면 정리하지 않음 이하 바뀌는 부분이 있을경우
                //서비스에서 받아서 브로드캐스트 리시버로 데이터를 작성해줘야 한다.

                try{

                    if (!sqLiteDB.getDataCompanyName().equals(CurentUser.getInstance().getCompanyName())) {
                        //회사명이다르면.
                        //다른사람이 접근 한 것이므로 테이블을 지우고 새로 이닛을 시작한다.
                        //테이블을 지우는 부분을 정의해야함.
                        toastoast("초기화를 시작합니다.");
                        sqLiteDB.clearData();
                        ArrayList<QRDO> arr = new ArrayList<QRDO>();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            HashMap<String, Object> json = (HashMap<String, Object>) child.getValue();
                            QRDO qrdo = new QRDO();
                            qrdo.setAdminID(json.get("adminID").toString());
                            qrdo.setBuilding(json.get("building").toString());
                            qrdo.setCompanyName(json.get("companyName").toString());
                            qrdo.setDate(json.get("date").toString());
                            qrdo.setDetailedProductName(json.get("detailedProductName").toString());
                            qrdo.setFloor(json.get("floor").toString());
                            qrdo.setLocation(json.get("location").toString());
                            qrdo.setPrice(json.get("price").toString());
                            qrdo.setProductName(json.get("productName").toString());
                            qrdo.setRoomName(json.get("roomName").toString());
                            qrdo.setSerialNumber(json.get("serialNumber").toString());
                            arr.add(qrdo);
                        }
                        sqLiteDB.initData(arr);
                        hidProgressDialog();
                    }else{
                        //회사명이 같으면
                        System.out.println("DB의 회사이름"+sqLiteDB.getDataCompanyName());
                        hidProgressDialog();
                    }
                }catch (NullPointerException e){
                    //DB내의 회사명이 null이면 첫접속이므로 초기화를 시작해줌.

                    e.printStackTrace();
                    HashMap<String, Object> test = (HashMap<String, Object>) dataSnapshot.getValue();
                    System.out.println("dataSnapshot" + test);
                    // System.out.println("test.get(\"companyName\").toString()"+test.get("companyName").toString());
                    System.out.println(CurentUser.getInstance().getCompanyName());

                    //회사명이 다르다는 것이므로 테이블을 지우고 새로 이닛을 시작한다.
                    //테이블을 지우는 부분을 정의해야함.
                    toastoast("초기화를 시작합니다.");
                    sqLiteDB.clearData();
                    ArrayList<QRDO> arr = new ArrayList<QRDO>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        HashMap<String, Object> json = (HashMap<String, Object>) child.getValue();
                        QRDO qrdo = new QRDO();
                        qrdo.setAdminID(json.get("adminID").toString());
                        qrdo.setBuilding(json.get("building").toString());
                        qrdo.setCompanyName(json.get("companyName").toString());
                        qrdo.setDate(json.get("date").toString());
                        qrdo.setDetailedProductName(json.get("detailedProductName").toString());
                        qrdo.setFloor(json.get("floor").toString());
                        qrdo.setLocation(json.get("location").toString());
                        qrdo.setPrice(json.get("price").toString());
                        qrdo.setProductName(json.get("productName").toString());
                        qrdo.setRoomName(json.get("roomName").toString());
                        qrdo.setSerialNumber(json.get("serialNumber").toString());
                        arr.add(qrdo);
                    }
                    sqLiteDB.initData(arr);
                    hidProgressDialog();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void sendEmailVerification() {
        user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener((AppCompatActivity) context, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button

                        if (task.isSuccessful()) {
                            Toast.makeText(context, "E-mail이 " + user.getEmail() + "로 전송 되었습니다.", Toast.LENGTH_SHORT).show();
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
        if (TextUtils.isEmpty(email_)) {
            //edit에 setError false
            toastoast("아이디 칸이 비어있습니다.");
            valid = false;
        } else if (Pattern.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", email_) != true) {
            toastoast("아이디가 이메일 형식과 맞지 않습니다.");
            valid = false;
        } else if (TextUtils.isEmpty(pw)) {
            //edit에 setError false
            toastoast("비밀 번호 란이 비어있으면 안됩니다.");
            valid = false;
        }

        return valid;
    }

    private boolean validateFormName(final String name) {
        boolean valid = true;
        if (TextUtils.isEmpty(name)) {
            //edit에 setError false
            toastoast("빈값은 허용되지 않습니다.");
            valid = false;
        } else if (Pattern.matches("^[a-zA-Z0-9가-힣_]{0,30}$", name) != true) {
            toastoast("30자 이내로 써주세요(한/영)");
            valid = false;
        }

        return valid;
    }

    private boolean validateFormNumber(final String no) {
        boolean valid = true;
        if (TextUtils.isEmpty(no)) {
            toastoast("빈값은 허용되지 않습니다.");
            valid = false;
        } else if (Pattern.matches("^[a-zA-Z0-9가-힣]{0,30}$", no) != true) {
            toastoast("30자 이내로 써주세요(한/영)");
            valid = false;
        }
        return valid;
    }

    public String getUserEmail() {
        return user.getEmail();
    }

    private void signOut() {
        mAuth.signOut();
    }

}
