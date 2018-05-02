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

private var myWorker = startWorker()


actual fun runOnBackground(workerData: WorkerData){
    when(workerData){
        is InsertValues -> {
//            Methods.insertValues(holder, workerData.count)
            println("InsertValues calling")
            myWorker.schedule(TransferMode.CHECKED,{InsertValues(workerData.holder, workerData.count)}){ input ->
                println("InsertValues inside")
                Methods.insertValues(input.holder, input.count)
                WorkerResult(1234)
            }
        }
        is SelectValues ->  {
//            Methods.selectValues(holder, workerData.count)
            println("SelectValues calling")
            myWorker.schedule(TransferMode.CHECKED,{SelectValues(workerData.holder, workerData.count)}){ input ->
                println("SelectValues inside")
                Methods.selectValues(input.holder, input.count)
                WorkerResult(1234)
            }
        }
        else -> throw IllegalArgumentException("Don't know type of $workerData")
    }
}

actual fun createHolder(db: SqlDelightDatabaseHelper)= HelperHolder(db).freeze()