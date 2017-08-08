package com.example.jaeho.productmanagement.DAOS;

import com.example.jaeho.productmanagement.QNAActivitys.CustomQNAAdapter;
import com.example.jaeho.productmanagement.QNAActivitys.QNADO;
import com.example.jaeho.productmanagement.jheaders.InformationQR;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 7. 20..
 */

public interface InformationDAO {//앱스트렉으로 바꾼다음 인스턴스로 넣어주는걸 둘다 넣고 만들어놓고 주석처리할것.
    void insert(InformationQR qr);
    void deleteInformation(int id);
    ArrayList<InformationQR> getInformation();
    ArrayList<InformationQR> getInformationByQR(InformationQR qr);
    CustomQNAAdapter getAdapter();
    void checkSignIn(final String id, final String pw);//아이디 비번으로 로그인 여부
    void checkSignUp(final String id, final String pw);//아이디 비번으로 회원가입여부
    void updateInformation(InformationQR qr);
    void makeAccount(final String id,final String pw);
    void accessUserInform(final String id,final String pw);
    void onStop();
    void addQna(QNADO qnado);
    String getUserName();
    String getUserEmail();
}
