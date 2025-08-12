package com.example.qlsv

// LƯU Ý: đừng import android.R
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var edtMaLop: EditText       // editTextText
    private lateinit var edtTenLop: EditText      // editTextText2
    private lateinit var edtSiSo: EditText        // editTextNumber

    private lateinit var btnInsert: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnQuery: Button
    private lateinit var lv: ListView

    private lateinit var myList: ArrayList<String>
    private lateinit var myAdapter: ArrayAdapter<String>
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ view
        edtMaLop = findViewById(R.id.editTextText)
        edtTenLop = findViewById(R.id.editTextText2)
        edtSiSo   = findViewById(R.id.editTextNumber)

        btnInsert = findViewById(R.id.btnInsert)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        btnQuery  = findViewById(R.id.btnQuery)
        lv        = findViewById(R.id.lv)

        // ListView
        myList = arrayListOf()
        myAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, myList)
        lv.adapter = myAdapter

        // SQLite
        db = openOrCreateDatabase("QLSV.db", MODE_PRIVATE, null)
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS tbllop(
                malop TEXT PRIMARY KEY,
                tenlop TEXT,
                siso  INTEGER
            )
            """.trimIndent()
        )

        // Sự kiện click vào 1 dòng -> đổ dữ liệu lên ô nhập để sửa/xóa
        lv.setOnItemClickListener { _, _, position, _ ->
            // Định dạng chuỗi đang là: "ma - ten - siso"
            val item = myList[position]
            val parts = item.split(" - ")
            if (parts.size == 3) {
                edtMaLop.setText(parts[0])
                edtTenLop.setText(parts[1])
                edtSiSo.setText(parts[2])
            } else {
                Toast.makeText(this, "Không đọc được dữ liệu dòng", Toast.LENGTH_SHORT).show()
            }
        }

        // Nút
        btnInsert.setOnClickListener { insertRow() }
        btnUpdate.setOnClickListener { updateRow() }
        btnDelete.setOnClickListener { deleteRow() }
        btnQuery.setOnClickListener  { loadAll() }

        // Tải ban đầu
        loadAll()
    }

    private fun insertRow() {
        val ma   = edtMaLop.text.toString().trim()
        val ten  = edtTenLop.text.toString().trim()
        val siso = edtSiSo.text.toString().trim().toIntOrNull()

        if (ma.isEmpty() || ten.isEmpty() || siso == null) {
            toast("Vui lòng nhập MÃ LỚP, TÊN LỚP và SĨ SỐ hợp lệ")
            return
        }

        val values = ContentValues().apply {
            put("malop", ma)
            put("tenlop", ten)
            put("siso", siso)
        }
        val res = db.insert("tbllop", null, values)
        if (res == -1L) {
            toast("Thêm thất bại! (Trùng MÃ LỚP?)")
        } else {
            toast("Thêm thành công")
            clearInputs()
            loadAll()
        }
    }

    private fun updateRow() {
        val ma   = edtMaLop.text.toString().trim()
        val siso = edtSiSo.text.toString().trim().toIntOrNull()

        if (ma.isEmpty() || siso == null) {
            toast("Nhập MÃ LỚP và SĨ SỐ hợp lệ để cập nhật")
            return
        }

        val values = ContentValues().apply { put("siso", siso) }
        val n = db.update("tbllop", values, "malop = ?", arrayOf(ma))
        if (n == 0) toast("Không tìm thấy MÃ LỚP để cập nhật")
        else {
            toast("Đã cập nhật $n bản ghi")
            loadAll()
        }
    }

    private fun deleteRow() {
        val ma = edtMaLop.text.toString().trim()
        if (ma.isEmpty()) {
            toast("Nhập MÃ LỚP để xóa")
            return
        }
        val n = db.delete("tbllop", "malop = ?", arrayOf(ma))
        if (n == 0) toast("Không tìm thấy MÃ LỚP để xóa")
        else {
            toast("Đã xóa $n bản ghi")
            clearInputs()
            loadAll()
        }
    }

    private fun loadAll() {
        myList.clear()
        val c = db.query("tbllop", null, null, null, null, null, "malop ASC")
        c.use {
            if (it.moveToFirst()) {
                do {
                    val ma   = it.getString(it.getColumnIndexOrThrow("malop"))
                    val ten  = it.getString(it.getColumnIndexOrThrow("tenlop"))
                    val siso = it.getInt(it.getColumnIndexOrThrow("siso"))
                    myList.add("$ma - $ten - $siso")
                } while (it.moveToNext())
            }
        }
        myAdapter.notifyDataSetChanged()
    }

    private fun clearInputs() {
        edtMaLop.text?.clear()
        edtTenLop.text?.clear()
        edtSiSo.text?.clear()
        edtMaLop.requestFocus()
    }

    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
