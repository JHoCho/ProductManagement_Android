package com.example.jaeho.productmanagement.utils;

/**
 * Created by jaeho on 2017. 7. 24..
 */

public class ServerInform {
    private String serverURL = "http://172.20.10.3";
    private String serverInsertDiractory ="/pushData";
    private Integer serverPort = 3000;
    private String st="";
    final String data = st;
    public ServerInform(){}
    public String getServerURL() {
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public String getServerInsertDiractory() {
        return serverInsertDiractory;
    }

    public void setServerInsertDiractory(String serverInsertDiractory) {
        this.serverInsertDiractory = serverInsertDiractory;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }
}