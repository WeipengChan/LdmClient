package com.ldm.ldmclient.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * base table
 * Created by LDM on 2014/7/9. Email : nightkid-s@163.com
 */
public abstract class BaseTBL<T> {

    public abstract void insert(T t);

    public abstract void delete(T t);

    public abstract int update(T t);

    public abstract ContentValues getContentValue(T t); //operating container

    public abstract String getTableName();

    protected SQLiteDatabase db;

    protected ContentValues cv;

    protected BaseTBL(BaseWritableDbHelper helper) {
        db = helper.getDb();
        cv = new ContentValues();
    }

    public void add(List<T> data) {
        if( data == null || data.size() < 1 ) return;

        db.beginTransaction();
        try {
            for (T t : data) {
                if(update(t) < 1 ) insert(t);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void update(List<T> data) {
        if( data == null || data.size() < 1 ) return;
        try {
            db.beginTransaction();
            for (T t : data) {
                update(t);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void remove(List<T> data) {
        if( data == null || data.size() < 1 ) return;
        try {
            db.beginTransaction();
            for (T t : data) {
                delete(t);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void deleteAll(){
        db.delete(getTableName(), null, null);
    }
}
