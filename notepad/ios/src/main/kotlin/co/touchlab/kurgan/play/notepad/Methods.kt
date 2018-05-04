package co.touchlab.kurgan.play.notepad

import kotlinx.cinterop.*
import objcsrc.*
import konan.worker.*
import co.touchlab.kurgan.architecture.database.sqldelight.SqlDelightDatabaseHelper

actual fun memzy(body: () -> Unit){
    memScoped {
        autoreleasepool {
            body()
        }
    }
}

private var myWorker :Worker? = null


actual fun runOnBackground(workerData: WorkerData){
    if(myWorker == null) {
        myWorker = startWorker()
    }

    when(workerData){

        is InsertValues -> {
            myWorker!!.schedule(TransferMode.CHECKED,{InsertValues(workerData.holder, workerData.count, workerData.mems)}){ input ->
                input.runme()
                WorkerResult(1234)
            }
        }
        is SelectValues ->  {
            myWorker!!.schedule(TransferMode.CHECKED,{SelectValues(workerData.holder, workerData.count)}){ input ->
                input.runme()
                WorkerResult(1234)
            }
        }
        else -> throw IllegalArgumentException("Don't know type of $workerData")
    }
}

actual fun createHolder(db: SqlDelightDatabaseHelper)= HelperHolder(db).freeze()