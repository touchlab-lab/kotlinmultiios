package co.touchlab.kurgan.architecture.database.support

import objcsrc.*

actual fun deleteDatabase(path:String):Boolean{
    return AndroidDatabaseSqliteSQLiteDatabase.deleteDatabaseWithJavaIoFile(JavaIoFile(path))
}