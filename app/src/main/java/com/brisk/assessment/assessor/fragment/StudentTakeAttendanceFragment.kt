package com.brisk.assessment.assessor.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.brisk.assessment.BriskMindApplication
import com.brisk.assessment.activities.CameraActivity
import com.brisk.assessment.assessor.async.TakeAttnImageAsync
import com.brisk.assessment.common.Constants
import com.brisk.assessment.common.Utility
import com.brisk.assessment.database.LoginDataHelper
import com.brisk.assessment.database.SyncUserAttendenceDataHelper
import com.brisk.assessment.databinding.FragmentStudentTakeAttendanceBinding
import com.brisk.assessment.fragments.CameraFragment
import com.brisk.assessment.listner.ImageCallbackListener
import com.brisk.assessment.listner.PhotoCompressedListener
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.SyncUserAttendance
import java.io.File

class StudentTakeAttendanceFragment : Fragment(), View.OnClickListener, ImageCallbackListener {

    private var _binding: FragmentStudentTakeAttendanceBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: FragmentActivity
    val REQUEST_PERMISSION_CAMERA = 203
    private var bundle: Bundle? = null
    var firstImg: Boolean = false
    var secondImg: Boolean = false
    var thirdImg: Boolean = false
    var fourthImg: Boolean = false
    var batchId = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentTakeAttendanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bundle = arguments
        if (bundle != null) {
            batchId = bundle!!.getString(Constants.batchId)!!
        }
        CameraFragment.getImageCallback(this)
        binding.submitBtn.setOnClickListener(this)
        binding.lytEntryId.setOnClickListener(this)
        binding.lytEntryProfile.setOnClickListener(this)
        binding.lytExitId.setOnClickListener(this)
        binding.lytExitProfile.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {

            binding.submitBtn -> {
            }

            binding.lytEntryId -> {
                firstImg = true
                secondImg = false
                thirdImg = false
                fourthImg = false
                checkCameraPermission()
            }

            binding.lytEntryProfile -> {
                firstImg = false
                secondImg = true
                thirdImg = false
                fourthImg = false
                checkCameraPermission()
            }

            binding.lytExitId -> {
                firstImg = false
                secondImg = false
                thirdImg = true
                fourthImg = false
                checkCameraPermission()
            }

            binding.lytExitProfile -> {
                firstImg = false
                secondImg = false
                thirdImg = false
                fourthImg = true
                checkCameraPermission()
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun imageCallback(file: File) {
        try {
            if (file.exists()) {

              var  syncUserAttendance =
                    SyncUserAttendenceDataHelper.getSyncUserAttendanceDataByBatchId(batchId, mActivity)
                if (syncUserAttendance==null)
                {
                    val loginRes: LoginRes? = LoginDataHelper.getLogin(mActivity)
                    if (loginRes != null)
                    {
                        syncUserAttendance= SyncUserAttendance()
                        syncUserAttendance.batch_id = batchId
                        syncUserAttendance.user_id = loginRes.user_id
                    }
                }

                try {
                    synchronized(this) {

                        mActivity.runOnUiThread {

                            TakeAttnImageAsync(
                                file,
                                firstImg,
                                secondImg,
                                thirdImg,
                                fourthImg,
                                syncUserAttendance!!,
                                batchId,
                                object : PhotoCompressedListener {
                                    override fun compressedPhoto(path: String?) {
                                        if (syncUserAttendance != null) {
                                            if (firstImg && syncUserAttendance?.entry_id != null) {
                                                binding.imgEntryID.visibility = View.VISIBLE
                                                binding.imgEntryID.setImageBitmap(Utility.getBitmapByStringImage(syncUserAttendance?.entry_id))
                                            } else if (secondImg && syncUserAttendance?.entry_photo != null) {
                                                binding.imgEntryProfile.visibility = View.VISIBLE
                                                binding.imgEntryProfile.setImageBitmap(Utility.getBitmapByStringImage(syncUserAttendance?.entry_photo))
                                            }
                                            else if (thirdImg && syncUserAttendance?.exit_id != null) {
                                                binding.imgExitId.visibility = View.VISIBLE
                                                binding.imgExitId.setImageBitmap(Utility.getBitmapByStringImage(syncUserAttendance?.exit_id))
                                            }
                                            else if (fourthImg && syncUserAttendance?.exit_photo != null) {
                                                binding.imgExitProfile.visibility = View.VISIBLE
                                                binding.imgExitProfile.setImageBitmap(Utility.getBitmapByStringImage(syncUserAttendance?.exit_photo))
                                            }
                                        }
                                    }
                                }).execute()
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.e("CKDemo", "Exception in photo callback")
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                mActivity,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED

        ) {
            // Open Camera Intent
            CameraFragment.bothlensFacing = true
            val intent = Intent(activity, CameraActivity::class.java)
            intent.putExtra("pageAction", true)
            startActivity(intent)

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,
                    android.Manifest.permission.CAMERA
                )
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.CAMERA),
                    REQUEST_PERMISSION_CAMERA
                )
            } else {
                requestPermissions(
                    arrayOf(android.Manifest.permission.CAMERA),
                    REQUEST_PERMISSION_CAMERA
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION_CAMERA -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkCameraPermission()
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

}