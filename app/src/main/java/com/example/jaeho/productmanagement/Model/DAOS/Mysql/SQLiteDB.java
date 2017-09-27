package com.example.jaeho.productmanagement.Model.DAOS.Mysql;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import com.example.jaeho.productmanagement.Model.DO.QRDO;
import com.example.jaeho.productmanagement.utils.CurentUser;

import java.util.ArrayList;
import java.util.HashSet;

import static android.content.Context.MODE_PRIVATE;
import static com.example.jaeho.productmanagement.Controller.Activities.SelectLocationActivity.selectedSt1;
import static com.example.jaeho.productmanagement.Controller.Activities.SelectLocationActivity.selectedSt2;
import static com.example.jaeho.productmanagement.Model.DAOS.Mysql.QRReaderDbHelper.DATABASE_NAME;

/**
 * Created by jaeho on 2017. 9. 4..
 */

public class SQLiteDB {
    QRReaderDbHelper qrReaderDbHelper;
    SQLiteDatabase db;
    Context context;
    private static String TAG = "SQLiteDB.java";
    public static String TABLE_NAME = "QR";

    public SQLiteDB(Context context) {
        this.context = context;
        qrReaderDbHelper = new QRReaderDbHelper(context);
        // db = qrReaderDbHelper.getWritableDatabase();
        db = qrReaderDbHelper.getWritableDatabase();
        try {
            //MODE_PRIVATE는 0x00000000 Val을 가지고 있으며 불리는 어플리케이션 외에서는 불릴 수없다(private 느낌)는 것을 의미한다.
            db = this.context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TABLE_NAME
                    + " (idx integer not null primary key autoincrement,adminId TEXT,building TEXT,companyName TEXT,date TEXT,detailedProductName TEXT,floor TEXT,location TEXT,price TEXT,productName TEXT,roomName TEXT,serialNumber TEXT);");

            //https://stackoverflow.com/questions/3037767/create-sqlite-database-in-android db예시

        } catch (Exception e) {
            System.out.println(TAG);
            e.printStackTrace();
        }
        
    }

    public void initData(ArrayList<QRDO> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            db.execSQL("INSERT INTO "
                    + TABLE_NAME
                    + " (adminId, building,companyName,date,detailedProductName,floor,location,price,productName,roomName,serialNumber)"
                    + " VALUES ('" + arrayList.get(i).getAdminID() + "','" + arrayList.get(i).getBuilding() + "','" + arrayList.get(i).getCompanyName()
                    + "','" + arrayList.get(i).getDate() + "','" + arrayList.get(i).getDetailedProductName() + "','" + arrayList.get(i).getFloor() + "','" + arrayList.get(i).getLocation()
                    + "','" + arrayList.get(i).getPrice() + "','" + arrayList.get(i).getProductName() + "','" + arrayList.get(i).getRoomName() + "','" + arrayList.get(i).getSerialNumber() + "' );");
        }
    }

    public void clearData() {
        db.execSQL("DROP TABLE " + TABLE_NAME + ";");
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME
                + " (idx integer not null primary key autoincrement,adminId TEXT,building TEXT,companyName TEXT,date TEXT,detailedProductName TEXT,floor TEXT,location TEXT,price TEXT,productName TEXT,roomName TEXT,serialNumber TEXT);");
    }

    public String getDataCompanyName() {
        try {
            String query = "SELECT companyName FROM " + TABLE_NAME + ";";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            return cursor.getString(0);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();

            return null;
        }
    }

    public ArrayList<String> getTopLevelLocation(){
        try {//빌딩명 같은 룸네임 모두 가져오기.
            String query = "SELECT "+"building"+" FROM " + TABLE_NAME + " WHERE companyName='"+ CurentUser.getInstance().getCompanyName()+"';";
            return getResertAboutQuery(query);
        } catch (CursorIndexOutOfBoundsException e) {
            System.out.println("SQLiteDB.java");
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<String> getMiddleLevelLocation(String building){
        try {//빌딩명 같은 층 모두 가져오기.
            String query = "SELECT "+"floor"+" FROM " + TABLE_NAME + " WHERE building='"+building+"';";
            return getResertAboutQuery(query);
        } catch (CursorIndexOutOfBoundsException e) {
            System.out.println("SQLiteDB.java");
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<String> getLowLevelLocation(String building,String floor){
        try {//빌딩명 이랑 층 같은 방 모두 가져오기.
            String query = "SELECT "+"roomName"+" FROM " + TABLE_NAME + " WHERE building='"+building+"' and floor='"+floor+"';";
            return getResertAboutQuery(query);
        } catch (CursorIndexOutOfBoundsException e) {
            System.out.println("SQLiteDB.java");
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<String> getTopLevelPname(){
        try {//회사명 같은 곳에서 물건명 전부 가져오기 매개변수 세개 받아서 ""일경우 *로 대체해주면 될듯.
            String query = "SELECT "+"productName"+" FROM " + TABLE_NAME + " WHERE companyName='"+ CurentUser.getInstance().getCompanyName()+defineQuery(selectedSt1);
            Cursor cursor = db.rawQuery(query, null);
            ArrayList<String> arl = new ArrayList<>();
            HashSet<String> set = new HashSet<>();
            if(cursor!=null){
                while (cursor.moveToNext()==true)
                {
                    if(set.add(cursor.getString(0).trim()))
                    {
                        arl.add(cursor.getString(0).trim());
                    }else{
                        System.out.println("SQLiteDB.java"+"값이 중복되어 무시됨");
                    }
                }
                return arl;
            }
            return getResertAboutQuery(query);
        } catch (CursorIndexOutOfBoundsException e) {
            System.out.println("SQLiteDB.java");
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<String> getMiddleLevelPname(String productName){
        try {// 현재는 대분류 받아서 그거랑 같은 미들템들만 뽑아줌 앞에 대 중 소 추가해서 ""이면 *로 대체하고 나머지는 그대로 들어가게 코딩하면될듯
            String query = "SELECT "+"detailedProductName"+" FROM " + TABLE_NAME + " WHERE productName='"+productName+defineQuery(selectedSt1);
            return getResertAboutQuery(query);
        } catch (CursorIndexOutOfBoundsException e) {
            System.out.println("SQLiteDB.java");
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<String> getLowLevelPname(String productName,String detailedProductName){
        try {// 현재는 대분류 랑 중분류 받아서 맨 하위 템들만 뽑아줌 앞에 대 중 소 추가해서 ""이면 *로 대체하고 나머지는 그대로 들어가게 코딩하면될듯
            String query = "SELECT "+"serialNumber"+" FROM " + TABLE_NAME + " WHERE productName='"+productName+"' and detailedProductName='"+detailedProductName+defineQuery(selectedSt1);
            return getResertAboutQuery(query);
        } catch (CursorIndexOutOfBoundsException e) {
            System.out.println("SQLiteDB.java");
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<String> getResertAboutQuery(String query){
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> arl = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        if(cursor!=null){
            while (cursor.moveToNext()==true)
            {
                if(set.add(cursor.getString(0).trim()))
                {
                    arl.add(cursor.getString(0).trim());
                }else{
                    System.out.println("SQLiteDB.java"+"값이 중복되어 무시됨");
                }
            }
            return arl;
        }
        System.out.println("SQLiteDB.java null");
        return null;
    }
    private String defineQuery(String[] st){
        String building;
        String floor ;
        String roomName;
        if(st[0].equals("")){
            return  "';";
        }else if(st[1].equals("")){
            building = st[0];
            return "' and building='"+building+"';";
        }else if(st[2].equals("")){
            building = st[0];
            floor = st[1];
            return  "' and building='"+building+"' and floor='"+floor+"';";
        }else {
            building = st[0];
            floor = st[1];
            roomName = st[2];
            return  "' and building='"+building+"' and floor='"+floor+"' and roomName='"+roomName+"';";
        }
    }
    private String defineQuery2(String[] st){
        String productName;
        String detailedProductName ;
        String serialNumber;
        if(st[0].equals("")){
            return  "';";
        }else if(st[1].equals("")){
            productName = st[0];
            return "SELECT "+"count(*)"+" FROM " + TABLE_NAME + " WHERE productName='"+productName+defineQuery(selectedSt1);
        }else if(st[2].equals("")){
            productName = st[0];
            detailedProductName = st[1];
            return  "SELECT "+"count(*)"+" FROM " + TABLE_NAME + " WHERE productName='"+productName+"' and detailedProductName='"+detailedProductName+defineQuery(selectedSt1);
        }else {
            productName = st[0];
            detailedProductName = st[1];
            serialNumber = st[2];
            return  "SELECT "+"count(*)"+" FROM " + TABLE_NAME + " WHERE productName='"+productName+"' and detailedProductName='"+detailedProductName+"' and serialNumber='"+serialNumber+defineQuery(selectedSt1);
        }
    }
    public int getNumOfRow(){
        Cursor cursor = db.rawQuery(defineQuery2(selectedSt2), null);
        cursor.moveToFirst();
        if(cursor!=null){
            return Integer.parseInt(cursor.getString(0));
        }
        return 0;
    }

}
