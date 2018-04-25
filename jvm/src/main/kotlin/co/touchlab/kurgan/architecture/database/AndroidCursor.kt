package co.touchlab.kurgan.architecture.database

class AndroidCursor(val cursor: android.database.Cursor): Cursor{
    override fun getCount(): Int = cursor.count
    override fun getPosition(): Int = cursor.position
    override fun move(offset: Int): Boolean = cursor.move(offset)
    override fun moveToPosition(position: Int): Boolean = cursor.moveToPosition(position)
    override fun moveToFirst(): Boolean = cursor.moveToFirst()
    override fun moveToLast(): Boolean = cursor.moveToLast()
    override fun moveToNext(): Boolean = cursor.moveToNext()
    override fun moveToPrevious(): Boolean = cursor.moveToPrevious()
    override fun isFirst(): Boolean = cursor.isFirst
    override fun isLast(): Boolean = cursor.isLast
    override fun isBeforeFirst(): Boolean = cursor.isBeforeFirst
    override fun isAfterLast(): Boolean = cursor.isAfterLast
    override fun getColumnIndex(columnName: String): Int = cursor.getColumnIndex(columnName)
    override fun getColumnIndexOrThrow(columnName: String): Int = cursor.getColumnIndexOrThrow(columnName)
    override fun getColumnName(columnIndex: Int): String = cursor.getColumnName(columnIndex)
    override fun getColumnNames(): Array<String> = cursor.columnNames
    override fun getColumnCount(): Int = cursor.columnCount
    override fun getBlob(columnIndex: Int): ByteArray = cursor.getBlob(columnIndex)
    override fun getString(columnIndex: Int): String = cursor.getString(columnIndex)
    override fun getShort(columnIndex: Int): Short = cursor.getShort(columnIndex)
    override fun getInt(columnIndex: Int): Int = cursor.getInt(columnIndex)
    override fun getLong(columnIndex: Int): Long = cursor.getLong(columnIndex)
    override fun getFloat(columnIndex: Int): Float = cursor.getFloat(columnIndex)
    override fun getDouble(columnIndex: Int): Double = cursor.getDouble(columnIndex)
    override fun getType(columnIndex: Int): Int = cursor.getType(columnIndex)
    override fun isNull(columnIndex: Int): Boolean = cursor.isNull(columnIndex)
    override fun close() = cursor.close()
    override fun isClosed(): Boolean = cursor.isClosed
}