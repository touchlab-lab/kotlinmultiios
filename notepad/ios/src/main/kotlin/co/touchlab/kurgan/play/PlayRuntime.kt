package co.touchlab.kurgan.play

import co.touchlab.kurgan.architecture.*
import co.touchlab.kurgan.play.notepad.*
import co.touchlab.kurgan.architecture.database.sqlite.*
import co.touchlab.kurgan.play.notepad.*
import co.touchlab.kurgan.architecture.database.sqldelight.*
import co.touchlab.kurgan.util.currentTimeMillis
import com.squareup.sqldelight.db.SqlDatabase
//import co.touchlab.kurgan.play.notepad.*
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

        fun helloStart(){
            DopplRuntime.start()
            println("Before initBadThreading")
            initBadThreading()
            println("After initBadThreading")
            initApplicationDb(context)

            Methods.testInserts()
        }
    }
}