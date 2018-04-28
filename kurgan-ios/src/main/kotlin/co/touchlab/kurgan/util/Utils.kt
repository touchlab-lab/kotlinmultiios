package co.touchlab.kurgan.util
import kotlinx.cinterop.*
import objcsrc.*

actual fun currentTimeMillis():Long = JavaLangSystem.currentTimeMillis()

fun AndroidContentContentValues.get(key:String):Any?{
    return "asdlfkj"
}