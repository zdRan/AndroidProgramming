package com.zdran.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.zdran.criminalintent.model.Crime;

import java.util.Date;
import java.util.UUID;

import static com.zdran.criminalintent.database.CrimeDbSchema.CrimeTable;

/**
 * Create by Ranzd on 2020-04-28 13:26
 *
 * @author cm.zdran@foxmail.com
 */
public class CrimeCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));

        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setSuspect(suspect);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        return crime;
    }

}

