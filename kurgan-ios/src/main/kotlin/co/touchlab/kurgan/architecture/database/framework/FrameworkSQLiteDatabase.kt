package co.touchlab.kurgan.architecture.database.framework

import co.touchlab.kurgan.architecture.database.support.SupportSQLiteDatabase
import co.touchlab.kurgan.architecture.database.support.SimpleSQLiteQuery
import co.touchlab.kurgan.architecture.database.support.SupportSQLiteStatement

import co.touchlab.kurgan.architecture.database.ContentValues
import co.touchlab.kurgan.architecture.database.Cursor
import co.touchlab.kurgan.architecture.database.platformValues
import co.touchlab.kurgan.architecture.database.IosCursor
import co.touchlab.kurgan.architecture.database.sqlite.SQLiteTransactionListener
import co.touchlab.kurgan.architecture.database.support.SupportSQLiteQuery

import kotlinx.cinterop.*
import objcsrc.*
import co.touchlab.kurgan.j2objc.*

class FrameworkSQLiteDatabase(private val mDelegate: AndroidDatabaseSqliteSQLiteDatabase) : SupportSQLiteDatabase{

    class TransactionListenerAdapter(val transactionListener: SQLiteTransactionListener) : ComKgalliganJustdbextractSharedIosSQLiteTransactionListener(){
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
        return FrameworkSQLiteStatement(mDelegate.compileStatementWithNSString(sql)!!)
    }

    override fun beginTransaction() {
        mDelegate.beginTransaction()
    }

    override fun beginTransactionNonExclusive() {
        mDelegate.beginTransactionNonExclusive()
    }

    override fun beginTransactionWithListener(transactionListener: SQLiteTransactionListener) {
        mDelegate.beginTransactionWithListenerWithAndroidDatabaseSqliteSQLiteTransactionListener(TransactionListenerAdapter(transactionListener))
    }

    override fun beginTransactionWithListenerNonExclusive(transactionListener: SQLiteTransactionListener) {
        mDelegate.beginTransactionWithListenerNonExclusiveWithAndroidDatabaseSqliteSQLiteTransactionListener(TransactionListenerAdapter(transactionListener))
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
        return mDelegate.isDbLockedByCurrentThread()
    }

    override fun yieldIfContendedSafely(): Boolean {
        return mDelegate.yieldIfContendedSafely()
    }

    override fun yieldIfContendedSafely(sleepAfterYieldDelay: Long): Boolean {
        return mDelegate.yieldIfContendedSafelyWithLong(sleepAfterYieldDelay)
    }

    override fun getVersion(): Int {
        return mDelegate.getVersion()
    }

    override fun setVersion(version: Int) {
        mDelegate.setVersionWithInt(version)
    }

    override fun getMaximumSize(): Long {
        return mDelegate.getMaximumSize()
    }

    override fun setMaximumSize(numBytes: Long): Long {
        return mDelegate.setMaximumSizeWithLong(numBytes)
    }

    override fun getPageSize(): Long {
        return mDelegate.getPageSize()
    }

    override fun setPageSize(numBytes: Long) {
        mDelegate.setPageSizeWithLong(numBytes)
    }

    override fun query(query: String): Cursor {
        return query(SimpleSQLiteQuery(query))
    }

    override fun query(query: String, bindArgs: Array<Any?>?): Cursor {
        return query(SimpleSQLiteQuery(query, bindArgs))
    }

    class CursorFactory(val supportQuery: SupportSQLiteQuery):ComKgalliganJustdbextractSharedIosCursorFactory(){
        override fun newCursorRealWithAndroidDatabaseSqliteSQLiteDatabase(db: AndroidDatabaseSqliteSQLiteDatabase?, withAndroidDatabaseSqliteSQLiteCursorDriver: AndroidDatabaseSqliteSQLiteCursorDriverProtocol?, withNSString: String?, withAndroidDatabaseSqliteSQLiteQuery: AndroidDatabaseSqliteSQLiteQuery?): AndroidDatabaseCursorProtocol?
        {
            val query = withAndroidDatabaseSqliteSQLiteQuery!!
            supportQuery.bindTo(FrameworkSQLiteProgram(query))
            return AndroidDatabaseSqliteSQLiteCursor(withAndroidDatabaseSqliteSQLiteCursorDriver, withNSString, query)
        }
    }

    override fun query(supportQuery: SupportSQLiteQuery): Cursor {
        val cursor = mDelegate.rawQueryWithFactoryWithAndroidDatabaseSqliteSQLiteDatabase_CursorFactory(
                CursorFactory(supportQuery)
                , supportQuery.getSql(), stringArrayAsIos(EMPTY_STRING_ARRAY), null)
        return IosCursor(cursor!!)
    }

    override fun insert(table: String, conflictAlgorithm: Int, values: ContentValues): Long {
        return mDelegate.insertWithOnConflictWithNSString(table, null, values.platformValues(),
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
            mDelegate.execSQLWithNSString(sql)
        else

            throw UnsupportedOperationException("Figure out types")
    }

    override fun isReadOnly(): Boolean {
        return mDelegate.isReadOnly()
    }

    override fun isOpen(): Boolean {
        return mDelegate.isOpen()
    }

    override fun needUpgrade(newVersion: Int): Boolean {
        return mDelegate.needUpgradeWithInt(newVersion)
    }

    override fun getPath(): String {
        return mDelegate.getPath()!!
    }

    override fun setMaxSqlCacheSize(cacheSize: Int) {
        mDelegate.setMaxSqlCacheSizeWithInt(cacheSize)
    }

    override fun setForeignKeyConstraintsEnabled(enable: Boolean) {
        mDelegate.setForeignKeyConstraintsEnabledWithBoolean(enable)
    }

    override fun enableWriteAheadLogging(): Boolean {
        return mDelegate.enableWriteAheadLogging()
    }

    override fun disableWriteAheadLogging() {
        mDelegate.disableWriteAheadLogging()
    }

    override fun isWriteAheadLoggingEnabled(): Boolean {
        return mDelegate.isWriteAheadLoggingEnabled()
    }

    override fun isDatabaseIntegrityOk(): Boolean {
        return mDelegate.isDatabaseIntegrityOk()
    }

    override fun close() {
        mDelegate.close()
    }
}