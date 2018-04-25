package co.touchlab.kurgan.play.notepad

import co.touchlab.kurgan.architecture.DataContext
import co.touchlab.kurgan.architecture.database.ContentValues
import co.touchlab.kurgan.architecture.database.support.SupportSQLiteDatabase
import co.touchlab.kurgan.architecture.database.support.SupportSQLiteOpenHelper
import co.touchlab.kurgan.play.notepad.NoteColumns.Companion.NOTE
import co.touchlab.kurgan.play.notepad.NoteColumns.Companion.TITLE
import co.touchlab.kurgan.play.notepad.NoteColumns.Companion.NOTES_TABLE_NAME
import co.touchlab.kurgan.util.currentTimeMillis

class NoteDbHelper {
    companion object {
        fun initDatabase(dataContext: DataContext): SupportSQLiteOpenHelper.Configuration
        {
            return SupportSQLiteOpenHelper.Configuration(dataContext, "HelloDb", object : SupportSQLiteOpenHelper.Callback(2){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    db.execSQL(
                    "CREATE TABLE " + NOTES_TABLE_NAME + " ("
                    + NoteColumns._ID + " INTEGER PRIMARY KEY,"
                    + NoteColumns.TITLE + " TEXT,"
                    + NoteColumns.NOTE + " TEXT,"
                    + NoteColumns.CREATED_DATE + " INTEGER,"
                    + NoteColumns.MODIFIED_DATE + " INTEGER"
                    + ");")
                    println("Create table success!!!!!")
                }

                override fun onUpgrade(db: SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) {
                    db.execSQL("DROP TABLE IF EXISTS notes")
                    onCreate(db)
                }

            })
        }

        fun insertNotes(db: SupportSQLiteDatabase, note:Array<Pair<String,String>>){
            db.beginTransaction()
            try {

                val now = currentTimeMillis()
                for (s in note) {
                    val cv = ContentValues()
                    cv.put(NoteColumns.TITLE, s.first)
                    cv.put(NoteColumns.NOTE, s.second)
                    cv.put(NoteColumns.CREATED_DATE, now)
                    cv.put(NoteColumns.MODIFIED_DATE, now)
                    db.insert(NOTES_TABLE_NAME, values = cv)

//                    db.execSQL("insert into $NOTES_TABLE_NAME(${NoteColumns.TITLE}, ${NoteColumns.NOTE}, ${NoteColumns.CREATED_DATE}, ${NoteColumns.MODIFIED_DATE})" +
//                            "values('${s.first}','${s.second}', $now, $now)")
                }
                db.setTransactionSuccessful()
            }finally {
                db.endTransaction()
            }
        }

        val TEST_NOTE_COUNT = 100000

        fun createTestNotes(db: SupportSQLiteDatabase, count:Int) {
            val list = ArrayList<Pair<String,String>>(count)
            for(i in 0..count) {
                list.add(Pair("title $i", "note $i"))
            }

            insertNotes(db, list.toTypedArray())
        }

        fun queryData(db: SupportSQLiteDatabase, count:Int) {
            val list = ArrayList<Pair<String,String>>(TEST_NOTE_COUNT)

            val cursor = db.query(
                    "select * from $NOTES_TABLE_NAME " +
                    "order by ${NoteColumns.CREATED_DATE} desc " +
                    "limit $count"
            )

            val titleColumn = cursor.getColumnIndex(TITLE)
            val noteColumn = cursor.getColumnIndex(NOTE)

            println("queryData titleColumn $titleColumn")
            println("queryData noteColumn $noteColumn")
            while (cursor.moveToNext()){
                list.add(Pair(cursor.getString(titleColumn), cursor.getString(noteColumn)))
            }

            println("Count Result ${list.size}")
            for (i in 0..20) {
                val pair = list[i]
                println("title: ${pair.first}/note: ${pair.second}")
            }
        }

    }
}