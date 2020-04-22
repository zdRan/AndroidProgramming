package com.zdran.criminalintent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.zdran.criminalintent.R;
import com.zdran.criminalintent.fragment.CrimeFragment;
import com.zdran.criminalintent.model.Crime;
import com.zdran.criminalintent.model.CrimeLab;

import java.util.List;
import java.util.UUID;

/**
 * Create by Ranzd on 2020-04-19 19:36
 *
 * @author cm.zdran@foxmail.com
 */
public class CrimePagerActivity extends AppCompatActivity {
    private static final String EXTRA_CIRME_ID =
            "com.zdran.criminalintent.cirme_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimeList;

    public static Intent newIntent(Context context, UUID crimeId) {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CIRME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CIRME_ID);

        mViewPager = findViewById(R.id.activity_crime_page_view_page);
        mCrimeList = CrimeLab.getCrimeLab(this).getCrimeList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        //由于 API 的变动，这里 FragmentStatePagerAdapter 需要两个参数
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager,
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

            @NonNull
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimeList.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimeList.size();
            }
        });
        Crime crime = CrimeLab.getCrimeLab(this).getCrime(crimeId);
        mViewPager.setCurrentItem(crime.getSorted());

    }
}
