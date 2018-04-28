package co.touchlab.kurgan.architecture.database

expect class ContentValues{
    fun size():Int
    fun keySet():Set<String>
    fun get(key:String):Any?
}