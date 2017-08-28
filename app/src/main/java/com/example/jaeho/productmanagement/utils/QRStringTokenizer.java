package com.example.jaeho.productmanagement.utils;

import com.example.jaeho.productmanagement.Model.DO.QRDO;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 8. 22..
 */

public class QRStringTokenizer {
    private QRDO qrdo = new QRDO();
    public QRStringTokenizer(){

    }
    public QRStringTokenizer(String s){
        splitQR(s);
    }
    public void splitQR(String s){
        String[] result = s.split(",");
        qrdo.setProductName(result[0]);
        qrdo.setDetailedProductName(result[1]);
        qrdo.setSerialNumber(result[2]);
        qrdo.setCompanyName(result[3]);
        qrdo.setBuilding(result[4]);
        qrdo.setFloor(result[5]);
        qrdo.setRoomName(result[6]);
        qrdo.setDate(result[7]);
        qrdo.setAdminID(result[8]);
    }
    public QRDO getSplitedQRDO(){
        return qrdo;
    }
}
