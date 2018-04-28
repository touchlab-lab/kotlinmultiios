package co.touchlab.kurgan.architecture.database

import kotlinx.cinterop.*
import objcsrc.*
import co.touchlab.kurgan.*
import co.touchlab.kurgan.j2objc.*

typealias IosContentValues = AndroidContentContentValues

fun ContentValues.platformValues():IosContentValues{
    val acv = IosContentValues(mValues.size)
    for(colName in keySet()){
        val arg = mValues.get(colName)
        when (arg) {
            null -> acv.putNullWithNSString(colName)
            is ByteArray -> acv.putWithNSString(colName, byteArrayToIOSByteArray(arg))
            is Float -> acv.putWithNSString(colName, JavaLangFloat.valueOfWithFloat(arg)!!)
            is Double -> acv.putWithNSString(colName, JavaLangDouble.valueOfWithDouble(arg)!!)
            is Long -> acv.putWithNSString(colName, JavaLangLong.valueOfWithLong(arg)!!)
            is Int -> acv.putWithNSString(colName, JavaLangInteger.valueOfWithInt(arg)!!)
            is Short -> acv.putWithNSString(colName, JavaLangShort.valueOfWithShort(arg)!!)
            is Byte -> acv.putWithNSString(colName, JavaLangByte.valueOfWithByte(arg)!!)
            is String -> acv.putWithNSString(colName, arg)
            else -> throw IllegalArgumentException("Cannot bind $arg with name $colName")
        }
    }

    return acv
}