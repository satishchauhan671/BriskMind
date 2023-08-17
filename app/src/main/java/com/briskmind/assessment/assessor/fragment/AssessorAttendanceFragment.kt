package com.briskmind.assessment.assessor.fragment

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
import com.briskmind.assessment.activities.CameraActivity
import com.briskmind.assessment.common.Utility
import com.briskmind.assessment.databinding.FragmentAssessorAttendanceBinding
import com.briskmind.assessment.fragments.CameraFragment
import com.briskmind.assessment.listner.ImageCallbackListener
import java.io.File

class AssessorAttendanceFragment : Fragment(), View.OnClickListener, ImageCallbackListener {

    private var _binding: FragmentAssessorAttendanceBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: FragmentActivity
    val REQUEST_PERMISSION_CAMERA = 203
    var firstimg: Boolean = false
    var secondimg: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssessorAttendanceBinding.inflate(inflater, container, false)
        CameraFragment.getImageCallback(this)
        binding.submitBtn.setOnClickListener(this)
        binding.profileMarkOutIv.setOnClickListener(this)
        binding.markOutIv.setOnClickListener(this)
        binding.profileMarkInIv.setOnClickListener(this)
        binding.markInIv.setOnClickListener(this)
        return binding.root
    }


    override fun onClick(p0: View?) {
        when (p0) {

            binding.submitBtn ->{

            }

            binding.markInIv -> {
                firstimg = true
                secondimg = false
                checkCameraPermission()
            }

            binding.profileMarkInIv -> {
                firstimg = false
                secondimg = true
                checkCameraPermission()
            }

        }
    }

    override fun imageCallback(file: File) {
        try {
            if (file.exists()) {

                val filepath = file.absolutePath
                val rotation = Utility.getRotation(filepath)

                if (firstimg) {
                    _binding!!.markInIv.setImageBitmap(
                        Utility.getBitmapByStringImage(
                            Utility.bitmapToBASE64(
                                Utility.rotateImage(
                                    Utility.getBitmap(filepath)!!, rotation.toFloat()
                                )
                            )
                        )
                    )
                } else {
                    _binding!!.profileMarkInIv.setImageBitmap(
                        Utility.getBitmapByStringImage(
                            Utility.bitmapToBASE64(
                                Utility.rotateImage(
                                    Utility.getBitmap(filepath)!!, rotation.toFloat()
                                )
                            )
                        )
                    )
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