package com.ldm.ldmclient.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LDM on 2014/7/11. Email : nightkid-s@163.com
 */
public abstract class BaseWritableDbHelper extends SQLiteOpenHelper{

    private SQLiteDatabase db;

    public BaseWritableDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        db  = getWritableDatabase();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public abstract void clearAllData();
}
