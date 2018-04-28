package co.touchlab.kurgan.architecture.database.framework

import co.touchlab.kurgan.architecture.database.support.SupportSQLiteOpenHelper
import co.touchlab.kurgan.architecture.database.support.SupportSQLiteDatabase
import kotlinx.cinterop.*
import objcsrc.*
import co.touchlab.kurgan.j2objc.*

class FrameworkSQLiteOpenHelper: SupportSQLiteOpenHelper{
    private var mDelegate: OpenHelper

    constructor(context: AndroidContentContext, name: String?,
                callback: SupportSQLiteOpenHelper.Callback){
        mDelegate = createDelegate(context, name, callback)
    }

    private fun createDelegate(context: AndroidContentContext, name: String?, callback: SupportSQLiteOpenHelper.Callback): OpenHelper {
        val dbRef = arrayOfNulls<FrameworkSQLiteDatabase>(1)
        return OpenHelper(context, name, dbRef, callback)
    }

    override fun getDatabaseName(): String {
        println("compilings2!")
        return mDelegate.getDatabaseName()!!
    }

    override fun setWriteAheadLoggingEnabled(enabled: Boolean) {
        mDelegate.setWriteAheadLoggingEnabledWithBoolean(enabled)
    }

    override fun getWritableDatabase(): SupportSQLiteDatabase {
        return mDelegate.writableSupportDatabase
    }

    override fun getReadableDatabase(): SupportSQLiteDatabase {
        return mDelegate.readableSupportDatabase
    }

    override fun close() {
        mDelegate.close()
    }

    class DatabaseErrorHandler(val mDbRef: Array<FrameworkSQLiteDatabase?>, val mCallback: SupportSQLiteOpenHelper.Callback):ComKgalliganJustdbextractSharedIosDatabaseErrorHandler(){
        override fun onCorruptionWithAndroidDatabaseSqliteSQLiteDatabase(dbObj: AndroidDatabaseSqliteSQLiteDatabase?){
            val db = mDbRef[0]
            if (db != null) {
                mCallback.onCorruption(db)
            }
        }
    }
    internal class OpenHelper(context: AndroidContentContext, name: String?,
                              val mDbRef: Array<FrameworkSQLiteDatabase?>,
                              val mCallback: SupportSQLiteOpenHelper.Callback) :
            AndroidDatabaseSqliteSQLiteOpenHelper(context, name, null, mCallback.version, DatabaseErrorHandler(mDbRef, mCallback))
    {

        val writableSupportDatabase: SupportSQLiteDatabase
            get() {
                val db = super.getWritableDatabase()
                return getWrappedDb(db)
            }

        val readableSupportDatabase: SupportSQLiteDatabase
            get() {
                val db = super.getReadableDatabase()
                return getWrappedDb(db)
            }

        fun getWrappedDb(sqLiteDatabase: AndroidDatabaseSqliteSQLiteDatabase?): FrameworkSQLiteDatabase {
            var dbRef: FrameworkSQLiteDatabase? = mDbRef[0]
            if (dbRef == null) {
                dbRef = FrameworkSQLiteDatabase(sqLiteDatabase!!)
                mDbRef[0] = dbRef
            }
            return mDbRef[0]!!
        }

        override fun onCreateWithAndroidDatabaseSqliteSQLiteDatabase(db: AndroidDatabaseSqliteSQLiteDatabase?) {
            mCallback.onCreate(getWrappedDb(db!!))
        }

        override fun onUpgradeWithAndroidDatabaseSqliteSQLiteDatabase(db: AndroidDatabaseSqliteSQLiteDatabase?, withInt: Int, _withInt: Int) {
            mCallback.onUpgrade(getWrappedDb(db!!), withInt, _withInt)
        }

        override fun onConfigureWithAndroidDatabaseSqliteSQLiteDatabase(db: AndroidDatabaseSqliteSQLiteDatabase?) {
            mCallback.onConfigure(getWrappedDb(db))
        }

        override fun onDowngradeWithAndroidDatabaseSqliteSQLiteDatabase(db: AndroidDatabaseSqliteSQLiteDatabase?, withInt: Int, _withInt: Int) {
            mCallback.onDowngrade(getWrappedDb(db), withInt, _withInt)
        }

        override fun onOpenWithAndroidDatabaseSqliteSQLiteDatabase(db: AndroidDatabaseSqliteSQLiteDatabase?) {
            mCallback.onOpen(getWrappedDb(db))
        }

        @Synchronized
        override fun close() {
            super.close()
            mDbRef[0] = null
        }
    }
}