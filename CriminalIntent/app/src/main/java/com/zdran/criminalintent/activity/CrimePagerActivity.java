package com.zdran.criminalintent.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    private Button mJumpToFirstButton;
    private Button mJumpToLastButton;


    public static Intent newIntent(Context context, UUID crimeId) {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CIRME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        this.init();
        this.setViewPageAdapter();
        this.setJumpToFirstButton();
        this.setJumpToLastButton();

        //跳转到指定位置
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CIRME_ID);
        Crime crime = CrimeLab.getCrimeLab(this).getCrime(crimeId);
        //使用菜单栏打开页面时，sorted 始终为 0
//        mViewPager.setCurrentItem(crime.getSorted());
        List<Crime> crimeList = CrimeLab.getCrimeLab(this).getCrimeList();
        for (int i = 0; i < crimeList.size(); i++) {
            if (crimeList.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
            }
        }
    }

    @Override
    public void finish() {
        setResult(Activity.RESULT_OK);
        super.finish();
    }

    /**
     * 统一执行 findViewById 动作，以及部分变量的初始化动作
     */
    private void init() {
        mCrimeList = CrimeLab.getCrimeLab(this).getCrimeList();
        mViewPager = findViewById(R.id.activity_crime_page_view_page);
        mJumpToFirstButton = findViewById(R.id.jump_to_first_button);
        mJumpToLastButton = findViewById(R.id.jump_to_last_button);
    }

    /**
     * 设置 VIewPage 的 Adapter
     */
    private void setViewPageAdapter() {
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
    }

    private void setJumpToFirstButton() {
        mJumpToFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0, true);
            }
        });
    }

    private void setJumpToLastButton() {
        mJumpToLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mCrimeList.size() - 1, true);
            }
        });
    }
}
