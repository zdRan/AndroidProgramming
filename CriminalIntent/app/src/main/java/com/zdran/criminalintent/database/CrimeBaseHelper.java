package com.zdran.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.zdran.criminalintent.database.CrimeDbSchema.CrimeTable;

/**
 * Create by Ranzd on 2020-04-26 19:52
 *
 * @author cm.zdran@foxmail.com
 */
public class CrimeBaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "CrimeBaseHelper";
    private static final String DATABASE_NAME = "crimeBase.db";
    private static final int VERSION = 1;

    public CrimeBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: 创建数据库");
        db.execSQL("create table " + CrimeTable.NAME
                + "("
                + " _id integer primary key autoincrement,"
                + CrimeTable.Cols.UUID + ","
                + CrimeTable.Cols.TITLE + ","
                + CrimeTable.Cols.SUSPECT + ","
                + CrimeTable.Cols.DATE + ","
                + CrimeTable.Cols.SOLVED
                + ")"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
