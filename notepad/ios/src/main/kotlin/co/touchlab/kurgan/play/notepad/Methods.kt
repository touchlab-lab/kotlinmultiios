package co.touchlab.kurgan.play.notepad

import kotlinx.cinterop.*
import objcsrc.*
import konan.worker.*

actual fun memzy(body: () -> Unit){
    memScoped {
        autoreleasepool {
            body()
        }
    }
}

private var executorService :JavaUtilConcurrentExecutorServiceProtocol? = null// = JavaUtilConcurrentExecutors.newSingleThreadExecutor()!!
private var mainHandler :AndroidOsHandler? = null //= AndroidOsHandler(AndroidOsLooper.getMainLooper())!!

actual fun initBadThreading(){
    executorService = JavaUtilConcurrentExecutors.newSingleThreadExecutor()!!
    mainHandler = AndroidOsHandler(AndroidOsLooper.getMainLooper())!!
    println("init executorService $executorService")
}

actual fun runOnBackground(r:Runner){
    println("executorService $executorService, r $r")
    r.run()
    /*executorService!!.executeWithJavaLangRunnable(object : ComKgalliganJustdbextractSharedIosRunnable(){
        override fun runReal(){
            r.run()
        }
    })*/
}

/*
actual fun runOnMain(r:Runner){
    mainHandler!!.postWithJavaLangRunnable (object : ComKgalliganJustdbextractSharedIosRunnable(){
        override fun runReal(){
            r.run()
        }
    })
}*/
