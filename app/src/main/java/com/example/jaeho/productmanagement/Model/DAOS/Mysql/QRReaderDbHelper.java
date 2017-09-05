package com.example.jaeho.productmanagement.Model.DAOS.Mysql;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jaeho on 2017. 9. 4..
 */

public class QRReaderDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + QRReaderContract.QREntry.TABLE_NAME + " (" +
                    QRReaderContract.QREntry._ID + " INTEGER PRIMARY KEY," +
                    QRReaderContract.QREntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    QRReaderContract.QREntry.COLUMN_NAME_SUBTITLE + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + QRReaderContract.QREntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    //스키마 변경시 데이터베이스 버전을 1증가시켜줘야함.
    public static final String DATABASE_NAME = "QRReader.db";

    public QRReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
