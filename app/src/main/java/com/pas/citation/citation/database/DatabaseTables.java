package com.pas.citation.citation.database;

import android.provider.BaseColumns;

/**
 * Created by Paskal on 18.12.2014.
 */
public class DatabaseTables {
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "Citation";



    public static class Authors implements BaseColumns {
        public final static String IMAGE_URL = "image_url";
        public final static String AUTHOR = "author";
    }

    public static class Citation implements BaseColumns {
        public final static String AUTHOR_ID = "author_id";
        public final static String TEXT = "citation";
    }

}
