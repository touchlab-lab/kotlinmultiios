package co.touchlab.kurgan.architecture.database.sqlite

import objcsrc.*
import co.touchlab.kurgan.architecture.database.*

var application:AndroidAppApplication?=null

/**
 * Not pretty, but you know
 */
fun initApplicationDb(app: AndroidAppApplication){
    application = app
}

actual fun createOpenHelper(
        name:String?,
        callback:PlatformSQLiteOpenHelperCallback,
        errorHandler: DatabaseErrorHandler?):SQLiteOpenHelper{

    if(application == null)
        throw IllegalArgumentException("Must call 'initApplicationDb' before creating a database")

    return PlatformSQLiteOpenHelper(
            callback,
            application!!,
            name,
            callback.version,
            errorHandler
    )
}

actual fun deleteDatabase(path:String):Boolean{
    return AndroidDatabaseSqliteSQLiteDatabase.deleteDatabaseWithJavaIoFile(JavaIoFile(path))
}
