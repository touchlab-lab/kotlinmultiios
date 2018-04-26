package co.touchlab.kurgan.architecture.database.sqlite.plain

import co.touchlab.kurgan.architecture.database.Cursor

expect interface CursorFactory
expect class SQLiteDatabase{


    fun beginTransaction():Unit
    fun beginTransactionNonExclusive():Unit
    fun beginTransactionWithListener(transactionListener: SQLiteTransactionListener):Unit
    fun beginTransactionWithListenerNonExclusive(transactionListener: SQLiteTransactionListener):Unit
    fun endTransaction():Unit
    fun setTransactionSuccessful():Unit
    fun inTransaction(): Boolean
    fun isDbLockedByCurrentThread(): Boolean
    fun yieldIfContendedSafely(): Boolean
    fun yieldIfContendedSafely(sleepAfterYieldDelay: Long): Boolean
    fun getVersion():Int
    fun setVersion(value:Int)
    fun getMaximumSize():Long
    fun setMaximumSize(value:Long):Long
    fun getPageSize():Long
    fun setPageSize(value:Long)

    fun insertWithOnConflict(table: String, nullColumnHack:String?, contentValues: ContentValues, conflictAlgorithm: Int): Long

    fun execSQL(sql:String):Unit
    fun execSQL(sql:String, bindArgs:Array<Any?>):Unit

    fun rawQueryWithFactory(cursorFactory: CursorFactory, sql:String, selectionArgs:Array<String>?, editTable: String?):Cursor

    //CursorFactory cursorFactory, String sql, String[] selectionArgs,
    //            String editTable

    fun isReadOnly():Boolean
    fun isOpen():Boolean
    fun needUpgrade(newVersion:Int):Boolean


    /**
     * Source docs tell me this can't be null, even if in memory.
     */
    fun getPath():String
    fun setMaxSqlCacheSize(cacheSize:Int):Unit
    fun setForeignKeyConstraintsEnabled(enable:Boolean):Unit
    fun enableWriteAheadLogging():Boolean
    fun disableWriteAheadLogging():Unit
    fun isWriteAheadLoggingEnabled():Boolean
    fun isDatabaseIntegrityOk():Boolean
    fun close():Unit

    fun compileStatement(sql:String):SQLiteStatement
}

expect interface SQLiteTransactionListener