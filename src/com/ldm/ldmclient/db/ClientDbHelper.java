package com.ldm.ldmclient.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ldm.ldmclient.app.AppManager;

/**
 * client database
 * Created by LDM on 2014/12/23. Email : nightkid-s@163.com
 */
public class ClientDbHelper extends SQLiteOpenHelper{

    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "database";
    private static ClientDbHelper instance;
    //table
    private RequestTBL requestTBL;


    public static ClientDbHelper getInstance(){
        if(instance == null){
            synchronized (ClientDbHelper.class){
                if(instance == null) instance = new ClientDbHelper(AppManager.getInstance());
            }
        }
        return instance;
    }

    private ClientDbHelper(Context context) {
        this(context, null);
    }

    private ClientDbHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, VERSION);
        requestTBL = new RequestTBL(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RequestTBL.getDDL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    @Override
    public synchronized void close() {
        instance = null;
        super.close();
    }

    public void clearAllData() {
        requestTBL.deleteAll();
    }

    public String getOfflineData(String url){
        return requestTBL.getOfflineData(url);
    }

    public void updateOfflineData(String url, String data){
        requestTBL.update(url, data);
    }
}
