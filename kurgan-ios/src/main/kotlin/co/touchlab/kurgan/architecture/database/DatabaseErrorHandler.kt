package co.touchlab.kurgan.architecture.database

import co.touchlab.kurgan.architecture.database.sqlite.plain.SQLiteDatabase

actual interface DatabaseErrorHandler{
    actual fun onCorruption(dbObj: SQLiteDatabase)
}