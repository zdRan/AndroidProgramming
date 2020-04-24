package com.zdran.criminalintent.model;

import android.content.Context;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Create by Ranzd on 2020-04-19 10:14
 *
 * @author cm.zdran@foxmail.com
 */
public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimeList;
    private Map<UUID, Crime> mUUIDCrimeMap;

    private CrimeLab(Context context) {
        mCrimeList = new LinkedList<>();
        mUUIDCrimeMap = new HashMap<>();

    }

    public static CrimeLab getCrimeLab(Context context) {
        if (Objects.isNull(sCrimeLab)) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public List<Crime> getCrimeList() {
        return mCrimeList;
    }

    public Crime getCrime(UUID uuid) {
        return mUUIDCrimeMap.get(uuid);
    }

    public void addCrime(Crime crime) {
        mCrimeList.add(crime);
        mUUIDCrimeMap.put(crime.getId(), crime);
    }
}
