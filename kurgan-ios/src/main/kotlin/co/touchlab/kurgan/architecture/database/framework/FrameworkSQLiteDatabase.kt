package co.touchlab.kurgan.architecture.database.framework

import co.touchlab.kurgan.architecture.database.ContentValues
import co.touchlab.kurgan.architecture.database.Cursor
import co.touchlab.kurgan.architecture.database.platformValues
import co.touchlab.kurgan.architecture.database.IosCursor
import co.touchlab.kurgan.architecture.database.sqlite.SQLiteTransactionListener
import co.touchlab.kurgan.architecture.database.support.*

import kotlinx.cinterop.*
import objcsrc.*
import co.touchlab.kurgan.j2objc.*

class FrameworkSQLiteDatabase(private val mDelegate: AndroidDatabaseSqliteSQLiteDatabase) : SupportSQLiteDatabase{

    class TransactionListenerAdapter(val transactionListener: SQLiteTransactionListener) : ComKgalliganJustdbextractSharedIosSQLiteTransactionListener(){
        override fun onCommit() = transactionListener.onCommit()
        override fun onRollback() = transactionListener.onRollback()
        override fun onBegin() = transactionListener.onBegin()
    }

    override fun compileStatement(sql: String): SupportSQLiteStatement = FrameworkSQLiteStatement(mDelegate.compileStatementWithNSString(sql)!!)
    override fun beginTransaction() = mDelegate.beginTransaction()
    override fun beginTransactionNonExclusive() = mDelegate.beginTransactionNonExclusive()
    override fun beginTransactionWithListener(transactionListener: SQLiteTransactionListener) = mDelegate.beginTransactionWithListenerWithAndroidDatabaseSqliteSQLiteTransactionListener(TransactionListenerAdapter(transactionListener))
    override fun beginTransactionWithListenerNonExclusive(transactionListener: SQLiteTransactionListener) = mDelegate.beginTransactionWithListenerNonExclusiveWithAndroidDatabaseSqliteSQLiteTransactionListener(TransactionListenerAdapter(transactionListener))
    override fun endTransaction() = mDelegate.endTransaction()
    override fun setTransactionSuccessful() = mDelegate.setTransactionSuccessful()
    override fun inTransaction(): Boolean = mDelegate.inTransaction()
    override fun isDbLockedByCurrentThread(): Boolean = mDelegate.isDbLockedByCurrentThread()
    override fun yieldIfContendedSafely(): Boolean = mDelegate.yieldIfContendedSafely()
    override fun yieldIfContendedSafely(sleepAfterYieldDelay: Long): Boolean = mDelegate.yieldIfContendedSafelyWithLong(sleepAfterYieldDelay)

    override var version: Int
        get() = mDelegate.getVersion()
        set(value) {
            mDelegate.setVersionWithInt(value)
        }

    override var maximumSize: Long
        get() = mDelegate.getMaximumSize()
        set(value) {
            mDelegate.setMaximumSizeWithLong(value)
        }

    override var pageSize: Long
        get() = mDelegate.getPageSize()
        set(value) {
            mDelegate.setPageSizeWithLong(value)
        }

    override fun query(query: String, bindArgs: Array<Any?>?): Cursor = query(SimpleSQLiteQuery(query, bindArgs))

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
                , supportQuery.getSql(), stringArrayAsIos(arrayOfNulls<String>(0)), null)
        return IosCursor(cursor!!)
    }

    override fun insert(table: String, conflictAlgorithm: Int, values: ContentValues): Long =
            mDelegate.insertWithOnConflictWithNSString(table, null, values.platformValues(), conflictAlgorithm)

    override fun delete(table: String, whereClause: String?, whereArgs: Array<Any?>?): Int  =
            execDeleteStatement(this, table, whereClause, whereArgs)

    override fun update(table: String, conflictAlgorithm: Int,
                        values: ContentValues, whereClause: String?, whereArgs: Array<Any?>?): Int =
            execUpdateStatement(this, table, conflictAlgorithm, values, whereClause, whereArgs)

    override fun execSQL(sql: String, bindArgs: Array<Any?>?) {
        if(bindArgs == null)
            mDelegate.execSQLWithNSString(sql)
        else
            throw UnsupportedOperationException("Figure out types")
    }

    override fun isReadOnly(): Boolean = mDelegate.isReadOnly()
    override fun isOpen(): Boolean = mDelegate.isOpen()
    override fun needUpgrade(newVersion: Int): Boolean = mDelegate.needUpgradeWithInt(newVersion)
    override fun getPath(): String = mDelegate.getPath()!!
    override fun setMaxSqlCacheSize(cacheSize: Int) = mDelegate.setMaxSqlCacheSizeWithInt(cacheSize)
    override fun setForeignKeyConstraintsEnabled(enable: Boolean) = mDelegate.setForeignKeyConstraintsEnabledWithBoolean(enable)
    override fun enableWriteAheadLogging(): Boolean = mDelegate.enableWriteAheadLogging()
    override fun disableWriteAheadLogging() = mDelegate.disableWriteAheadLogging()
    override fun isWriteAheadLoggingEnabled(): Boolean = mDelegate.isWriteAheadLoggingEnabled()
    override fun isDatabaseIntegrityOk(): Boolean = mDelegate.isDatabaseIntegrityOk()
    override fun close() = mDelegate.close()
}