package co.touchlab.kurgan.architecture.database

expect class ContentValues(){
    constructor(size: Int)
    constructor(from: ContentValues)

    fun size():Int
    fun keySet():Set<String>
    fun get(key:String):Any?
    fun put(key: String, value: String)
    fun put(key: String, value: Byte)
    fun put(key: String, value: Short)
    fun put(key: String, value: Int)
    fun put(key: String, value: Long)
    fun put(key: String, value: Float)
    fun put(key: String, value: Double)
    fun put(key: String, value: Boolean)
    fun putNull(key: String)
    fun remove(key: String)
    fun clear()
    fun containsKey(key: String): Boolean
    fun getAsString(key: String): String?
}