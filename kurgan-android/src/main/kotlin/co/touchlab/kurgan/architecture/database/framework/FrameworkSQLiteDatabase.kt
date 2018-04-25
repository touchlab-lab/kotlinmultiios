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
import co.touchlab.kurgan.architecture.database.support.SupportSQLiteQuery

class FrameworkSQLiteDatabase(private val mDelegate: SQLiteDatabase) : SupportSQLiteDatabase{

    class TransactionListenerAdapter(val transactionListener: SQLiteTransactionListener) : android.database.sqlite.SQLiteTransactionListener{
        override fun onCommit() {
            transactionListener.onCommit()
        }

        override fun onRollback() {
            transactionListener.onRollback()
        }

        override fun onBegin() {
            transactionListener.onBegin()
        }

    }

    private val CONFLICT_VALUES = arrayOf("", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE ")
    private val EMPTY_STRING_ARRAY = arrayOfNulls<String>(0)

    override fun compileStatement(sql: String): SupportSQLiteStatement {
        return FrameworkSQLiteStatement(mDelegate.compileStatement(sql))
    }

    override fun beginTransaction() {
        mDelegate.beginTransaction()
    }

    override fun beginTransactionNonExclusive() {
        mDelegate.beginTransactionNonExclusive()
    }

    override fun beginTransactionWithListener(transactionListener: SQLiteTransactionListener) {
        mDelegate.beginTransactionWithListener(TransactionListenerAdapter(transactionListener))
    }

    override fun beginTransactionWithListenerNonExclusive(transactionListener: SQLiteTransactionListener) {
        mDelegate.beginTransactionWithListenerNonExclusive(TransactionListenerAdapter(transactionListener))
    }

    override fun endTransaction() {
        mDelegate.endTransaction()
    }

    override fun setTransactionSuccessful() {
        mDelegate.setTransactionSuccessful()
    }

    override fun inTransaction(): Boolean {
        return mDelegate.inTransaction()
    }

    override fun isDbLockedByCurrentThread(): Boolean {
        return mDelegate.isDbLockedByCurrentThread
    }

    override fun yieldIfContendedSafely(): Boolean {
        return mDelegate.yieldIfContendedSafely()
    }

    override fun yieldIfContendedSafely(sleepAfterYieldDelay: Long): Boolean {
        return mDelegate.yieldIfContendedSafely(sleepAfterYieldDelay)
    }

    override fun getVersion(): Int {
        return mDelegate.version
    }

    override fun setVersion(version: Int) {
        mDelegate.version = version
    }

    override fun getMaximumSize(): Long {
        return mDelegate.maximumSize
    }

    override fun setMaximumSize(numBytes: Long): Long {
        return mDelegate.setMaximumSize(numBytes)
    }

    override fun getPageSize(): Long {
        return mDelegate.pageSize
    }

    override fun setPageSize(numBytes: Long) {
        mDelegate.pageSize = numBytes
    }

    override fun query(query: String): Cursor {
        return query(SimpleSQLiteQuery(query))
    }

    override fun query(query: String, bindArgs: Array<Any?>?): Cursor {
        return query(SimpleSQLiteQuery(query, bindArgs))
    }

    override fun query(supportQuery: SupportSQLiteQuery): Cursor {
        val cursor = mDelegate.rawQueryWithFactory({ db, masterQuery, editTable, query ->
            supportQuery.bindTo(FrameworkSQLiteProgram(query))
            SQLiteCursor(masterQuery, editTable, query)
        }, supportQuery.getSql(), EMPTY_STRING_ARRAY, null)
        return AndroidCursor(cursor)
    }

    override fun insert(table: String, conflictAlgorithm: Int, values: ContentValues): Long {
        return mDelegate.insertWithOnConflict(table, null, values.platformValues(),
                conflictAlgorithm)
    }

    override fun delete(table: String, whereClause: String?, whereArgs: Array<Any?>?): Int {
        val query = ("DELETE FROM " + table
                + if (whereClause.isNullOrEmpty()) "" else " WHERE $whereClause")
        val statement = compileStatement(query)
        SimpleSQLiteQuery.bind(statement, whereArgs)
        return statement.executeUpdateDelete()
    }

    override fun update(table: String, conflictAlgorithm: Int,
                        values: ContentValues, whereClause: String?, whereArgs: Array<Any?>?): Int {
        // taken from SQLiteDatabase class.
        if (values.size() == 0) {
            throw IllegalArgumentException("Empty values")
        }
        val sql = StringBuilder(120)
        sql.append("UPDATE ")
        sql.append(CONFLICT_VALUES[conflictAlgorithm])
        sql.append(table)
        sql.append(" SET ")

        // move all bind args to one array
        val setValuesSize = values.size()
        val bindArgsSize = if (whereArgs == null) setValuesSize else setValuesSize + whereArgs.size
        val bindArgs = arrayOfNulls<Any>(bindArgsSize)
        var i = 0
        for (colName in values.keySet()) {
            sql.append(if (i > 0) "," else "")
            sql.append(colName)
            bindArgs[i++] = values.get(colName)
            sql.append("=?")
        }
        if (whereArgs != null) {
            i = setValuesSize
            while (i < bindArgsSize) {
                bindArgs[i] = whereArgs[i - setValuesSize]
                i++
            }
        }
        if (!whereClause.isNullOrEmpty()) {
            sql.append(" WHERE ")
            sql.append(whereClause)
        }
        val stmt = compileStatement(sql.toString())
        SimpleSQLiteQuery.bind(stmt, bindArgs)
        return stmt.executeUpdateDelete()
    }

    override fun execSQL(sql: String, bindArgs: Array<Any?>?) {
        if(bindArgs == null)
            mDelegate.execSQL(sql)
        else
            mDelegate.execSQL(sql, bindArgs)
    }

    override fun isReadOnly(): Boolean {
        return mDelegate.isReadOnly
    }

    override fun isOpen(): Boolean {
        return mDelegate.isOpen
    }

    override fun needUpgrade(newVersion: Int): Boolean {
        return mDelegate.needUpgrade(newVersion)
    }

    override fun getPath(): String {
        return mDelegate.path
    }

    override fun setMaxSqlCacheSize(cacheSize: Int) {
        mDelegate.setMaxSqlCacheSize(cacheSize)
    }

    override fun setForeignKeyConstraintsEnabled(enable: Boolean) {
        mDelegate.setForeignKeyConstraintsEnabled(enable)
    }

    override fun enableWriteAheadLogging(): Boolean {
        return mDelegate.enableWriteAheadLogging()
    }

    override fun disableWriteAheadLogging() {
        mDelegate.disableWriteAheadLogging()
    }

    override fun isWriteAheadLoggingEnabled(): Boolean {
        return mDelegate.isWriteAheadLoggingEnabled
    }

    override fun isDatabaseIntegrityOk(): Boolean {
        return mDelegate.isDatabaseIntegrityOk
    }

    @Throws(IOException::class)
    override fun close() {
        mDelegate.close()
    }
}