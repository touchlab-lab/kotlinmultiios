package co.touchlab.kurgan.architecture.database

import co.touchlab.kurgan.architecture.database.sqlite.plain.SQLiteDatabase

expect interface DatabaseErrorHandler{
    fun onCorruption(dbObj: SQLiteDatabase)
}