package com.kgalligan.justdbextract.shared;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

public abstract class IosCursorFactory implements SQLiteDatabase.CursorFactory
{
    @Override
    public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query)
    {
        return newCursorReal(db, masterQuery, editTable, query);
    }

    public abstract Cursor newCursorReal(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query);
}
