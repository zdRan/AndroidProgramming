package com.zdran.criminalintent.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zdran.criminalintent.database.CrimeBaseHelper;
import com.zdran.criminalintent.database.CrimeDbSchema.CrimeTable;

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
        return null;
    }

    public Crime getCrime(UUID uuid) {
        return null;
    }

    public void addCrime(Crime crime) {
        ContentValues contentValues = getContentValeus(crime);
        mSQLiteDatabase.insert(CrimeTable.NAME, null, contentValues);
    }

    public void updateCrime(Crime crime) {
        String uuid = crime.getId().toString();
        ContentValues value = getContentValeus(crime);
        mSQLiteDatabase.update(CrimeTable.NAME, value,
                CrimeTable.Cols.UUID + " = ?", new String[]{uuid});
    }

    public void deleteCrime(UUID uuid) {
        mSQLiteDatabase.delete(CrimeTable.NAME,
                CrimeTable.Cols.UUID + " = ?", new String[]{uuid.toString()});
    }

    private static ContentValues getContentValeus(Crime crime) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrimeTable.Cols.UUID, crime.getId().toString());
        contentValues.put(CrimeTable.Cols.TITLE, crime.getTitle());
        contentValues.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        contentValues.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        return contentValues;
    }
}
