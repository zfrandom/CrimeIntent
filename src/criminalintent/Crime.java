package com.example.android.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zifeifeng on 4/25/17.
 */

public class Crime {
    private final UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mSuspect;
    public Crime(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Crime(UUID id){
        mId = id;
        mDate = new Date();
    }
    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
    public Date getDate() {
        return mDate;
    }
    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }
    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getmSuspect() {
        return mSuspect;
    }
    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }

    public void setmSuspect(String mSuspect) {
        this.mSuspect = mSuspect;
    }
}
