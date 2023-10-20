package com.brisk.assessment.assessor.fragment

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
import com.brisk.assessment.activities.CameraActivity
import com.brisk.assessment.assessor.async.AssessorAttnImageAsync
import com.brisk.assessment.assessor.async.TakeAttnImageAsync
import com.brisk.assessment.common.Constants
import com.brisk.assessment.common.Utility
import com.brisk.assessment.database.LoginDataHelper
import com.brisk.assessment.database.SyncAssessorAttendenceDataHelper
import com.brisk.assessment.database.SyncUserAttendenceDataHelper
import com.brisk.assessment.databinding.AssessorIdProfileLayoutBinding
import com.brisk.assessment.fragments.CameraFragment
import com.brisk.assessment.listner.ImageCallbackListener
import com.brisk.assessment.listner.PhotoCompressedListener
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.SyncAssessorAttendance
import com.brisk.assessment.model.SyncUserAttendance
import java.io.File
import java.util.Date

class AssessorIdProfileImageFragment : Fragment(), View.OnClickListener, ImageCallbackListener {

    private lateinit var _binding: AssessorIdProfileLayoutBinding
    private val binding get() = _binding
    private lateinit var mActivity: FragmentActivity
    val REQUEST_PERMISSION_CAMERA = 203
    var firstImg: Boolean = false
    var secondImg: Boolean = false
    var thirdImg: Boolean = false
    var fourthImg: Boolean = false
    var batchId = ""
    var batchNo = ""
    var assessorIdImg = ""
    var assessorProfileImg = ""
    var userId = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AssessorIdProfileLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CameraFragment.getImageCallback(this)
        binding.submitBtn.setOnClickListener(this)
        binding.lytEntryId.setOnClickListener(this)
        binding.lytEntryProfile.setOnClickListener(this)
        binding.lytExitId.setOnClickListener(this)
        binding.lytExitProfile.setOnClickListener(this)

        val bundle = arguments
        if (bundle != null && !bundle.isEmpty) {
            batchId = bundle.getString(Constants.batchId).toString()
            batchNo = bundle.getString(Constants.batchNo).toString()
        }
    }


    override fun onClick(p0: View?) {
        when (p0) {
            binding.submitBtn -> {
                val bundle = Bundle()
                bundle.putString(Constants.batchId, batchId)
                bundle.putString(Constants.batchNo, batchNo)
                Utility.replaceFragmentWithBundle(AssessorBatchListFragment(), mActivity.supportFragmentManager, binding.layoutRoot.id,bundle)
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

    override fun imageCallback(file: File) {
        try {


            if (file.exists()) {

                var  syncAssessorAttendance =
                    SyncAssessorAttendenceDataHelper.getSyncAssessorAttendanceDataByBatchId(batchId, mActivity)
                if (syncAssessorAttendance==null)
                {
                    val loginRes: LoginRes? = LoginDataHelper.getLogin(mActivity)
                    if (loginRes != null)
                    {
                        syncAssessorAttendance= SyncAssessorAttendance()
                        syncAssessorAttendance.batch_id = batchId
                        syncAssessorAttendance.user_id = loginRes.user_id
                    }
                }

                try {
                    synchronized(this) {

                        mActivity.runOnUiThread {

                            AssessorAttnImageAsync(
                                file,
                                firstImg,
                                secondImg,
                                thirdImg,
                                fourthImg,
                                syncAssessorAttendance!!,
                                batchId,
                                object : PhotoCompressedListener {
                                    override fun compressedPhoto(path: String?) {
                                        if (syncAssessorAttendance != null) {
                                            if (firstImg && syncAssessorAttendance?.entry_id != null) {
                                                binding.imgEntryID.visibility = View.VISIBLE
                                                binding.imgEntryID.setImageBitmap(Utility.getBitmapByStringImage(syncAssessorAttendance?.entry_id))
                                            } else if (secondImg && syncAssessorAttendance?.entry_photo != null) {
                                                binding.imgEntryProfile.visibility = View.VISIBLE
                                                binding.imgEntryProfile.setImageBitmap(Utility.getBitmapByStringImage(syncAssessorAttendance?.entry_photo))
                                            }
                                            else if (thirdImg && syncAssessorAttendance?.exit_id != null) {
                                                binding.imgExitId.visibility = View.VISIBLE
                                                binding.imgExitId.setImageBitmap(Utility.getBitmapByStringImage(syncAssessorAttendance?.exit_id))
                                            }
                                            else if (fourthImg && syncAssessorAttendance?.exit_photo != null) {
                                                binding.imgExitProfile.visibility = View.VISIBLE
                                                binding.imgExitProfile.setImageBitmap(Utility.getBitmapByStringImage(syncAssessorAttendance?.exit_photo))
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