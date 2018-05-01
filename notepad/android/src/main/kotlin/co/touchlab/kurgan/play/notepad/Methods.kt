package co.touchlab.kurgan.play.notepad

import java.util.concurrent.*

actual fun memzy(body: () -> Unit) = body()

private val executorService = Executors.newSingleThreadExecutor()
private val mainHandler = android.os.Handler(android.os.Looper.getMainLooper())

actual fun initBadThreading(){
    //Just for iOS 😛
}
actual fun runOnBackground(r:Runner){
    executorService.execute {
        r.run()
    }
}

/*
actual fun runOnMain(r:Runner){
    mainHandler.post {
        r.run()
    }
}*/
