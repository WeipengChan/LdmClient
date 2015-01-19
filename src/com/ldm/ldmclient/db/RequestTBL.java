package com.ldm.ldmclient.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import com.ldm.ldmclient.bean.RemoteData;

/**
 * this table restores the url and it's result
 * Created by LDM on 2014/7/7. Email : nightkid-s@163.com
 */
public class RequestTBL extends BaseTBL<RemoteData> {

    public static  final String TABLE_NAME_REQUEST = "request";

    public static final String colUrl = "url";
    public static final String colData = "data";
    public static final String colHashCode = "url_hash_code";

    public RequestTBL(BaseWritableDbHelper helper) {
        super(helper);
    }

    public void insert(RemoteData remoteData) {
        db.insert(TABLE_NAME_REQUEST, null, getContentValue(remoteData));
    }

    @Override
    public void delete(RemoteData remoteData) {

    }

    @Override
    public int update(RemoteData remoteData) {
        int result = db.update(TABLE_NAME_REQUEST, getContentValue(remoteData), colHashCode + " = ?", new String[]{String.valueOf(remoteData.getHashCode())});
        if(result < 1) insert(remoteData);
        return result;
    }

    @Override
    public ContentValues getContentValue(RemoteData remoteData) {
        cv.clear();
        cv.put(colUrl, remoteData.getUrl());
        cv.put(colData, remoteData.getData());
        cv.put(colHashCode, remoteData.getHashCode());
        return cv;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME_REQUEST;
    }

    public static String getDDL(){
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_REQUEST + "(" + colHashCode + " INTEGER PRIMARY KEY , " +
                colUrl + " TEXT, " +
                colData + " TEXT)";
    }

    public void update(String url, String data){
        update(new RemoteData(url, data));
    }

    public String getOfflineData(String url) {
        if(TextUtils.isEmpty(url)) return "";
        url = url.trim();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(String.format("select * from %1$s where %2$s = %3$d limit 1;", TABLE_NAME_REQUEST, colHashCode, url.hashCode()), null);
            if(cursor == null || cursor.getCount() < 1) return "";
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex(colData));
        } finally {
            if(cursor != null && !cursor.isClosed()) cursor.close();
        }
    }
}
