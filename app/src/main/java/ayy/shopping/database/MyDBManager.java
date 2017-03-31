package ayy.shopping.database;
/*
  Created by Karl on 24 Mar 2017.
 */

// Reference: The following code is from
//Susan McKeever's Mobile development Class

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBManager {
    // These are the names of the columns the table will contain
    private static final String KEY_VALUE = "Value";
    private static final String KEY_QUANTITY = "Quantity";
    private static final String KEY_DENOMINATION = "Denomination";
    ;

    private static final String DATABASE_NAME = "Database";
    private static final String WALLET_TABLE = "Wallet";
    private static final String CURRENCY_TABLE = "Currency";
    private static final int DATABASE_VERSION = 1;
    // This is the string containing the SQL database create statement
    private static final String DATABASE_CREATE = "create table " +
            WALLET_TABLE + "(" +
            KEY_VALUE + " real not null primary key, " +
            KEY_QUANTITY + "integer not null); " +
            "create table " +
            CURRENCY_TABLE + "(" +
            KEY_DENOMINATION + " text not null primary key, " +
            KEY_VALUE + "real not null, " +
            " FOREIGN KEY (" + KEY_VALUE + ") REFERENCES " + WALLET_TABLE + "(" + KEY_VALUE + "));";

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    // constructor for your class
    public MyDBManager(Context ctx) {
        // Context is a way that Android transfers info about Activities and apps.
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    // This is the helper class that will create the dB if it doesn’t exist and
    //upgrades it if the structure has changed. It needs a constructor, an
    //onCreate() method and an onUpgrade() method

    private static class DatabaseHelper extends SQLiteOpenHelper {
        // constructor for your dB helper class. This code is standard. You’ve set
        //up the parameter values for the constructor already…database name,etc
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // The “Database_create” string below needs to contain the SQL
            //statement needed to create the dB
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {

 /*If you want to change the structure of your database, e.g.
      Add a new column to a table, the code will go head..
      This method only triggers if the database version number has
            increased*/


        }
    } // end of the help class

    // from here on, include whatever methods will be used to access or change data
    //in the database
    //---opens the database--- any activity that uses the dB will need to do this
    public MyDBManager open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database--- any activity that uses the dB will need to do this
    public void close() {
        DBHelper.close();
    }


    //---insert a person into the database---
    public long insertWallet(float val, int qty) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_VALUE, val);
        initialValues.put(KEY_QUANTITY, qty);
        return db.insert(WALLET_TABLE, null, initialValues);
    }

    //---deletes a particular person---
    public boolean deleteWalletAmt(long val) {
        // delete statement. If any rows deleted (i.e. >0), returns true
        return db.delete(WALLET_TABLE, KEY_VALUE +
                "=" + val, null) > 0;
    }

    //---retrieves wallet---
    public Cursor getWallet() throws SQLException {
        Cursor mCursor = db.query(true,
                WALLET_TABLE,
                new String[]{
                        KEY_VALUE,
                        KEY_QUANTITY},
                null,
                null,
                null,
                null,
                null,
                null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates wallet---
    public boolean updateWallet(float val, long qty) {
        ContentValues args = new ContentValues();
        args.put(KEY_QUANTITY, qty);
        return db.update(WALLET_TABLE, args,
                KEY_VALUE + "=" + val, null) > 0;
    }

    // end Reference:
}