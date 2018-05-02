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


        init {

        }

        fun testInserts(){
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
            runOnBackground(InsertValues(helperHolder,15000))
            runOnBackground(InsertValues(helperHolder,15000))
            runOnBackground(InsertValues(helperHolder, 15000))
            runOnBackground(SelectValues(helperHolder, 50))
        }

        fun insertValues(holder:HelperHolder, insertCount: Int) {
            val queryWrapper = QueryWrapper(holder.dbOpenHelper)

            val noteQueries = queryWrapper.noteQueries

            println("Total Count: ${noteQueries.count().executeAsOne()}")

            val now = currentTimeMillis()

            noteQueries.transaction {
                for (i in 0 until insertCount) {
                    memzy {
                        noteQueries.insertNote("asdf", "qwert 🥃👀", now, now, null)
                    }
                }
            }
        }

        fun selectValues(holder:HelperHolder, selectCount: Long) {
            val queryWrapper = QueryWrapper(holder.dbOpenHelper)

            val noteQueries = queryWrapper.noteQueries

            val alls = noteQueries.selectAll(selectCount).executeAsList()
            for(row in alls){
                println("id: ${row.id}/title: ${row.title}/note: ${row.note}")
            }
        }
    }
}

expect fun createHolder(db: SqlDelightDatabaseHelper):HelperHolder

expect fun memzy(body: () -> Unit)

expect fun runOnBackground(workerData: WorkerData)
//expect fun runOnMain(r:Runner)


interface WorkerData

data class InsertValues(val holder:HelperHolder, val count:Int):WorkerData
data class SelectValues(val holder:HelperHolder, val count:Long):WorkerData
data class WorkerResult(val count:Int)