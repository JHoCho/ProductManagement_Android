package com.example.jaeho.productmanagement.utils;

import com.example.jaeho.productmanagement.Model.DO.UserDO;

/**
 * Created by jaeho on 2017. 8. 29..
 */

public class CurentUser extends UserDO {
    private static volatile CurentUser instance = null;
    private CurentUser(){}
    private String name;
    private String adminID;
    private String companyName;
    private String position;
    private String idNo;
    private String email;
    private CurentUser(UserDO userDO){
        name=userDO.getName();
        adminID=userDO.getAdminID();
        companyName = userDO.getCompanyName();
        position = userDO.getPosition();
        idNo = userDO.getIdNo();
        email = userDO.getEmail();
    }
    public static CurentUser getInstance(){
        if(instance == null){
            synchronized ( CurentUser.class){
                if(instance == null){
                    //instance = new CurentUser();
                    //맨처음 로그인시 싱글톤 초기화가 되어 이부분은 불려서는 안될곳입니다.
                    System.out.println("CurnetUSer의 들어와선 안될곳");
                }
            }
        }
        return instance;
    }

    public static CurentUser getInstance(UserDO userDO){
        if(instance == null){
            synchronized ( CurentUser.class){
                if(instance == null){
                    instance = new CurentUser(userDO);
                    System.out.println("CurnetUSer의 들어와야할곳.");
                }
            }
        }
        return instance;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAdminID() {
        return adminID;
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String getPosition() {
        return position;
    }

    @Override
    public String getIdNo() {
        return idNo;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
