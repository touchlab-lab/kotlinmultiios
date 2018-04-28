package co.touchlab.kurgan.architecture.database.sqlite

import co.touchlab.kurgan.architecture.database.AbstractWindowedCursor

expect class SQLiteCursor(driver: SQLiteCursorDriver, editTable: String, query:SQLiteQuery):AbstractWindowedCursor