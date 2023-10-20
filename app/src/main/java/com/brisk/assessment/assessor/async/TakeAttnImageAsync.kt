package com.brisk.assessment.assessor.async

import android.os.AsyncTask
import com.brisk.assessment.BriskMindApplication
import com.brisk.assessment.common.Constants
import com.brisk.assessment.common.Utility
import com.brisk.assessment.database.SyncUserAttendenceDataHelper
import com.brisk.assessment.database.SyncUserVivaAttendenceDataHelper
import com.brisk.assessment.listner.PhotoCompressedListener
import com.brisk.assessment.model.SyncUserAttendance
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class TakeAttnImageAsync : AsyncTask<Void?, Void?, String?> {

    private var maxHeight = 500
    private var maxWidth = 500
    private var path: String?
    private var batchId: String
    private var photoCompressedListener: PhotoCompressedListener?
    private var syncUserAttendance: SyncUserAttendance
    private var photoPath: File
    private var firstImg = false
    private var secondImg = false
    private var thirdImg = false
    private var fourthImg = false

    constructor(
        photoPath: File,
        firstImg: Boolean,
        secondImg: Boolean,
        thirdImg: Boolean,
        fourthImg: Boolean,
        syncUserAttendance: SyncUserAttendance,
        batchId: String,
        photoCompressedListener: PhotoCompressedListener?
    ) {
        path = photoPath.absolutePath
        this.photoPath = photoPath
        this.batchId = batchId
        this.syncUserAttendance = syncUserAttendance
        this.firstImg = firstImg
        this.secondImg = secondImg
        this.thirdImg = thirdImg
        this.fourthImg = fourthImg
        this.photoCompressedListener = photoCompressedListener
    }


    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg voids: Void?): String? {
        if (photoPath.exists() && path != null) {

            val myDir: File = BriskMindApplication.mInstance!!.applicationContext.filesDir
            val externalDirectory: File? =
                BriskMindApplication.mInstance!!.applicationContext.getExternalFilesDir(path)
            val documentsFolder = File(myDir, "BRISK MIND")
            if (!documentsFolder.exists()) {
                documentsFolder.mkdirs()
            }
            try {
                val destination = File(documentsFolder.path + "/" + externalDirectory?.name)
                val src = FileInputStream(photoPath).channel
                val dst = FileOutputStream(destination).channel
                dst.transferFrom(src, 0, src.size())
                src.close()
                dst.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val numFiles: File = File(
                BriskMindApplication.mInstance!!.applicationContext.filesDir.absolutePath
                    .toString() + "/BRISK MIND" + "/" + externalDirectory?.name
            )
            path = numFiles.path
            photoPath = numFiles

            val rotation = Utility.getRotation(path)
            if (maxHeight > 0 && maxWidth > 0 && photoPath.length() / 1024 > 200) {
                while (photoPath.length() / 1024 > 200) {
                    Utility.setPic(path, maxHeight, maxWidth)
                    maxHeight = maxHeight - 100
                    maxWidth = maxWidth - 100
                }
            }
            if (firstImg) {
                syncUserAttendance.entry_id = Utility.bitmapToBASE64(
                    Utility.rotateImage(
                        Utility.getBitmap(path)!!, rotation.toFloat()
                    )
                )
                syncUserAttendance.entry_id_time = Utility.currentDateTime
            }
            if (secondImg) {
                syncUserAttendance.entry_photo = Utility.bitmapToBASE64(
                    Utility.rotateImage(
                        Utility.getBitmap(path)!!, rotation.toFloat()
                    )
                )
                syncUserAttendance.entry_photo_time = Utility.currentDateTime
            }
            if (thirdImg) {
                syncUserAttendance.exit_id = Utility.bitmapToBASE64(
                    Utility.rotateImage(
                        Utility.getBitmap(path)!!, rotation.toFloat()
                    )
                )
                syncUserAttendance.exit_id_time = Utility.currentDateTime
            }
            if (fourthImg) {
                syncUserAttendance.exit_photo = Utility.bitmapToBASE64(
                    Utility.rotateImage(
                        Utility.getBitmap(path)!!, rotation.toFloat()
                    )
                )
                syncUserAttendance.exit_photo_time = Utility.currentDateTime
            }
        }

        SyncUserAttendenceDataHelper.saveSyncUserAttendenceData(
            syncUserAttendance,
            BriskMindApplication.mInstance!!
        )
        try {
            Utility.deleteImages(BriskMindApplication.mInstance!!!!)
        } catch (e: Exception) {
            e.message
        }

        return path
    }

    override fun onPostExecute(path: String?) {
        super.onPostExecute(path)
        if (photoCompressedListener != null) photoCompressedListener!!.compressedPhoto(path)
    }


}