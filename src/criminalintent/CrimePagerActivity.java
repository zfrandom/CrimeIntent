package com.example.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

import static com.example.android.criminalintent.CrimeFragment.newInstance;

/**
 * Created by zifeifeng on 4/26/17.
 */

public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.Callbacks, CrimeFragment.OnButtonClickListener{

    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private static final String EXTRA_CRIME_ID="com.example.android.criminalintent.crime_id";
    public static Intent newIntent(Context packageContext, UUID id){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, id);
        return intent;
    }


    @Override
    public void onCrimeUpdated(Crime crime) {

    }

    @Override
    public void jumpToTheLastOne(){
        mViewPager.setCurrentItem(mCrimes.size()-1);

    }

    @Override
    public void jumpToTheFirstOne() {
        mViewPager.setCurrentItem(0);
//        if(findViewById(R.id.fragment_detail_container) == null){
//            Intent intent = CrimePagerActivity.newIntent(this, mCrimes.get(0).getId());
//            startActivity(intent);
//        }
//        else{
//            Fragment crimeDetail = CrimeFragment.newInstance(mCrimes.get(0).getId());
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail_container, crimeDetail).commit();
//        }

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager);
        mCrimes = CrimeLab.get(this).getmCrimes();
        FragmentManager fm = getSupportFragmentManager();


        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                Fragment result = CrimeFragment.newInstance(crime.getId());
                return result;
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });


        for(int i = 0; i < mCrimes.size(); i++){
            if(mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
