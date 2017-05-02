package com.example.android.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by zifeifeng on 4/25/17.
 */

public class CrimeListActivity extends SimpleFragmentActivity implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks{

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }


    @Override
    public void onCrimeSelected(Crime crime) {
        if(findViewById(R.id.fragment_detail_container) == null){
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        }
        else{
            Fragment crimeDetail = CrimeFragment.newInstance(crime.getId());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail_container, crimeDetail).commit();
        }

    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment crimeListFragment = (CrimeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        crimeListFragment.updateUI();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }
}
