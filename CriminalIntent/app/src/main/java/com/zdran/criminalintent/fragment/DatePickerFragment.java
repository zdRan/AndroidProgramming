package com.zdran.criminalintent.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.zdran.criminalintent.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * 托管对话框的 Fragment
 * Create by Ranzd on 2020-04-23 08:47
 *
 * @author cm.zdran@foxmail.com
 */
public class DatePickerFragment extends DialogFragment {
    private static final String ARGS_DATE = "date";
    private static final String EXTRA_DATE = "com.zdran.criminalintent.fragment.data";
    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGS_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Date getDate(Intent intent) {
        if (Objects.isNull(intent)) {
            return null;
        }
        return (Date) intent.getSerializableExtra(EXTRA_DATE);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        this.initDialog(v);
        this.setDatePicker();

        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setTitle(R.string.date_picker_title)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day).getTime();
                        DatePickerFragment.this.sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();

    }

    private void initDialog(View v) {
        mDatePicker = v.findViewById(R.id.dialog_date_picker);
    }

    private void setDatePicker() {
        Date date = (Date) Objects.requireNonNull(getArguments()).getSerializable(ARGS_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Objects.requireNonNull(date));
        mDatePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }

    private void sendResult(int resultCode, Date date) {
        if (Objects.isNull(getTargetFragment())) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), resultCode, intent);

    }
}
