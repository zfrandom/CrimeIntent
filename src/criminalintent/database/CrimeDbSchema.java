package com.example.android.criminalintent.database;

import static java.lang.reflect.Modifier.STATIC;

/**
 * Created by zifeifeng on 4/28/17.
 */

public class CrimeDbSchema {
    public static final class CrimeTable{
        public static final String NAME = "crimes";
        public static final class Cols{
            public static final String UUID = "UUID";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT= "suspect";
        }
    }
}
