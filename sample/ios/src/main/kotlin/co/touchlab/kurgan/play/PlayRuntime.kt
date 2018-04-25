package co.touchlab.kurgan.play

import co.touchlab.kurgan.architecture.*
import co.touchlab.kurgan.architecture.database.framework.*
import co.touchlab.kurgan.architecture.database.support.SupportSQLiteDatabase
import co.touchlab.kurgan.architecture.database.support.SupportSQLiteOpenHelper
import co.touchlab.kurgan.play.notepad.*
import kotlinx.cinterop.*
import objcsrc.*

fun helloStartGlobal(){
    PlayRuntime.helloStart()
}

fun helloInsertRow(){

}

class PlayRuntime(){
    companion object {
        val context = AndroidContentIOSContext()
        val dataContext : DataContext = IosDataContext(context)
        var dbOpenHelper:SupportSQLiteOpenHelper? = null
        fun helloStart(){
            DopplRuntime.start()
            dbOpenHelper = initDatabase()

            val db = dbOpenHelper!!.getWritableDatabase()
            val insertCount = 15000

            for(i in 0 until 3) {
                memScoped {
                    autoreleasepool {
                        NoteDbHelper.createTestNotes(db, insertCount)
                        println("Wrote $insertCount")
                        NoteDbHelper.queryData(db, insertCount)
                    }
                }
            }


        }

        fun initDatabase():SupportSQLiteOpenHelper
        {
            return FrameworkSQLiteOpenHelperFactory().create(NoteDbHelper.initDatabase(dataContext))
        }
    }
}