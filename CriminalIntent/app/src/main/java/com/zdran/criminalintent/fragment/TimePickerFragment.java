package com.zdran.criminalintent.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.zdran.criminalintent.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * 时间 Fragment
 * Create by Ranzd on 2020-04-23 10:56
 *
 * @author cm.zdran@foxmail.com
 */
public class TimePickerFragment extends DialogFragment {
    private TimePicker mTimePicker;
    private static final String ARGS_TIME = "time";
    private static final String EXTRA_TIME = "com.zdran.criminalintent.fragment.time";

    public static TimePickerFragment newInstance(Date date) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGS_TIME, date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    public static Date getDate(Intent intent) {
        if (Objects.isNull(intent)) {
            return null;
        }
        return (Date) intent.getSerializableExtra(EXTRA_TIME);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);
        this.initView(v);
        this.setTimePicker();
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = mTimePicker.getHour();
                        int minute = mTimePicker.getMinute();
                        Date date = new GregorianCalendar(0,0,0,hour,minute).getTime();
                        TimePickerFragment.this.sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();

    }

    private void initView(View view) {
        mTimePicker = view.findViewById(R.id.dialog_time_picker);
    }

    private void setTimePicker() {
        Date date = (Date) Objects.requireNonNull(getArguments()).getSerializable(ARGS_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Objects.requireNonNull(date));

        mTimePicker.setHour(calendar.get(Calendar.HOUR));
        mTimePicker.setMinute(calendar.get(Calendar.MINUTE));

    }

    private void sendResult(int resultCode, Date date) {

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);
        Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), resultCode, intent);

    }
}
