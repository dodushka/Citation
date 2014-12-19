package com.pas.citation.citation.database;

import android.util.SparseArray;

import com.pas.citation.citation.util.Logger;

/**
 * Created by Paskal on 18.12.2014.
 */
class MatchHelper {
    private static String LOG_TAG = MatchHelper.class.getSimpleName();
     static SparseArray<String> TABLE_MATCHER = new SparseArray<>();

    static {
        try {
            Class[] classes = DatabaseTables.class.getDeclaredClasses();
            for (Class table : classes) {
                TABLE_MATCHER.append(TABLE_MATCHER.size(), table.getSimpleName());
            }
        } catch (Exception e) {
            Logger.e(LOG_TAG, "Some error at sparse array init", e);
        }
    }
   public static String getTableName(int code){

       return  TABLE_MATCHER.get(code);
   }

}
