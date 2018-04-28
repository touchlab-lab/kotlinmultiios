package co.touchlab.kurgan.architecture.database.sqlite.plain

import co.touchlab.kurgan.architecture.database.Cursor

actual interface CursorFactory{
    actual fun newCursor(db: SQLiteDatabase,
                  masterQuery: SQLiteCursorDriver,
                  editTable: String,
                  query: SQLiteQuery): Cursor
}

