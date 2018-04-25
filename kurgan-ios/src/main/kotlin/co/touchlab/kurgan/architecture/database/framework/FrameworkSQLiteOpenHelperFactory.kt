package co.touchlab.kurgan.architecture.database.framework

import kotlinx.cinterop.*
import objcsrc.*
import co.touchlab.kurgan.j2objc.*
import co.touchlab.kurgan.architecture.database.support.SupportSQLiteOpenHelper

class FrameworkSQLiteOpenHelperFactory : SupportSQLiteOpenHelper.Factory {
    override fun create(configuration: SupportSQLiteOpenHelper.Configuration): SupportSQLiteOpenHelper {
        println("Right before the crash")
        val nativeContext = configuration.context.nativeContext().uncheckedCast<AndroidContentIOSContext>()
        println("Yeah, it wasn't that")
        return FrameworkSQLiteOpenHelper(nativeContext,
                configuration.name,
                configuration.callback!!)
    }
}