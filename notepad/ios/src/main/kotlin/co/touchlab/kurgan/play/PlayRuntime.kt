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

class PlayRuntime(){

    init {
        val context = AndroidContentIOSContext()
        DopplRuntime.start()
        initApplicationDb(context)
    }

    fun helloStart(mems:Boolean){
        Methods.testInserts(mems)
    }

    companion object {
        fun hi(mems: Boolean){
            val pr = PlayRuntime()
            pr.helloStart(mems)
        }
    }
}