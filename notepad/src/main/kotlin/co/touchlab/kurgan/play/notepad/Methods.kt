package co.touchlab.kurgan.play.notepad


import co.touchlab.kurgan.architecture.database.sqldelight.SqlDelightDatabaseHelper
import co.touchlab.kurgan.architecture.database.sqldelight.create
import co.touchlab.kurgan.architecture.database.sqlite.*
import co.touchlab.kurgan.util.currentTimeMillis
import kotlin.coroutines.experimental.*
import kotlin.coroutines.experimental.intrinsics.*

data class HelperHolder(val dbOpenHelper : SqlDelightDatabaseHelper)

class Methods{
    companion object {
        fun testInserts(mems:Boolean){
            val callback = object : PlatformSQLiteOpenHelperCallback(4){
                override fun onCreate(db: SQLiteDatabase) {
                    QueryWrapper.onCreate(QueryWrapper.create(db).getConnection())
                    println("Create table success!!!!!")
                }

                override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
                    QueryWrapper.onMigrate(
                            QueryWrapper.create(db).getConnection(),
                            oldVersion,
                            newVersion
                    )
                }
            }

            val helperHolder = createHolder(SqlDelightDatabaseHelper(createOpenHelper("binkyiscool", callback, null)))
            runOnBackground(InsertValues(helperHolder,15000, mems))
            runOnBackground(InsertValues(helperHolder,15000, mems))
            runOnBackground(InsertValues(helperHolder, 15000, mems))
            runOnBackground(SelectValues(helperHolder, 10))
        }
    }
}

expect fun createHolder(db: SqlDelightDatabaseHelper):HelperHolder

expect fun memzy(body: () -> Unit)

expect fun runOnBackground(workerData: WorkerData)
//expect fun runOnMain(r:Runner)


interface WorkerData{
    fun runme()
}

data class InsertValues(val holder:HelperHolder, val count:Int, val mems:Boolean):WorkerData{
    override fun runme() {
        val queryWrapper = QueryWrapper(holder.dbOpenHelper)

        val noteQueries = queryWrapper.noteQueries

        println("Total Count: ${noteQueries.count().executeAsOne()}")

        val now = currentTimeMillis()

        noteQueries.transaction {
            if(mems) {
                for (i in 0 until count) {
                    memzy {
                        noteQueries.insertNote("asdf", "qwert 🥃👀", now, now, null)
                    }
                }
            }else{
                memzy {
                    for (i in 0 until count) {
                        noteQueries.insertNote("asdf", "qwert 🥃👀", now, now, null)
                    }
                }
            }
        }
    }
}
data class SelectValues(val holder:HelperHolder, val count:Long):WorkerData{
    override fun runme() {
        val queryWrapper = QueryWrapper(holder.dbOpenHelper)

        val noteQueries = queryWrapper.noteQueries

        val alls = noteQueries.selectAll(count).executeAsList()
        for(row in alls){
            println("id: ${row.id}/title: ${row.title}/note: ${row.note}")
        }
    }
}
data class WorkerResult(val count:Int)