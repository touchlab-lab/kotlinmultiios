package co.touchlab.kurgan.architecture.database.sqlite

import co.touchlab.kurgan.architecture.database.*
import objcsrc.*

actual class SQLiteDatabase(val db:AndroidDatabaseSqliteSQLiteDatabase){
    actual fun beginTransaction():Unit{
        db.beginTransaction()
    }

    actual fun beginTransactionNonExclusive():Unit{
        db.beginTransactionNonExclusive()
    }

    actual fun beginTransactionWithListener(transactionListener: SQLiteTransactionListener):Unit{
//        db.beginTransactionWithListenerWithAndroidDatabaseSqliteSQLiteTransactionListener()
        TODO()
    }

    actual fun beginTransactionWithListenerNonExclusive(transactionListener: SQLiteTransactionListener):Unit{
//        db.beginTransactionWithListenerNonExclusiveWithAndroidDatabaseSqliteSQLiteTransactionListener()
        TODO()
    }

    actual fun endTransaction():Unit{
        db.endTransaction()
    }

    actual fun setTransactionSuccessful():Unit{
        db.setTransactionSuccessful()
    }

    actual fun inTransaction(): Boolean = db.inTransaction()
    actual fun isDbLockedByCurrentThread(): Boolean = db.isDbLockedByCurrentThread()
    actual fun yieldIfContendedSafely(): Boolean = db.yieldIfContendedSafely()
    actual fun yieldIfContendedSafely(sleepAfterYieldDelay: Long): Boolean = db.yieldIfContendedSafelyWithLong(sleepAfterYieldDelay)
    actual fun getVersion():Int = db.getVersion()
    actual fun setVersion(value:Int){
        db.setVersionWithInt(value)
    }
    actual fun getMaximumSize():Long = db.getMaximumSize()
    actual fun setMaximumSize(value:Long):Long = db.setMaximumSizeWithLong(value)
    actual fun getPageSize():Long = db.getPageSize()
    actual fun setPageSize(value:Long){
        db.setPageSizeWithLong(value)
    }

    actual fun insertWithOnConflict(table: String, nullColumnHack:String?, contentValues: ContentValues, conflictAlgorithm: Int): Long{
        return db.insertWithOnConflictWithNSString(table, nullColumnHack, contentValues.platformValues(), conflictAlgorithm)
    }

    actual fun execSQL(sql:String):Unit{
        db.execSQLWithNSString(sql)
    }

    actual fun execSQL(sql:String, bindArgs:Array<Any?>):Unit{
//        db.execSQLWithNSString(sql, )
        TODO()
    }

    actual fun rawQueryWithFactory(cursorFactory: CursorFactory, sql:String, selectionArgs:Array<String?>?, editTable: String?): Cursor{
        TODO()
    }

    //CursorFactory cursorFactory, String sql, String[] selectionArgs,
    //            String editTable

    actual fun isReadOnly():Boolean = db.isReadOnly()
    actual fun isOpen():Boolean = db.isOpen()
    actual fun needUpgrade(newVersion:Int):Boolean = db.needUpgradeWithInt(newVersion)


    /**
     * Source docs tell me this can't be null, even if in memory.
     */
    actual fun getPath():String = db.getPath()!!
    actual fun setMaxSqlCacheSize(cacheSize:Int):Unit{
        db.setMaxSqlCacheSizeWithInt(cacheSize)
    }
    actual fun setForeignKeyConstraintsEnabled(enable:Boolean):Unit{
        db.setForeignKeyConstraintsEnabledWithBoolean(enable)
    }

    actual fun enableWriteAheadLogging():Boolean = db.enableWriteAheadLogging()
    actual fun disableWriteAheadLogging():Unit{
        db.disableWriteAheadLogging()
    }
    actual fun isWriteAheadLoggingEnabled():Boolean = db.isWriteAheadLoggingEnabled()
    actual fun isDatabaseIntegrityOk():Boolean = db.isDatabaseIntegrityOk()
    actual fun close():Unit{
        db.close()
    }

    actual fun compileStatement(sql:String): SQLiteStatement{
        TODO()
//        = db.compileStatementWithNSString(sql)
    }
}