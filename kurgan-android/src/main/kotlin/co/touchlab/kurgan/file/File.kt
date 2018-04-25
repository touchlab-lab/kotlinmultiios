package co.touchlab.kurgan.file

//fun java.io.File.listFilesKt():Array<File> = this.listFiles()

fun java.io.File.asdf(){}

//actual typealias File = java.io.File

actual class File actual constructor(base: Any){
    val inFile = base as java.io.File
    actual fun canExecute():Boolean = inFile.canExecute()
    actual fun canRead():Boolean = inFile.canRead()
    actual fun canWrite():Boolean = inFile.canWrite()
    actual fun getPath():String? = inFile.path
    actual fun listFilesKt():Array<File> {
        val files = inFile.listFiles()
        val convFiles = ArrayList<File>(files.size)
        for (file in files) {
            convFiles.add(File(file))
        }

        return convFiles.toTypedArray()
    }

    actual fun delete(): Boolean {
        return inFile.delete()
    }

    actual fun getParentFile(): File? {
        return File(inFile.parentFile)
    }

    actual fun getName(): String {
        return inFile.name
    }
}