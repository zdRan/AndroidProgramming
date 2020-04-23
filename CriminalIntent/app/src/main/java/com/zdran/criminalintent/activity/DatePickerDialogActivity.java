package com.zdran.criminalintent.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.zdran.criminalintent.fragment.DatePickerFragment;

import java.util.Date;

/**
 * Create by Ranzd on 2020-04-23 14:48
 *
 * @author cm.zdran@foxmail.com
 */
public class DatePickerDialogActivity extends SingleFragmentActivity implements DatePickerFragment.DateChangeListener {
    private static final String DIALOG_DATE = "dialogDate";
    private static final String TAG = "DatePickerDialogActivity";

    private Date mDate;
    public static Intent newIntent(Context context, Date date) {
        Intent intent = new Intent(context, DatePickerDialogActivity.class);
        intent.putExtra(DIALOG_DATE, date);
        return intent;
    }

    public static Date getDate(Intent intent) {
       return (Date) intent.getSerializableExtra(DIALOG_DATE);
    }

    @Override
    protected Fragment createFragment() {
        Date date = (Date) getIntent().getSerializableExtra(DIALOG_DATE);
        return DatePickerFragment.newInstance(date);
    }

    @Override
    public void finish() {
        Log.d(TAG, "finish: 执行");
        Intent intent = new Intent();
        intent.putExtra(DIALOG_DATE, mDate);
        setResult(Activity.RESULT_OK, intent);
        super.finish();
    }

    @Override
    public void sendDate(Date date) {
        mDate = date;
    }
}
