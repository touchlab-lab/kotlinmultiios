package co.touchlab.kurgan.architecture

import android.app.Application
import co.touchlab.kurgan.file.File

class JvmDataContext(val context: Application):DataContext {
    override fun nativeContext(): Any {
        return context
    }
//    override fun getSharedPrefsFile(name: String): File {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

    override fun getSharedPreferences(name: String, mode: Int): SharedPreferences = SharedPreferencesImpl(context.getSharedPreferences(name, mode))
}