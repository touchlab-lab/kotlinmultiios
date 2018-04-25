package co.touchlab.kurgan.file

expect class File(base: Any)
{
//    constructor(path:String)
//    constructor(path: String, name: String)

    fun canExecute():Boolean
    fun canRead():Boolean
    fun canWrite():Boolean
    fun getPath():String?
    fun listFilesKt():Array<File>
    fun delete():Boolean
    fun getParentFile():File?
    fun getName():String
}