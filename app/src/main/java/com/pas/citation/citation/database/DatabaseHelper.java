package com.pas.citation.citation.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

class DatabaseHelper extends SQLiteOpenHelper {
    public static String LOG_TAG=DatabaseHelper.class.getSimpleName();
    private final Context context;
    private SQLiteDatabase database;
    // database path
    private static String DATABASE_PATH;
        DatabaseHelper(Context context) {
            super(context, DatabaseTables.DATABASE_NAME, null, DatabaseTables.DATABASE_VERSION);
            this.context = context;
            DATABASE_PATH = context.getFilesDir().getParentFile().getPath()
                    + "/databases/";
        }
    /**
     * Creates a empty database on the system and rewrites it with our own
     * database.
     * */
    public void create() throws IOException {
        boolean check = checkDataBase();

        SQLiteDatabase db_Read = null;

        // Creates empty database default system path
        db_Read = this.getWritableDatabase();
        db_Read.close();
        try {
            if (!check) {
                copyDataBase();
            }
        } catch (IOException e) {
            throw new Error("Error copying database");
        }
    }
    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DATABASE_PATH + DatabaseTables.DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e(LOG_TAG,"Error at checkDataBase()",e);
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }
    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     *
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DatabaseTables.DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DATABASE_PATH + DatabaseTables.DATABASE_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
    public void open() throws SQLException {
        String myPath = DATABASE_PATH + DatabaseTables.DATABASE_NAME;
        database = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    /** close the database */
    @Override
    public synchronized void close() {
        if (database != null)
            database.close();
        super.close();
    }
        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
        }
    }