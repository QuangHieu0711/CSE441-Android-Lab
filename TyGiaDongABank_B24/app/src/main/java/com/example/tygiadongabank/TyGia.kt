package com.example.tygiadongabank

import android.graphics.Bitmap

data class TyGia(
    var type: String = "",
    var imageurl: String = "",
    var bitmap: Bitmap? = null,
    var muatienmat: String = "",
    var muack: String = "",
    var bantienmat: String = "",
    var banck: String = "",
    var bank: String = ""
)
