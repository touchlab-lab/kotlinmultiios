package co.touchlab.kurgan.architecture.database.support

import android.database.sqlite.SQLiteDatabase
import java.io.File

actual fun deleteDatabase(path:String):Boolean{
    return SQLiteDatabase.deleteDatabase(File(path))
}