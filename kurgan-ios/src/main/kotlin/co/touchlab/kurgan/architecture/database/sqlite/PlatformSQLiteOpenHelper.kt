package co.touchlab.kurgan.architecture.database.sqlite

import objcsrc.*
import co.touchlab.kurgan.architecture.database.*

/**
 * Sometimes type systems don't work out great
 */
class PlatformSQLiteOpenHelper(
        callback: PlatformSQLiteOpenHelperCallback,
        context: AndroidContentContext,
        name: String?,
        version: Int,
        errorHandler: DatabaseErrorHandler?) :
        SQLiteOpenHelper(
                callback,
                context,
                name,
                version,
                errorHandler
        ) {

}