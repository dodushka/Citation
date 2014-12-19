package com.pas.citation.citation.database;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.pas.citation.citation.BuildConfig;
import com.pas.citation.citation.util.Logger;



public class CitationProvider extends ContentProvider {
    private DatabaseHelper dbHelper;
    private static String LOG_TAG = CitationProvider.class.toString();
    private SQLiteDatabase db;
    private static final String AUTHORITY = BuildConfig.APPLICATION_ID;


    public static final String URL = "content://" + AUTHORITY;
    public static final Uri CONTENT_URI = Uri.parse(URL);
    public static final String COMMON_TABLE_PATH="Citation_with_author";

    private static HashMap<String, String> CITATION_PROJECTION_MAP;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        for (int i = 0; i < MatchHelper.TABLE_MATCHER.size(); i++) {
            uriMatcher.addURI(AUTHORITY, MatchHelper.TABLE_MATCHER.get(i).toLowerCase(), MatchHelper.TABLE_MATCHER.keyAt(i));
        }
        uriMatcher.addURI(AUTHORITY,COMMON_TABLE_PATH,MatchHelper.TABLE_MATCHER.size()+1);
    }

    @Override
    public boolean onCreate() {

        dbHelper = new DatabaseHelper(getContext());
        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        try {
            dbHelper.create();
            //TODO
            dbHelper.open();
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            Logger.e(LOG_TAG, "Problem with init provider", e);
        }

        return (db != null);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /**
         * Add a new  record
         */
        long rowID = db.insert(getTableName(uri), "", values);

        /**
         * If record isn't added successfully
         */
        if (rowID < 0) {
            throw new SQLException("Failed to add a record into " + uri);
        }
        Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
        getContext().getContentResolver().notifyChange(_uri, null);
        return _uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor c;
        if(uriMatcher.match(uri)==MatchHelper.TABLE_MATCHER.size()+1){
            c=db.rawQuery("SELECT citates._id, citates.author_id , citates.citation ,authors.author FROM citates,authors WHERE citates.author_id=authors._id",null);
        }
        else {
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            String table = MatchHelper.getTableName(uriMatcher.match(uri));
            if (table == null) {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
            qb.setTables(table);


             c = qb.query(db, projection, selection, selectionArgs,
                    null, null, sortOrder);
        }
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = db.delete(getTableName(uri), selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int count = db.update(getTableName(uri), values,
                selection, selectionArgs);


        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.item/vnd.pas." + getTableName(uri).toLowerCase();
    }

    private static String getTableName(Uri uri) {
        String table = MatchHelper.getTableName(uriMatcher.match(uri));
        if (table == null) {
            throw new TableNotFoundException("Unknown URI " + uri);
        }
        return table;
    }
}