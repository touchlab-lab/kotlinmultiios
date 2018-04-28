package co.touchlab.kurgan.architecture.database.framework

import co.touchlab.kurgan.architecture.database.support.SupportSQLiteStatement
import kotlinx.cinterop.*
import objcsrc.*
import co.touchlab.kurgan.j2objc.*

class FrameworkSQLiteStatement(private val mDelegate: AndroidDatabaseSqliteSQLiteStatement) : SupportSQLiteStatement {

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

    override fun execute() {
        mDelegate.execute()
    }

    override fun executeUpdateDelete(): Int {
        return mDelegate.executeUpdateDelete()
    }

    override fun executeInsert(): Long {
        return mDelegate.executeInsert()
    }

    override fun simpleQueryForLong(): Long {
        return mDelegate.simpleQueryForLong()
    }

    override fun simpleQueryForString(): String {
        return mDelegate.simpleQueryForString()!!
    }

    @Throws(Exception::class)
    fun close() {
        mDelegate.close()
    }
}