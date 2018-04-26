package co.touchlab.kurgan.architecture.database.support

actual fun deleteDatabase(path:String):Boolean{
    return AndroidDatabaseSqliteSQLiteDatabase.deleteDatabase(JavaIoFile(path))
}