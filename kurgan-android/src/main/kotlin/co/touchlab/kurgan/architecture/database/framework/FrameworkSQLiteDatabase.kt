package co.touchlab.kurgan.architecture.database.framework


import java.io.IOException

import co.touchlab.kurgan.architecture.database.support.SupportSQLiteDatabase
import co.touchlab.kurgan.architecture.database.support.SimpleSQLiteQuery
import co.touchlab.kurgan.architecture.database.support.SupportSQLiteStatement

import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteDatabase

import co.touchlab.kurgan.architecture.database.AndroidCursor
import co.touchlab.kurgan.architecture.database.ContentValues
import co.touchlab.kurgan.architecture.database.Cursor
import co.touchlab.kurgan.architecture.database.platformValues
import co.touchlab.kurgan.architecture.database.sqlite.SQLiteTransactionListener
import co.touchlab.kurgan.architecture.database.support.*

class FrameworkSQLiteDatabase(private val mDelegate: SQLiteDatabase) : SupportSQLiteDatabase{

    class TransactionListenerAdapter(val transactionListener: SQLiteTransactionListener) : android.database.sqlite.SQLiteTransactionListener{
        override fun onCommit() = transactionListener.onCommit()
        override fun onRollback() = transactionListener.onRollback()
        override fun onBegin() = transactionListener.onBegin()
    }

    override fun compileStatement(sql: String): SupportSQLiteStatement = FrameworkSQLiteStatement(mDelegate.compileStatement(sql))
    override fun beginTransaction() = mDelegate.beginTransaction()
    override fun beginTransactionNonExclusive() = mDelegate.beginTransactionNonExclusive()
    override fun beginTransactionWithListener(transactionListener: SQLiteTransactionListener) = mDelegate.beginTransactionWithListener(TransactionListenerAdapter(transactionListener))
    override fun beginTransactionWithListenerNonExclusive(transactionListener: SQLiteTransactionListener) = mDelegate.beginTransactionWithListenerNonExclusive(TransactionListenerAdapter(transactionListener))
    override fun endTransaction() = mDelegate.endTransaction()
    override fun setTransactionSuccessful() = mDelegate.setTransactionSuccessful()
    override fun inTransaction(): Boolean = mDelegate.inTransaction()
    override fun isDbLockedByCurrentThread(): Boolean = mDelegate.isDbLockedByCurrentThread
    override fun yieldIfContendedSafely(): Boolean = mDelegate.yieldIfContendedSafely()
    override fun yieldIfContendedSafely(sleepAfterYieldDelay: Long): Boolean = mDelegate.yieldIfContendedSafely(sleepAfterYieldDelay)
    override val version:Int = mDelegate.version
    override var maximumSize :Long
    get() = mDelegate.maximumSize
    set(value) {
        mDelegate.setMaximumSize(value)
    }
    override var pageSize = mDelegate.pageSize

    override fun query(query: String, bindArgs: Array<Any?>?): Cursor = query(SimpleSQLiteQuery(query, bindArgs))
    override fun query(supportQuery: SupportSQLiteQuery): Cursor {
        val cursor = mDelegate.rawQueryWithFactory({ db, masterQuery, editTable, query ->
            supportQuery.bindTo(FrameworkSQLiteProgram(query))
            SQLiteCursor(masterQuery, editTable, query)
        }, supportQuery.getSql(), arrayOfNulls<String>(0), null)
        return AndroidCursor(cursor)
    }

    override fun insert(table: String, conflictAlgorithm: Int, values: ContentValues): Long =
            mDelegate.insertWithOnConflict(table, null, values.platformValues(), conflictAlgorithm)

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

    override fun isReadOnly(): Boolean = mDelegate.isReadOnly
    override fun isOpen(): Boolean = mDelegate.isOpen
    override fun needUpgrade(newVersion: Int): Boolean = mDelegate.needUpgrade(newVersion)
    override fun getPath(): String = mDelegate.path
    override fun setMaxSqlCacheSize(cacheSize: Int) = mDelegate.setMaxSqlCacheSize(cacheSize)
    override fun setForeignKeyConstraintsEnabled(enable: Boolean) = mDelegate.setForeignKeyConstraintsEnabled(enable)
    override fun enableWriteAheadLogging(): Boolean = mDelegate.enableWriteAheadLogging()
    override fun disableWriteAheadLogging() = mDelegate.disableWriteAheadLogging()
    override fun isWriteAheadLoggingEnabled(): Boolean = mDelegate.isWriteAheadLoggingEnabled
    override fun isDatabaseIntegrityOk(): Boolean = mDelegate.isDatabaseIntegrityOk

    @Throws(IOException::class)
    override fun close() = mDelegate.close()
}