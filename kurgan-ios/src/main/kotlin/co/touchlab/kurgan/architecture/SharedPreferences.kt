package co.touchlab.kurgan.architecture

import kotlinx.cinterop.*
import objcsrc.*

class SharedPreferencesImpl(val sp: AndroidContentSharedPreferencesProtocol) : SharedPreferences{

    /*val changeListMap = HashMap<SharedPreferences.OnSharedPreferenceChangeListener, AndroidContentSharedPreferences_OnSharedPreferenceChangeListenerProtocol>()

    class ListenerWrapper(val listener: SharedPreferences.OnSharedPreferenceChangeListener): AndroidContentSharedPreferences_OnSharedPreferenceChangeListenerProtocol{
        override fun onSharedPreferenceChangedWithAndroidContentSharedPreferences(sharedPreferences: AndroidContentSharedPreferencesProtocol?, withNSString: String?){
            listener.
        }
    }*/

//    override fun getAllKeys(): Set<String> {
//        val allVals = sp.
//        val keySet = HashSet<String>()
//    }

    override fun getString(key: String, defValue: String?): String? = sp.getStringWithNSString(key, defValue)
    override fun getInt(key: String, defValue: Int): Int = sp.getIntWithNSString(key, defValue)

    override fun getLong(key: String, defValue: Long): Long = sp.getLongWithNSString(key, defValue)

    override fun getFloat(key: String, defValue: Float): Float = sp.getFloatWithNSString(key, defValue)

    override fun getBoolean(key: String, defValue: Boolean): Boolean = sp.getBooleanWithNSString(key, defValue)

    override fun contains(key: String): Boolean = sp.containsWithNSString(key)

    class EditorImpl(val editor: AndroidContentSharedPreferences_EditorProtocol): SharedPreferences.Editor{
        override fun clear(): SharedPreferences.Editor {
            editor.clear()
            return this
        }

        override fun putLong(key: String, value: Long): SharedPreferences.Editor {
            editor.putLongWithNSString(key, value)
            return this
        }

        override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
            editor.putFloatWithNSString(key, value)
            return this
        }

        override fun putInt(key: String, value: Int): SharedPreferences.Editor {
            editor.putIntWithNSString(key, value)
            return this
        }

        override fun apply() {
            editor.apply()
        }

        override fun remove(key: String): SharedPreferences.Editor {
            editor.removeWithNSString(key)
            return this
        }

        override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
            editor.putBooleanWithNSString(key, value)
            return this
        }

        override fun commit(): Boolean = editor.commit()

        override fun putString(key: String, value: String?): SharedPreferences.Editor {
            editor.putStringWithNSString(key, value)
            return this
        }
    }

    override fun edit(): SharedPreferences.Editor = EditorImpl(sp.edit()!!)

    /*class InnerChangeListener(val cl:SharedPreferences.OnSharedPreferenceChangeListener): android.content.SharedPreferences.OnSharedPreferenceChangeListener
    {
        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            cl.
        }

    }*/
    /*override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        throw UnsupportedOperationException("registerOnSharedPreferenceChangeListener")
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        throw UnsupportedOperationException("registerOnSharedPreferenceChangeListener")
    }*/

}
