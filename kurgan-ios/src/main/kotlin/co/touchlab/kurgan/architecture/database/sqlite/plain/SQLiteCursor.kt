package co.touchlab.kurgan.architecture.database.sqlite.plain

actual class SQLiteCursor actual constructor(driver: SQLiteCursorDriver, editTable: String, query:SQLiteQuery):AbstractWindowedCursor()
