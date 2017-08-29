package com.example.jaeho.productmanagement.Model.DAOS.NodeJS;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.jaeho.productmanagement.Model.DAOS.InformationDAO;
import com.example.jaeho.productmanagement.Model.DO.InformationQRDO;
import com.example.jaeho.productmanagement.utils.ServerInform;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 7. 24..
 */

public abstract class NodeJSDAO implements InformationDAO {
    ArrayList<InformationQRDO> infoList;

    public NodeJSDAO() {
        infoList = new ArrayList<InformationQRDO>();
    }


    Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            String answer = bun.getString("ans");
            //tv.setText(answer);
            //   Toast.makeText(CheckQRActivity.this,answer,Toast.LENGTH_SHORT).show();
            Log.i("handler", answer);
        }
    };

    @Override
    public void insert(InformationQRDO qr) {
        final ServerInform sv = new ServerInform();
        final InformationQRDO a = qr;
        String st1, st2;
        SendHttpWithMsg sendHttpWithMsg = new SendHttpWithMsg(sv.getServerURL() + ":" + sv.getServerPort().toString() + sv.getServerInsertDiractory(), sv.getServerPort().toString(), a.getQr(), a.getQr());
        String ans = sendHttpWithMsg.getSendedMsg();

        Bundle bun = new Bundle();
        bun.putString("ans", ans);
        Message msg = handler.obtainMessage();
        msg.setData(bun);
        handler.sendMessage(msg);


        infoList.add(qr);
    }

    @Override
    public void deleteInformation(int id) {
        infoList.remove(id);
    }

    @Override
    public ArrayList<InformationQRDO> getInformation() {
        return infoList;
    }

    @Override
    public ArrayList<InformationQRDO> getInformationByQR(InformationQRDO qr) {

        final ServerInform sv = new ServerInform();
        sv.setServerInsertDiractory("/findData");
        final InformationQRDO a = qr;
        SendHttpWithMsg sendHttpWithMsg = new SendHttpWithMsg(sv.getServerURL() + ":" + sv.getServerPort().toString() + sv.getServerInsertDiractory(), sv.getServerPort().toString(), a.getQr(), a.getQr());
        String ans = sendHttpWithMsg.getSendedMsg();
        Bundle bun = new Bundle();
        bun.putString("ans", ans);
        Message msg = handler.obtainMessage();
        msg.setData(bun);
        handler.sendMessage(msg);


        infoList.add(qr);
        return infoList;
    }

    @Override
    public void checkSignIn(final String id,final  String pw) {
        return;
    }

    @Override
    public void checkSignUp(final String id,final  String pw) {
        return;
    }

    public boolean getInformationById(final String id,final  String pw) {

        final ServerInform sv = new ServerInform();
        sv.setServerInsertDiractory("/logIn");
        SendHttpWithMsg sendHttpWithMsg = new SendHttpWithMsg(sv.getServerURL() + ":" + sv.getServerPort().toString() + sv.getServerInsertDiractory(), sv.getServerPort().toString(), id, pw);
        String ans = sendHttpWithMsg.getSendedMsg();
        Bundle bun = new Bundle();
        bun.putString("ans", ans);
        Message msg = handler.obtainMessage();
        msg.setData(bun);
        handler.sendMessage(msg);
        if (ans.equals("ok")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateInformation(InformationQRDO qr) {

    }
    public void makeAccount(InformationQRDO qr){}
    public void accessUserInform(final String id,final String pw){};
}