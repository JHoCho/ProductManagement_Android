package com.example.jaeho.productmanagement.DAOS;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 7. 20..
 */

public class FireBaseDAO implements InformationDAO {
    @Override
    public void insert(InformationQR qr) {
        
    }

    @Override
    public void deleteInformation(int id) {

    }

    @Override
    public ArrayList<InformationQR> getInformation() {
        return null;
    }

    @Override
    public ArrayList<InformationQR> getInformationByQR(InformationQR qr) {
        return null;
    }

    @Override
    public boolean getInformationById(String id, String pw) {
        return false;
    }

    @Override
    public void updateInformation(InformationQR qr) {

    }
}
