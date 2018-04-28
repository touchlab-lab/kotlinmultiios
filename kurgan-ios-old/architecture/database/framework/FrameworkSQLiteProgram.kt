package co.touchlab.kurgan.architecture.database.framework

import co.touchlab.kurgan.architecture.database.support.SupportSQLiteProgram
import kotlinx.cinterop.*
import objcsrc.*
import co.touchlab.kurgan.j2objc.*

class FrameworkSQLiteProgram(private val mDelegate: AndroidDatabaseSqliteSQLiteProgram) : SupportSQLiteProgram {

    override fun bindNull(index: Int) {
        mDelegate.bindNullWithInt(index)
    }

    override fun bindLong(index: Int, value: Long) {
        mDelegate.bindLongWithInt(index, value)
    }

    override fun bindDouble(index: Int, value: Double) {
        mDelegate.bindDoubleWithInt(index, value)
    }

    override fun bindString(index: Int, value: String?) {
        mDelegate.bindStringWithInt(index, value)
    }

    override fun bindBlob(index: Int, value: ByteArray) {
        mDelegate.bindBlobWithInt(index, byteArrayToIOSByteArray(value))
    }

    override fun clearBindings() {
        mDelegate.clearBindings()
    }

    @Throws(Exception::class)
    fun close() {
        mDelegate.close()
    }

}