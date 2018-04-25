package co.touchlab.kurgan.file

import kotlinx.cinterop.*
import objcsrc.*
//
//class IosFile: JavaIoFile{
//    constructor(dir: JavaIoFile, withNSString: String) : super(dir, withNSString)
//    constructor(name: String) : super(name)
//    constructor(dirPath: String, withNSString: String) : super(dirPath, withNSString)
//
//}

/*
fun JavaIoFile.listFilesKt():Array<File> {

    val list = ArrayList<File>()

//    val iosArray = this.listFiles()

    return list.toTypedArray()
}

fun JavaIoFile.asdf(){

}
actual typealias File = JavaIoFile
*/

actual class File actual constructor(base: Any){
    val inFile = base.uncheckedCast<JavaIoFile>()
    actual fun canExecute():Boolean = inFile.canExecute()
    actual fun canRead():Boolean = inFile.canRead()
    actual fun canWrite():Boolean = inFile.canWrite()
    actual fun getPath():String? = inFile.getPath()

    actual fun listFilesKt():Array<File> {
        val files = inFile.listFiles()!!
        val length = files.length()
        val convFiles = ArrayList<File>(length)
        for(i in 0 until length)
        {
            convFiles.add(File(files.objectAtIndex(i.toLong())!!))
        }

        return convFiles.toTypedArray()
    }

    actual fun delete(): Boolean {
        return inFile.delete__()
    }

    actual fun getParentFile(): File? {
        return File(inFile.getParentFile()!!)
    }

    actual fun getName(): String {
        return inFile.getName()!!
    }
}