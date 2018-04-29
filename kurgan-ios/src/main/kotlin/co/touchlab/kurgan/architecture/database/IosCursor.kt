package co.touchlab.kurgan.architecture.database

import kotlinx.cinterop.*
import objcsrc.*
import co.touchlab.kurgan.j2objc.*

class IosCursor(val cursor: AndroidDatabaseCursorProtocol): Cursor{
    override fun getCount(): Int = cursor.getCount()
    override fun getPosition(): Int = cursor.getPosition()
    override fun move(offset: Int): Boolean = cursor.moveWithInt(offset)
    override fun moveToPosition(position: Int): Boolean = cursor.moveToPositionWithInt(position)
    override fun moveToFirst(): Boolean = cursor.moveToFirst()
    override fun moveToLast(): Boolean = cursor.moveToLast()
    override fun moveToNext(): Boolean = cursor.moveToNext()
    override fun moveToPrevious(): Boolean = cursor.moveToPrevious()
    override fun isFirst(): Boolean = cursor.isFirst()
    override fun isLast(): Boolean = cursor.isLast()
    override fun isBeforeFirst(): Boolean = cursor.isBeforeFirst()
    override fun isAfterLast(): Boolean = cursor.isAfterLast()
    override fun getColumnIndex(columnName: String): Int = cursor.getColumnIndexWithNSString(columnName)
    override fun getColumnIndexOrThrow(columnName: String): Int = cursor.getColumnIndexOrThrowWithNSString(columnName)
    override fun getColumnName(columnIndex: Int): String = cursor.getColumnNameWithInt(columnIndex)!!
    override fun getColumnNames(): Array<String> = iosObjectArrayToStringArray(cursor.getColumnNames())!!
    override fun getColumnCount(): Int = cursor.getColumnCount()
    override fun getBlob(columnIndex: Int): ByteArray = iosByteArrayToByteArray(cursor.getBlobWithInt(columnIndex))!!
    override fun getString(columnIndex: Int): String = cursor.getStringWithInt(columnIndex)!!
    override fun getShort(columnIndex: Int): Short = cursor.getShortWithInt(columnIndex)
    override fun getInt(columnIndex: Int): Int = cursor.getIntWithInt(columnIndex)
    override fun getLong(columnIndex: Int): Long = cursor.getLongWithInt(columnIndex)
    override fun getFloat(columnIndex: Int): Float = cursor.getFloatWithInt(columnIndex)
    override fun getDouble(columnIndex: Int): Double = cursor.getDoubleWithInt(columnIndex)
    override fun getType(columnIndex: Int): Int = cursor.getTypeWithInt(columnIndex)
    override fun isNull(columnIndex: Int): Boolean = cursor.isNullWithInt(columnIndex)
    override fun close() {
        cursor.close()
    }
    override fun isClosed(): Boolean = cursor.isClosed()
}