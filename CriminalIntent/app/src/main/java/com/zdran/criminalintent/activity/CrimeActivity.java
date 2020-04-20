package com.zdran.criminalintent.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.zdran.criminalintent.fragment.CrimeFragment;

import java.util.UUID;

/**
 * Create by Ranzd on 2020-04-17 22:06
 *
 * @author cm.zdran@foxmail.com
 */
public class CrimeActivity extends SingleFragmentActivity {
    private static final String EXTRA_CIRME_ID =
            "com.zdran.criminalintent.cirme_id";

    public static Intent newIntent(Context context, UUID crimeId) {
        Intent intent = new Intent(context, CrimeActivity.class);
        intent.putExtra(EXTRA_CIRME_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID uuid = (UUID) getIntent().getSerializableExtra(CrimeActivity.EXTRA_CIRME_ID);
        return CrimeFragment.newInstance(uuid);
    }
}
