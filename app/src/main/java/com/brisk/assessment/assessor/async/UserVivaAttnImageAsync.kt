package com.brisk.assessment.assessor.async

import android.os.AsyncTask
import com.brisk.assessment.BriskMindApplication
import com.brisk.assessment.common.Constants
import com.brisk.assessment.common.Utility
import com.brisk.assessment.database.SyncUserAttendenceDataHelper
import com.brisk.assessment.database.SyncUserVivaAttendenceDataHelper
import com.brisk.assessment.listner.PhotoCompressedListener
import com.brisk.assessment.model.SyncUserAttendance
import com.brisk.assessment.model.SyncUserVivaAttendance
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class UserVivaAttnImageAsync : AsyncTask<Void?, Void?, String?> {

    private var maxHeight = 500
    private var maxWidth = 500
    private var path: String?
    private var batchId: String
    private var photoCompressedListener: PhotoCompressedListener?
    private var syncUserVivaAttendance: SyncUserVivaAttendance
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
        syncUserVivaAttendance: SyncUserVivaAttendance,
        batchId: String,
        photoCompressedListener: PhotoCompressedListener?
    ) {
        path = photoPath.absolutePath
        this.photoPath = photoPath
        this.batchId = batchId
        this.syncUserVivaAttendance = syncUserVivaAttendance
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
                syncUserVivaAttendance.entry_id = Utility.bitmapToBASE64(
                    Utility.rotateImage(
                        Utility.getBitmap(path)!!, rotation.toFloat()
                    )
                )
                syncUserVivaAttendance.entry_id_time = Utility.currentDateTime
            }
            if (secondImg) {
                syncUserVivaAttendance.entry_photo = Utility.bitmapToBASE64(
                    Utility.rotateImage(
                        Utility.getBitmap(path)!!, rotation.toFloat()
                    )
                )
                syncUserVivaAttendance.entry_photo_time = Utility.currentDateTime
            }
            if (thirdImg) {
                syncUserVivaAttendance.exit_id = Utility.bitmapToBASE64(
                    Utility.rotateImage(
                        Utility.getBitmap(path)!!, rotation.toFloat()
                    )
                )
                syncUserVivaAttendance.exit_id_time = Utility.currentDateTime
            }
            if (fourthImg) {
                syncUserVivaAttendance.exit_photo = Utility.bitmapToBASE64(
                    Utility.rotateImage(
                        Utility.getBitmap(path)!!, rotation.toFloat()
                    )
                )
                syncUserVivaAttendance.exit_photo_time = Utility.currentDateTime
            }
        }

        SyncUserVivaAttendenceDataHelper.saveSyncUserVivaAttData(
            syncUserVivaAttendance,
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