package co.touchlab.kurgan.play

import co.touchlab.kurgan.architecture.*
import co.touchlab.kurgan.architecture.database.sqlite.*
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

        var dbOpenHelper:SQLiteOpenHelper? = null
        fun helloStart(){
            DopplRuntime.start()
            initApplicationDb(context)
            dbOpenHelper = NoteDbHelper.initDatabase()

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

    }
}