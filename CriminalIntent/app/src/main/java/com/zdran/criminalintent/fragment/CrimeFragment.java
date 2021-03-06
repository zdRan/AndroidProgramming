package com.zdran.criminalintent.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import com.zdran.criminalintent.R;
import com.zdran.criminalintent.activity.DatePickerDialogActivity;
import com.zdran.criminalintent.model.Crime;
import com.zdran.criminalintent.model.CrimeLab;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * Create by Ranzd on 2020-04-18 22:15
 *
 * @author cm.zdran@foxmail.com
 */
public class CrimeFragment extends Fragment {
    private static final String TAG = "CrimeFragment";
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 1;
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_TIME = 2;
    private static final int REQUEST_CONTACT = 3;
    private static final String EXTRA_DELETE = "com.zdran.criminalintent.delete";

    private Crime mCrime;
    private final Intent pickIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);


    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private Button mReportButton;
    private Button mSuspectButton;

    private CheckBox mSolvedCheckBox;

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CRIME_ID, crimeId);
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(bundle);
        return crimeFragment;
    }

    public static boolean isDelete(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.getBooleanExtra(EXTRA_DELETE, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID uuid = (UUID) Objects.requireNonNull(getArguments()).getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.getCrimeLab(getActivity()).getCrime(uuid);
        Log.d(TAG, "创建 CrimeFragment: " + mCrime.toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 执行：" + mCrime.getTitle());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 执行：" + mCrime.getTitle());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 执行 " + mCrime.getTitle());
        CrimeLab.getCrimeLab(getActivity()).updateCrime(mCrime);

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 执行 " + mCrime.getTitle());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: 执行 " + mCrime.getTitle());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 执行 " + mCrime.getTitle());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        this.initView(v);

        this.setTitleField();
        this.setDateButton();
        this.setTimeButton();
        this.setSolvedCheckBox();
        this.setReportButton();
        this.setSuspectButton();
        //验证禁用是否生效
        //pickIntent.addCategory(Intent.CATEGORY_APP_BROWSER);
        this.checkActivity();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_crime:
                this.deleteItem();
                getActivity().finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Log.d(TAG, "onActivityResult: 执行！！" + requestCode);
            Date date = DatePickerFragment.getDate(data);
            Calendar target = Calendar.getInstance();
            target.setTime(Objects.requireNonNull(date));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mCrime.getDate());
            calendar.set(Calendar.YEAR, target.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, target.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, target.get(Calendar.DAY_OF_MONTH));
            mCrime.setDate(calendar.getTime());
            return;
        }
        if (requestCode == REQUEST_TIME) {
            Date date = TimePickerFragment.getDate(data);
            Calendar target = Calendar.getInstance();
            target.setTime(Objects.requireNonNull(date));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mCrime.getDate());
            calendar.set(Calendar.HOUR, target.get(Calendar.HOUR));
            calendar.set(Calendar.MINUTE, target.get(Calendar.MINUTE));
            mCrime.setDate(calendar.getTime());
            return;
        }
        if (requestCode == REQUEST_CONTACT && data != null) {
            Uri uri = data.getData();
            String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
            Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver()
                    .query(Objects.requireNonNull(uri), queryFields, null, null, null);

            if (Objects.requireNonNull(cursor).getCount() == 0) {
                Objects.requireNonNull(cursor).close();
                return;
            }
            cursor.moveToFirst();
            String name = cursor.getString(0);
            mCrime.setSuspect(name);
            mSuspectButton.setText(name);

        }
        updateDate();
    }


    private void initView(View v) {
        mTitleField = v.findViewById(R.id.crime_title);
        //日期
        mDateButton = v.findViewById(R.id.crime_date);
        //是否解决
        mSolvedCheckBox = v.findViewById(R.id.crime_solved);
        mTimeButton = v.findViewById(R.id.crime_time);
        mReportButton = v.findViewById(R.id.crime_report);
        mSuspectButton = v.findViewById(R.id.crime_suspect);
    }

    private void setTitleField() {
        mTitleField.setText(mCrime.getTitle());
        //标题
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setDateButton() {
        updateDate();
        //书中的代码有问题，应该启用按钮
        //mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
//                //类似于 startActivityForResult
//                dialog.setTargetFragment(CrimeFragment.this, CrimeFragment.REQUEST_DATE);
//                dialog.show(Objects.requireNonNull(getFragmentManager()), DIALOG_DATE);
                startActivityForResult(DatePickerDialogActivity.newIntent(getActivity(), mCrime.getDate()), REQUEST_DATE);
            }
        });
    }

    private void setTimeButton() {
        updateDate();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, CrimeFragment.REQUEST_TIME);
                dialog.show(Objects.requireNonNull(getFragmentManager()), DIALOG_TIME);
            }
        });
    }

    private void setSolvedCheckBox() {
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
    }

    private void setReportButton() {
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
//                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
//                intent = Intent.createChooser(intent, getString(R.string.send_report));
//                startActivity(intent);

                Intent intent = ShareCompat.IntentBuilder.from(Objects.requireNonNull(getActivity()))
                        .setText(getCrimeReport())
                        .setSubject(getString(R.string.crime_report_subject))
                        .setChooserTitle(getString(R.string.send_report))
                        .createChooserIntent();
                startActivity(intent);
            }
        });
    }

    private void setSuspectButton() {
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickIntent, REQUEST_CONTACT);
            }
        });
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
        mTimeButton.setText(mCrime.getDate().toString());
    }

    private void deleteItem() {
        CrimeLab.getCrimeLab(getActivity()).deleteCrime(mCrime.getId());
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DELETE, true);
        Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, intent);
    }

    private String getCrimeReport() {

        String solved = mCrime.isSolved() ?
                getString(R.string.crime_report_solved) :
                getString(R.string.crime_report_unsolved);
        String dateString = DateFormat.format("yyyy-MM-dd E hh:mm:ss", mCrime.getDate()).toString();
        String suspect = mCrime.getSuspect();
        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }

        return getString(R.string.crime_report, mCrime.getTitle(), dateString, solved, suspect);
    }

    /**
     * 检查是否有联系人、发送信息等相关应用
     */
    private void checkActivity() {
        PackageManager pm = Objects.requireNonNull(getActivity()).getPackageManager();
        //如果没有联系人相关应用，则禁用
        if (pm.resolveActivity(pickIntent, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            mSuspectButton.setEnabled(false);
        }
    }
}
