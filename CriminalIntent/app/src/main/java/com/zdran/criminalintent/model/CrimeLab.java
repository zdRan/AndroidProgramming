package com.zdran.criminalintent.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zdran.criminalintent.database.CrimeBaseHelper;
import com.zdran.criminalintent.database.CrimeCursorWrapper;
import com.zdran.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Create by Ranzd on 2020-04-19 10:14
 *
 * @author cm.zdran@foxmail.com
 */
public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;

    private CrimeLab(Context context) {

        mContext = context.getApplicationContext();
        mSQLiteDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

    }

    public static CrimeLab getCrimeLab(Context context) {
        if (Objects.isNull(sCrimeLab)) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public List<Crime> getCrimeList() {
        List<Crime> crimeList = new ArrayList<>();
        CrimeCursorWrapper crimeCursorWrapper = this.queryCrime(null, null);

        crimeCursorWrapper.moveToFirst();
        while (!crimeCursorWrapper.isAfterLast()) {
            crimeList.add(crimeCursorWrapper.getCrime());
            crimeCursorWrapper.moveToNext();
        }

        crimeCursorWrapper.close();
        return crimeList;
    }

    public Crime getCrime(UUID uuid) {
        CrimeCursorWrapper crimeCursorWrapper = this.queryCrime(CrimeTable.Cols.UUID + " = ?",
                new String[]{uuid.toString()});
        if (crimeCursorWrapper.getCount() == 0) {
            return null;
        }
        crimeCursorWrapper.moveToFirst();
        return crimeCursorWrapper.getCrime();
    }

    public void addCrime(Crime crime) {
        ContentValues contentValues = getContentValues(crime);
        mSQLiteDatabase.insert(CrimeTable.NAME, null, contentValues);
    }

    public CrimeCursorWrapper queryCrime(String whereClause, String[] whereArgs) {
        Cursor cursor = mSQLiteDatabase.query(CrimeTable.NAME,
                null, whereClause, whereArgs,
                null, null, null);
        return new CrimeCursorWrapper(cursor);
    }

    public void updateCrime(Crime crime) {
        String uuid = crime.getId().toString();
        ContentValues value = getContentValues(crime);
        mSQLiteDatabase.update(CrimeTable.NAME, value,
                CrimeTable.Cols.UUID + " = ?", new String[]{uuid});
    }

    public void deleteCrime(UUID uuid) {
        mSQLiteDatabase.delete(CrimeTable.NAME,
                CrimeTable.Cols.UUID + " = ?", new String[]{uuid.toString()});
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrimeTable.Cols.UUID, crime.getId().toString());
        contentValues.put(CrimeTable.Cols.TITLE, crime.getTitle());
        contentValues.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());
        contentValues.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        contentValues.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        return contentValues;
    }
}
