package com.zdran.criminalintent.database;

/**
 * Create by Ranzd on 2020-04-26 19:22
 *
 * @author cm.zdran@foxmail.com
 */
public class CrimeDbSchema {

    public static final class CrimeTable {
        public static final String NAME = "crimes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";

        }
    }

}
