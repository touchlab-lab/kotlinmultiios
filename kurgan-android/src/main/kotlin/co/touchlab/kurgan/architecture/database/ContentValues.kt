package co.touchlab.kurgan.architecture.database

typealias AndroidContentValues = android.content.ContentValues

fun ContentValues.platformValues():AndroidContentValues{
    val acv = AndroidContentValues(mValues.size)
    for(colName in keySet()){
        val arg = mValues.get(colName)
        when (arg) {
            null -> acv.putNull(colName)
            is ByteArray -> acv.put(colName, arg)
            is Float -> acv.put(colName, arg)
            is Double -> acv.put(colName, arg)
            is Long -> acv.put(colName, arg)
            is Int -> acv.put(colName, arg)
            is Short -> acv.put(colName, arg)
            is Byte -> acv.put(colName, arg)
            is String -> acv.put(colName, arg)
            else -> throw IllegalArgumentException("Cannot bind $arg with name $colName")
        }
    }

    return acv
}