package com.zdran.criminalintent.model;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地缓存
 * Create by Ranzd on 2020-04-25 10:07
 *
 * @author cm.zdran@foxmail.com
 */
public class LocalCache {
    private static LocalCache mLocalCache;

    private Map<String, String> mCache;

    private LocalCache(Context context) {
        mCache = new HashMap<>();
    }
    public static LocalCache getInstance(Context context){
        if (mLocalCache == null) {
            mLocalCache = new LocalCache(context);
        }
        return mLocalCache;
    }

    public void put(String key, String value) {
        mCache.put(key, value);
    }

    public String get(String key) {
        return mCache.get(key);
    }

    public String get(String key, String defaultValue) {
        String value = mCache.get(key);
        return value == null ? defaultValue : value;
    }
}
