package com.example.karaoke

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class DatabaseAssetHelper(private val context: Context) {

    companion object {
        const val DB_NAME = "arirang.sqlite"
        private const val DB_FOLDER_SUFFIX = "/databases/"
    }

    private fun dbFile(): File =
        File(context.applicationInfo.dataDir + DB_FOLDER_SUFFIX + DB_NAME)

    /** Chỉ copy nếu chưa tồn tại. */
    fun copyIfNeeded() {
        val outFile = dbFile()
        if (outFile.exists()) return

        outFile.parentFile?.mkdirs()
        context.assets.open(DB_NAME).use { input ->
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
        Log.i("DB", "Copied ${outFile.absolutePath}")
    }

    fun openDatabase(): SQLiteDatabase {
        copyIfNeeded()
        return SQLiteDatabase.openDatabase(
            dbFile().absolutePath, null, SQLiteDatabase.OPEN_READWRITE
        )
    }
}
