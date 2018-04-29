package co.touchlab.kurgan.architecture.database.sqlite

import objcsrc.*

actual class SQLiteStatement(val realStmt:AndroidDatabaseSqliteSQLiteStatement):SQLiteProgram(realStmt){
    actual fun execute(){
        realStmt.execute()
    }

    actual fun executeUpdateDelete(): Int{
        return realStmt.executeUpdateDelete()
    }

    actual fun executeInsert(): Long{
        return realStmt.executeInsert()
    }

    actual fun simpleQueryForLong(): Long{
        return realStmt.simpleQueryForLong()
    }

    actual fun simpleQueryForString(): String{
        return realStmt.simpleQueryForString()!!
    }
}