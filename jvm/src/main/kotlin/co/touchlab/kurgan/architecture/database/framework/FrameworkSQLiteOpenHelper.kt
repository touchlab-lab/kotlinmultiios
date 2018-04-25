package co.touchlab.kurgan.architecture.database.framework

import android.content.Context
import co.touchlab.kurgan.architecture.database.support.SupportSQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import co.touchlab.kurgan.architecture.database.support.SupportSQLiteDatabase
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteOpenHelper

class FrameworkSQLiteOpenHelper: SupportSQLiteOpenHelper{
    private var mDelegate: OpenHelper

    constructor(context: Context, name: String?,
                callback: SupportSQLiteOpenHelper.Callback){
        mDelegate = createDelegate(context, name, callback)
    }

    private fun createDelegate(context: Context, name: String?, callback: SupportSQLiteOpenHelper.Callback): OpenHelper {
        val dbRef = arrayOfNulls<FrameworkSQLiteDatabase>(1)
        return OpenHelper(context, name, dbRef, callback)
    }

    override fun getDatabaseName(): String {
        return mDelegate.databaseName
    }

    override fun setWriteAheadLoggingEnabled(enabled: Boolean) {
        mDelegate.setWriteAheadLoggingEnabled(enabled)
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

    internal class OpenHelper(context: Context, name: String?,
                              val mDbRef: Array<FrameworkSQLiteDatabase?>,
                              val mCallback: SupportSQLiteOpenHelper.Callback) :
            SQLiteOpenHelper(context, name, null, mCallback.version, DatabaseErrorHandler
    {
        val db = mDbRef[0]
        if (db != null) {
            mCallback.onCorruption(db)
        }
    })
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

        fun getWrappedDb(sqLiteDatabase: SQLiteDatabase?): FrameworkSQLiteDatabase {
            var dbRef: FrameworkSQLiteDatabase? = mDbRef[0]
            if (dbRef == null) {
                dbRef = FrameworkSQLiteDatabase(sqLiteDatabase!!)
                mDbRef[0] = dbRef
            }
            return mDbRef[0]!!
        }

        override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
            mCallback.onCreate(getWrappedDb(sqLiteDatabase))
        }

        override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            mCallback.onUpgrade(getWrappedDb(sqLiteDatabase), oldVersion, newVersion)
        }

        override fun onConfigure(db: SQLiteDatabase?) {
            mCallback.onConfigure(getWrappedDb(db))
        }

        override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            mCallback.onDowngrade(getWrappedDb(db), oldVersion, newVersion)
        }

        override fun onOpen(db: SQLiteDatabase?) {
            mCallback.onOpen(getWrappedDb(db))
        }

        @Synchronized
        override fun close() {
            super.close()
            mDbRef[0] = null
        }
    }
}