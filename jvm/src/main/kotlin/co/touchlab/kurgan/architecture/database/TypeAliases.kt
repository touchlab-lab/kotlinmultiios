package co.touchlab.kurgan.architecture.database

import android.content.ContentValues
import android.database.AbstractCursor
import android.database.AbstractWindowedCursor
import android.database.CrossProcessCursor
import android.database.Cursor
import android.database.DatabaseErrorHandler

actual typealias AbstractWindowedCursor = AbstractWindowedCursor
actual typealias AbstractCursor = AbstractCursor
actual typealias CrossProcessCursor = CrossProcessCursor
actual typealias Cursor = Cursor
actual typealias DatabaseErrorHandler = DatabaseErrorHandler
actual typealias ContentValues = ContentValues