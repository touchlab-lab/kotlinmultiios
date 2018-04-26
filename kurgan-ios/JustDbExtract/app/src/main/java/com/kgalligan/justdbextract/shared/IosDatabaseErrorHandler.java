package com.kgalligan.justdbextract.shared;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

public abstract class IosDatabaseErrorHandler implements DatabaseErrorHandler
{
    @Override
    public void onCorruption(SQLiteDatabase dbObj)
    {

    }

    public abstract void onCorruptionReal(SQLiteDatabase dbObj);

}
