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
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.FragmentStudentTakeAttendanceBinding
import com.brisk.assessment.fragments.CameraFragment
import com.brisk.assessment.listner.ImageCallbackListener
import java.io.File

class StudentTakeAttendanceFragment : Fragment(), View.OnClickListener, ImageCallbackListener {

    private var _binding: FragmentStudentTakeAttendanceBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: FragmentActivity
    val REQUEST_PERMISSION_CAMERA = 203
    var firstImg: Boolean = false
    var secondImg: Boolean = false
    var thirdImg: Boolean = false
    var fourthImg: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentTakeAttendanceBinding.inflate(inflater, container, false)
        CameraFragment.getImageCallback(this)
        binding.submitBtn.setOnClickListener(this)
        binding.lytEntryId.setOnClickListener(this)
        binding.lytEntryProfile.setOnClickListener(this)
        binding.lytExitId.setOnClickListener(this)
        binding.lytExitProfile.setOnClickListener(this)
        return binding.root
    }


    override fun onClick(p0: View?) {
        when (p0) {

            binding.submitBtn ->{

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

                val filepath = file.absolutePath
                val rotation = Utility.getRotation(filepath)

                try {
                    synchronized(this) {

                        mActivity.runOnUiThread {
                            if (firstImg) {
                                binding.imgEntryID.visibility = View.VISIBLE
                                binding.imgEntryID.setImageBitmap(
                                    Utility.getBitmapByStringImage(
                                        Utility.bitmapToBASE64(
                                            Utility.rotateImage(
                                                Utility.getBitmap(filepath)!!, rotation.toFloat()
                                            )
                                        )
                                    )
                                )
                            } else if (secondImg) {
                                binding.imgEntryProfile.visibility = View.VISIBLE
                                binding.imgEntryProfile.setImageBitmap(
                                    Utility.getBitmapByStringImage(
                                        Utility.bitmapToBASE64(
                                            Utility.rotateImage(
                                                Utility.getBitmap(filepath)!!, rotation.toFloat()
                                            )
                                        )
                                    )
                                )
                            }
                            else if (thirdImg) {
                                binding.imgExitId.visibility = View.VISIBLE
                                binding.imgExitId.setImageBitmap(
                                    Utility.getBitmapByStringImage(
                                        Utility.bitmapToBASE64(
                                            Utility.rotateImage(
                                                Utility.getBitmap(filepath)!!, rotation.toFloat()
                                            )
                                        )
                                    )
                                )
                            }
                            else {
                                binding.imgExitProfile.visibility = View.VISIBLE
                                binding.imgExitProfile.setImageBitmap(
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