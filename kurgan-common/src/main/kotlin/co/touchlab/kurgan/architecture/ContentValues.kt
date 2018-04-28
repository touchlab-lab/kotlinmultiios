package co.touchlab.kurgan.architecture

expect class ContentValues{
    fun size():Int
    fun keySet():Set<String>
    fun get(key:String):Any?
}