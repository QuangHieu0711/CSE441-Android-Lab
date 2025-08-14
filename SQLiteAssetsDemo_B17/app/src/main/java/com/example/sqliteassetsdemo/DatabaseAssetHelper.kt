package com.example.sqliteassetsdemo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class DatabaseAssetHelper(private val context: Context) {

    companion object {
        // ĐÚNG tên file trong assets/
        const val DATABASE_NAME = "qlsach.db"
        private const val DB_FOLDER_SUFFIX = "/databases/"
    }

    private fun dbFile(): File =
        File(context.applicationInfo.dataDir + DB_FOLDER_SUFFIX + DATABASE_NAME)

    /** Copy DB từ assets vào /data/data/<pkg>/databases nếu chưa có */
    fun copyIfNeeded() {
        val outFile = dbFile()
        if (outFile.exists()) return

        outFile.parentFile?.mkdirs()
        context.assets.open(DATABASE_NAME).use { input ->
            FileOutputStream(outFile).use { output ->
                val buf = ByteArray(8 * 1024)
                while (true) {
                    val r = input.read(buf)
                    if (r == -1) break
                    output.write(buf, 0, r)
                }
                output.flush()
            }
        }
        Log.i("DB", "Copied to ${outFile.absolutePath}")
    }

    /** Mở và trả về SQLiteDatabase */
    fun openDatabase(): SQLiteDatabase {
        copyIfNeeded()
        return SQLiteDatabase.openDatabase(
            dbFile().absolutePath, null, SQLiteDatabase.OPEN_READWRITE
        )
    }
}
