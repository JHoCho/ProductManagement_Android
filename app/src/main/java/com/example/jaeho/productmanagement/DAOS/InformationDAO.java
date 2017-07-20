package com.example.jaeho.productmanagement.DAOS;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 7. 20..
 */

public interface InformationDAO {
    void insert(InformationQR qr);
    void deleteInformation(int id);
    ArrayList<InformationQR> getInformation();
    ArrayList<InformationQR> getInformationByQR(InformationQR qr);
    boolean getInformationById(String id, String pw);
    void updateInformation(InformationQR qr);

}
