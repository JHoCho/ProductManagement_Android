package com.example.jaeho.productmanagement.Model.DAOS.Mysql;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import com.example.jaeho.productmanagement.Model.DO.QRDO;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
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
}
