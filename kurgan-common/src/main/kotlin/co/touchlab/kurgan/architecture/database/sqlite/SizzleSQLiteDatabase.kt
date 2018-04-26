package co.touchlab.kurgan.architecture.database.sqlite

import co.touchlab.kurgan.architecture.database.Cursor
import co.touchlab.kurgan.architecture.database.sqlite.plain.ContentValues
import co.touchlab.kurgan.architecture.database.sqlite.plain.SQLiteCursor
import co.touchlab.kurgan.architecture.database.sqlite.plain.SQLiteDatabase
import co.touchlab.kurgan.architecture.database.sqlite.plain.SQLiteTransactionListener
import co.touchlab.kurgan.architecture.database.support.*

class SizzleSQLiteDatabase(private val mDelegate: SQLiteDatabase): SupportSQLiteDatabase{


    override fun compileStatement(sql: String): SupportSQLiteStatement = SizzleSQLiteStatement(mDelegate.compileStatement(sql))
    override fun beginTransaction() = mDelegate.beginTransaction()
    override fun beginTransactionNonExclusive() = mDelegate.beginTransactionNonExclusive()
    override fun beginTransactionWithListener(transactionListener: SQLiteTransactionListener) = mDelegate.beginTransactionWithListener(transactionListener)
    override fun beginTransactionWithListenerNonExclusive(transactionListener: SQLiteTransactionListener) = mDelegate.beginTransactionWithListenerNonExclusive(transactionListener)
    override fun endTransaction() = mDelegate.endTransaction()
    override fun setTransactionSuccessful() = mDelegate.setTransactionSuccessful()
    override fun inTransaction(): Boolean = mDelegate.inTransaction()
    override fun isDbLockedByCurrentThread(): Boolean = mDelegate.isDbLockedByCurrentThread()
    override fun yieldIfContendedSafely(): Boolean = mDelegate.yieldIfContendedSafely()
    override fun yieldIfContendedSafely(sleepAfterYieldDelay: Long): Boolean = mDelegate.yieldIfContendedSafely(sleepAfterYieldDelay)
    override val version:Int = mDelegate.getVersion()
    override var maximumSize :Long = mDelegate.getMaximumSize()
    override var pageSize:Long = mDelegate.getPageSize()

    override fun query(query: String, bindArgs: Array<Any?>?): Cursor = query(SimpleSQLiteQuery(query, bindArgs))
    override fun query(supportQuery: SupportSQLiteQuery): Cursor {
        val cursor = mDelegate.rawQueryWithFactory({ db, masterQuery, editTable, query ->
            supportQuery.bindTo(FrameworkSQLiteProgram(query))
            SQLiteCursor(masterQuery, editTable, query)
        }, supportQuery.getSql(), arrayOfNulls<String>(0), null)
        return cursor
    }

    override fun insert(table: String, conflictAlgorithm: Int, values: ContentValues): Long =
            mDelegate.insertWithOnConflict(table, null, values, conflictAlgorithm)

    override fun delete(table: String, whereClause: String?, whereArgs: Array<Any?>?): Int =
            execDeleteStatement(this, table, whereClause, whereArgs)

    override fun update(table: String, conflictAlgorithm: Int,
                        values: ContentValues, whereClause: String?, whereArgs: Array<Any?>?): Int =
            execUpdateStatement(this, table, conflictAlgorithm, values, whereClause, whereArgs)

    override fun execSQL(sql: String, bindArgs: Array<Any?>?) {
        if(bindArgs == null)
            mDelegate.execSQL(sql)
        else
            mDelegate.execSQL(sql, bindArgs)
    }

    override fun isReadOnly(): Boolean = mDelegate.isReadOnly()
    override fun isOpen(): Boolean = mDelegate.isOpen()
    override fun needUpgrade(newVersion: Int): Boolean = mDelegate.needUpgrade(newVersion)
    override fun getPath(): String = mDelegate.getPath()
    override fun setMaxSqlCacheSize(cacheSize: Int) = mDelegate.setMaxSqlCacheSize(cacheSize)
    override fun setForeignKeyConstraintsEnabled(enable: Boolean) = mDelegate.setForeignKeyConstraintsEnabled(enable)
    override fun enableWriteAheadLogging(): Boolean = mDelegate.enableWriteAheadLogging()
    override fun disableWriteAheadLogging() = mDelegate.disableWriteAheadLogging()
    override fun isWriteAheadLoggingEnabled(): Boolean = mDelegate.isWriteAheadLoggingEnabled()
    override fun isDatabaseIntegrityOk(): Boolean = mDelegate.isDatabaseIntegrityOk()
    override fun close() = mDelegate.close()
}