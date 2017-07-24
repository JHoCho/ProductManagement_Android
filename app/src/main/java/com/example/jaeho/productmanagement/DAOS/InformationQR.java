package com.example.jaeho.productmanagement.DAOS;

/**
 * Created by jaeho on 2017. 7. 20..
 */

public class InformationQR {
    private int id;
    private String qr;
    private String password;
    private String name;
    private String company;
    private String department;
    private String email;
    private String data_name;
    private String data_Serial_no;
    private String data_Buy_Date;
    private String data_Price;
    private String Data_Count;
    private String Data_Locateion;
    private String Data_CarryOut;
    private String Data_CarryInto;
    private String Data_Check_Data;

    public InformationQR(){}
    public InformationQR(int id,String password,String name,String company,String department,String email,String data_name,String data_Serial_no,String data_Buy_Date,String data_Price,String Data_Count,String Data_Locateion,String Data_CarryOut,String Data_CarryInto,String Data_Check_Data)
    {}

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData_name() {
        return data_name;
    }

    public void setData_name(String data_name) {
        this.data_name = data_name;
    }

    public String getData_Serial_no() {
        return data_Serial_no;
    }

    public void setData_Serial_no(String data_Serial_no) {
        this.data_Serial_no = data_Serial_no;
    }

    public String getData_Buy_Date() {
        return data_Buy_Date;
    }

    public void setData_Buy_Date(String data_Buy_Date) {
        this.data_Buy_Date = data_Buy_Date;
    }

    public String getData_Price() {
        return data_Price;
    }

    public void setData_Price(String data_Price) {
        this.data_Price = data_Price;
    }

    public String getData_Count() {
        return Data_Count;
    }

    public void setData_Count(String data_Count) {
        Data_Count = data_Count;
    }

    public String getData_Locateion() {
        return Data_Locateion;
    }

    public void setData_Locateion(String data_Locateion) {
        Data_Locateion = data_Locateion;
    }

    public String getData_CarryOut() {
        return Data_CarryOut;
    }

    public void setData_CarryOut(String data_CarryOut) {
        Data_CarryOut = data_CarryOut;
    }

    public String getData_CarryInto() {
        return Data_CarryInto;
    }

    public void setData_CarryInto(String data_CarryInto) {
        Data_CarryInto = data_CarryInto;
    }

    public String getData_Check_Data() {
        return Data_Check_Data;
    }

    public void setData_Check_Data(String data_Check_Data) {
        Data_Check_Data = data_Check_Data;
    }

}
