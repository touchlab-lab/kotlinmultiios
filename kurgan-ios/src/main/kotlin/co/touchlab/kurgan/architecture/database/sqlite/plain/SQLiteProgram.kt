package co.touchlab.kurgan.architecture.database.sqlite.plain

import objcsrc.*
import co.touchlab.kurgan.j2objc.*

actual abstract class SQLiteProgram(val real: AndroidDatabaseSqliteSQLiteProgram):SQLiteClosable(real){
    actual fun bindNull(index:Int){
        real.bindNullWithInt(index)
    }

    actual fun bindLong(index:Int, value:Long){
        real.bindLongWithInt(index, value)
    }

    actual fun bindDouble(index:Int, value:Double){
        real.bindDoubleWithInt(index, value)
    }

    actual fun bindString(index:Int, value:String){
        real.bindStringWithInt(index, value)
    }

    actual fun bindBlob(index:Int, value:ByteArray){
        real.bindBlobWithInt(index, byteArrayToIOSByteArray(value))
    }

    actual fun clearBindings(){
        real.clearBindings()
    }

    actual fun bindAllArgsAsStrings(bindArgs:Array<String>){
        real.bindAllArgsAsStringsWithNSStringArray(stringArrayAsIos(bindArgs))
    }

}