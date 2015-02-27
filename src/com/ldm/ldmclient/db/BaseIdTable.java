package com.ldm.ldmclient.db;

import android.database.sqlite.SQLiteDatabase;
import com.ldm.ldmclient.bean.Id;

/**
 * Created by LDM on 2015/2/7. Email : nightkid-s@163.com
 */
public abstract class BaseIdTable<T extends Id> extends BaseTBL<T>{

    protected abstract String getIdColName();

    public BaseIdTable(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public void insert(T t) {
        db.insert(getTableName(), null, getContentValue(t));
    }

    @Override
    public void delete(T t) {
        db.delete(getTableName(), String.format(" %1s = %2$d", getIdColName(), t.getId()), null);
    }

    @Override
    public int update(T t) {
        return db.update(getTableName(), getContentValue(t), String.format(" %1s = %2$d", getIdColName(), t.getId()), null);
    }

}
