package co.touchlab.kurgan.util
import kotlinx.cinterop.*
import objcsrc.*
import co.touchlab.kurgan.j2objc.*

actual fun currentTimeMillis():Long = JavaLangSystem.currentTimeMillis()

actual fun stringToUtf8(s:String):ByteArray{
    val j2objcArray = ComKgalliganJustdbextractSharedTypeHelper.justTestingToUtf8WithNSString(s)!!
    return iosByteArrayToByteArray(j2objcArray)!!
}

actual fun utf8ToString(b:ByteArray):String{
    val ba = byteArrayToIOSByteArray(b)!!
    return ComKgalliganJustdbextractSharedTypeHelper.justTestingFromUtf8WithByteArray(ba)!!
}

fun AndroidContentContentValues.get(key:String):Any?{
    return "asdlfkj"
}