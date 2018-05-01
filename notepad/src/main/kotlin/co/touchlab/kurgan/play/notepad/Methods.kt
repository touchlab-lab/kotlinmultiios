package co.touchlab.kurgan.play.notepad


import co.touchlab.kurgan.architecture.database.sqldelight.SqlDelightDatabaseHelper
import co.touchlab.kurgan.architecture.database.sqldelight.create
import co.touchlab.kurgan.architecture.database.sqlite.*
import co.touchlab.kurgan.util.currentTimeMillis
import kotlin.coroutines.experimental.*
import kotlin.coroutines.experimental.intrinsics.*

class Methods{
    companion object {
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

            val dbOpenHelper = SqlDelightDatabaseHelper(createOpenHelper("binkyfarts", callback, null))
            var queryWrapper = QueryWrapper(dbOpenHelper)

            val noteQueries = queryWrapper.noteQueries

            runOnBackground(object : Runner{
                override fun run() {
                    for(i in 0 until 3) {
                        insertValues(noteQueries)
                        println("Inserted $i")
                    }
                    val alls = selectValues(noteQueries)

                    for(row in alls){
                        println("id: ${row.id}/title: ${row.title}/note: ${row.note}")
                    }
                    /*runOnMain(object : Runner{
                        override fun run() {
                            for(row in alls){
                                println("id: ${row.id}/title: ${row.title}/note: ${row.note}")
                            }
                        }
                    })*/
                }
            })



        }

        fun selectValues(noteQueries: NoteQueries) =
                noteQueries.selectAll(20).executeAsList()

        fun insertValues(noteQueries: NoteQueries) {
            println("Total Count: ${noteQueries.count().executeAsOne()}")
            val insertCount = 15000

            val now = currentTimeMillis()

            noteQueries.transaction {
                for (i in 0 until insertCount) {
                    memzy {
                        noteQueries.insertNote("asdf", "qwert 🥃👀", now, now, null)
                    }
                }
            }
        }
    }
}

expect fun memzy(body: () -> Unit)

expect fun initBadThreading()
expect fun runOnBackground(r:Runner)
//expect fun runOnMain(r:Runner)

interface Runner{
    fun run()
}