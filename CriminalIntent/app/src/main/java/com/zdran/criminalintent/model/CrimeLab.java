package com.zdran.criminalintent.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
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
    private Map<UUID, Crime> mUUIDCrimeMap;

    private CrimeLab(Context context) {
        mUUIDCrimeMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            crime.setRequiresPolice(i % 3 == 0);
            mUUIDCrimeMap.put(crime.getId(), crime);
        }
    }

    public static CrimeLab getCrimeLab(Context context) {
        if (Objects.isNull(sCrimeLab)) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public List<Crime> getCrimeList() {
        return new ArrayList<>(mUUIDCrimeMap.values());
    }

    public Crime getCrime(UUID uuid) {
        return mUUIDCrimeMap.get(uuid);
    }
}
