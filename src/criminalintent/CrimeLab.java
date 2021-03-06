package com.example.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.criminalintent.database.CrimeBaseHelper;
import com.example.android.criminalintent.database.CrimeCursorWrapper;
import com.example.android.criminalintent.database.CrimeDbSchema;
import com.example.android.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zifeifeng on 4/25/17.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;
    public static CrimeLab get (Context context){
        if(sCrimeLab == null)
            sCrimeLab = new CrimeLab(context);
        return sCrimeLab;
    }
    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved()?1:0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getmSuspect());
        values.put(CrimeTable.Cols.SEVERE, crime.ismRequiresPolice());
        values.put(CrimeTable.Cols.TIME, crime.getmTime().getTime().getTime());
        return  values;
    }
    public List<Crime> getmCrimes(){
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id){
        Cursor cursor = queryCrimes(CrimeTable.Cols.UUID +" =?", new String[]{id.toString()});
        CrimeCursorWrapper cursorWrapper = new CrimeCursorWrapper(cursor);
        try{
            if(cursor.getCount() == 0)
                return null;
            cursorWrapper.moveToFirst();
            return cursorWrapper.getCrime();
        }
        finally {
            cursor.close();
        }
    }

    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID +" =?", new String[]{uuidString});
    }
    private CrimeCursorWrapper queryCrimes(String whererClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(CrimeTable.NAME, null, whererClause, whereArgs, null,null,null);
        return new CrimeCursorWrapper(cursor);
    }

    public void deleteCrime(Crime c){
        //ContentValues values = getContentValues(c);
        String[] args = {c.getId().toString()};
        mDatabase.delete(CrimeTable.NAME, CrimeTable.Cols.UUID +" =?", args);
    }
    public void addCrime(Crime c){

        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public File getPhotoFile(Crime crime) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, crime.getPhotoFilename());
    }

    public boolean isEmpty(){
        Cursor cursor = mDatabase.rawQuery("SELECT  * FROM " + CrimeTable.NAME, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt==0;
    }
}
