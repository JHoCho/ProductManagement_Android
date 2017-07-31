package com.example.jaeho.productmanagement.DAOS;

import android.content.Context;

import com.example.jaeho.productmanagement.DAOS.Firebase.FireBaseDAO;

/**
 * Created by jaeho on 2017. 7. 24..
 */

public class NowUsingDAO extends FireBaseDAO {
    //extends부분에 현재 사용하는 클래스를 상속받아 다른 클래스에 동적으로 할당되도록 유도합니다.
    public NowUsingDAO(Context context){
        super(context);
    }
}
