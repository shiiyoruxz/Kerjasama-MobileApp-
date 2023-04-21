package com.gremoryyx.kerjasama.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.FirebaseStorage

class CompanyRepository {
    var companyImg_bitmap: Bitmap

    init {
        companyImg_bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }

    fun getImageFile(companyName: String): Task<Bitmap> {
        val storageRef = FirebaseStorage.getInstance().reference
        val companyImgRef = storageRef.child("${companyName}.png")
        return companyImgRef.getBytes(Long.MAX_VALUE)
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception!!
                }
                val data = task.result
                val bitmap = BitmapFactory.decodeByteArray(data, 0, data!!.size)
                companyImg_bitmap = bitmap
                Tasks.forResult(bitmap)
            }
    }

}