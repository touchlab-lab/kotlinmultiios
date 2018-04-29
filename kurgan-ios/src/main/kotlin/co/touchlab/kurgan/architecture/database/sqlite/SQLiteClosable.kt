package co.touchlab.kurgan.architecture.database.sqlite

import objcsrc.*

actual abstract class SQLiteClosable(val realClos:AndroidDatabaseSqliteSQLiteClosable){
    actual fun close(): Unit{
        realClos.close()
    }
}