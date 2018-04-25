package co.touchlab.kurgan.architecture

//import android.app.Application
import co.touchlab.kurgan.file.*
import kotlinx.cinterop.*
import objcsrc.*

class IosDataContext(val context: AndroidContentIOSContext):DataContext {
    override fun nativeContext(): Any {
        return context
    }

    override fun getSharedPreferences(name: String, mode: Int): SharedPreferences = SharedPreferencesImpl(context.getSharedPreferencesWithNSString(name, mode)!!)
}