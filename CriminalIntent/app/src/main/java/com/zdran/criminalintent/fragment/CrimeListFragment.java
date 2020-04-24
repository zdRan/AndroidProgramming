package com.zdran.criminalintent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zdran.criminalintent.R;
import com.zdran.criminalintent.activity.CrimePagerActivity;
import com.zdran.criminalintent.model.Crime;
import com.zdran.criminalintent.model.CrimeLab;

import java.util.List;
import java.util.Objects;

/**
 * Create by Ranzd on 2020-04-19 19:36
 *
 * @author cm.zdran@foxmail.com
 */
public class CrimeListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private CrimeListAdapter mCrimeListAdapter;
    private int mPosition = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.updateUI();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                this.newCrimeMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class CrimeListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        private Crime mCrime;

        /**
         * 这里由于 API 升级，已经与书中的代码不一致了
         *
         * @param itemView 列表项的视图
         */
        CrimeListHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);
            mSolvedImageView = itemView.findViewById(R.id.crime_solved);
            //设置点击事件
            itemView.setOnClickListener(this);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(DateFormat.format("yyyy-MM-dd E hh:mm:ss", mCrime.getDate()));
            mSolvedImageView.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public void onClick(View v) {
            //记录当前位置
            mPosition = super.getBindingAdapterPosition();

            //打开详情页
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }

    private class CrimeListAdapter extends RecyclerView.Adapter<CrimeListHolder> {
        private List<Crime> mCrimeList;

        public CrimeListAdapter(List<Crime> crimeList) {
            mCrimeList = crimeList;
        }

        @NonNull
        @Override
        public CrimeListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //使用 LayoutInflater 来加载 item 视图，并传递给 ViewHolder
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View itemView;
            switch (viewType) {
                case 0:
                    itemView = layoutInflater.inflate(R.layout.list_item_crime_requires_police, parent, false);
                    break;
                case 1:
                    itemView = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
                    break;
                default:
                    itemView = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            }
            return new CrimeListHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeListHolder holder, int position) {
            Crime crime = mCrimeList.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimeList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mCrimeList.get(position).isRequiresPolice() ? 0 : 1;
        }
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.getCrimeLab(getActivity());
        List<Crime> crimeList = crimeLab.getCrimeList();
        if (Objects.isNull(mCrimeListAdapter)) {
            mCrimeListAdapter = new CrimeListAdapter(crimeList);
            mRecyclerView.setAdapter(mCrimeListAdapter);
        } else {
            mCrimeListAdapter.notifyItemChanged(mPosition);
        }
    }

    private void newCrimeMenu() {
        Crime crime = new Crime();
        CrimeLab.getCrimeLab(getActivity()).addCrime(crime);
        //打开详情页
        Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
        startActivity(intent);
    }

}
