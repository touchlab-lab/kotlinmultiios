package co.touchlab.kurgan.j2objc

import kotlinx.cinterop.*
import objcsrc.*

/*
All of this array copy stuff is bad. There *must* be a way to directly pass from J2objc to Kotlin/Native, but
we're not going to worry about that right now.
 */
fun iosByteArrayToByteArray(inp: IOSByteArray?):ByteArray?{

    //This just made consumer code simpler
    if(inp == null)
        return null

    val arrayLength = inp.length().toLong()
    val out = ByteArray(arrayLength.toInt())
    for(i in 0 until arrayLength)
    {
        out[i.toInt()] = inp.byteAtIndex(i.toLong())
    }
    return out
}

fun byteArrayToIOSByteArray(inp: ByteArray?):IOSByteArray?{
    //This just made consumer code simpler
    if(inp == null)
        return null

    val arrayLength = inp.size.toLong()
    val out = IOSByteArray.arrayWithLength(arrayLength)!!
    for(i in 0 until arrayLength)
    {
        out.replaceByteAtIndex(i, inp[i.toInt()])
    }
    return out
}

fun stringArrayAsIos(inp: Array<String?>):IOSObjectArray?{

    val arrayLength = inp.size.toLong()
    val out = ComKgalliganJustdbextractSharedTypeHelper.stringArrayWithInt(arrayLength.toInt())
    for(i in 0 until arrayLength)
    {
        ComKgalliganJustdbextractSharedTypeHelper.replaceStringAtIndexWithNSStringArray(out, inp[i.toInt()], i.toInt())
    }
    return out
}

fun iosObjectArrayToStringArray(iosArray: IOSObjectArray?):Array<String>?{
    if(iosArray == null)
        return null
    val arrayLength = iosArray.length().toLong()
    val out = kotlin.Array<String>(arrayLength.toInt(),
            { i ->
                ComKgalliganJustdbextractSharedTypeHelper.stringAtIndexWithNSStringArray(iosArray, i) ?: ""
            })
    return out
}