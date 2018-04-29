package co.touchlab.kurgan.architecture.database.sqlite

import objcsrc.*
import co.touchlab.kurgan.architecture.database.*

actual abstract class SQLiteOpenHelper(
        callback:PlatformSQLiteOpenHelperCallback,
        context: AndroidContentContext,
        name: String?,
        version: Int,
        errorHandler: DatabaseErrorHandler?
) {

    val openHelper = OpenHelper(callback, context, name, version, errorHandler)

    actual fun getWritableDatabase(): SQLiteDatabase {
        return SQLiteDatabase(openHelper.getWritableDatabase()!!)
    }

    actual fun getReadableDatabase(): SQLiteDatabase {
        return SQLiteDatabase(openHelper.getReadableDatabase()!!)
    }

    actual open fun close() {
        openHelper.close()
    }

    actual fun getDatabaseName(): String? = openHelper.getDatabaseName()

    actual fun setWriteAheadLoggingEnabled(enabled: Boolean) {
        openHelper.setWriteAheadLoggingEnabledWithBoolean(enabled)
    }

    class OpenHelper(
            val callback:PlatformSQLiteOpenHelperCallback,
            context: AndroidContentContext,
                     name: String?,
                     version: Int,
                     errorHandler: DatabaseErrorHandler?) :
            AndroidDatabaseSqliteSQLiteOpenHelper(
                    context, name, null, version,
                    if (errorHandler == null) {
                        null
                    } else {
                        object : ComKgalliganJustdbextractSharedIosDatabaseErrorHandler() {
                            override fun onCorruptionRealWithAndroidDatabaseSqliteSQLiteDatabase(dbObj: AndroidDatabaseSqliteSQLiteDatabase?): Unit {
                                if(dbObj != null)
                                    errorHandler.onCorruption(SQLiteDatabase(dbObj))
                            }
                        }
                    }
            ){

        override fun onConfigureWithAndroidDatabaseSqliteSQLiteDatabase(db: AndroidDatabaseSqliteSQLiteDatabase?): Unit{
            callback.onConfigure(SQLiteDatabase(db!!))
        }

        override fun onCreateWithAndroidDatabaseSqliteSQLiteDatabase(db: AndroidDatabaseSqliteSQLiteDatabase?): Unit{
            callback.onCreate(SQLiteDatabase(db!!))
        }

        override fun onDowngradeWithAndroidDatabaseSqliteSQLiteDatabase(db: AndroidDatabaseSqliteSQLiteDatabase?, withInt: jint, _withInt: jint): Unit{
            callback.onDowngrade(SQLiteDatabase(db!!), withInt, _withInt)
        }

        override fun onOpenWithAndroidDatabaseSqliteSQLiteDatabase(db: AndroidDatabaseSqliteSQLiteDatabase?): Unit{
            callback.onOpen(SQLiteDatabase(db!!))
        }

        override fun onUpgradeWithAndroidDatabaseSqliteSQLiteDatabase(db: AndroidDatabaseSqliteSQLiteDatabase?, withInt: jint, _withInt: jint): Unit{
            callback.onUpgrade(SQLiteDatabase(db!!), withInt, _withInt)
        }
    }
}