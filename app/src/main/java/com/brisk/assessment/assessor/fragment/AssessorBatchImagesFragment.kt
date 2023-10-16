package com.brisk.assessment.assessor.fragment

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.brisk.assessment.activities.CameraActivity
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.BatchImagesLayoutBinding
import com.brisk.assessment.fragments.CameraFragment
import com.brisk.assessment.listner.ImageCallbackListener
import java.io.File

class AssessorBatchImagesFragment : Fragment(), View.OnClickListener, ImageCallbackListener {

    private lateinit var _binding : BatchImagesLayoutBinding
    private val binding get() = _binding
    private lateinit var mActivity : FragmentActivity
    var firstimg: Boolean = false
    var secondimg: Boolean = false
    var thirdimg: Boolean = false
    val REQUEST_PERMISSION_CAMERA = 203
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BatchImagesLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.pageTitle.text = "Batch Image"
        binding.toolbar.backArrowIv.setOnClickListener(this)
        CameraFragment.getImageCallback(this)
        binding.lytGroupImage.setOnClickListener(this)
        binding.lytBannerImage.setOnClickListener(this)
        binding.lytCenterImage.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0){
            binding.toolbar.backArrowIv -> {
                    mActivity.onBackPressed()
            }
            binding.lytGroupImage -> {
                firstimg = true
                secondimg = false
                thirdimg = false
                checkCameraPermission()
            }

            binding.lytBannerImage -> {
                firstimg = false
                secondimg = true
                thirdimg = false
                checkCameraPermission()
            }
            binding.lytCenterImage -> {
                firstimg = false
                secondimg = false
                thirdimg = true
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
                            if (firstimg) {
                                binding.groupImageCapture.visibility = View.VISIBLE
                                binding.groupImageCapture.setImageBitmap(
                                    Utility.getBitmapByStringImage(
                                        Utility.bitmapToBASE64(
                                            Utility.rotateImage(
                                                Utility.getBitmap(filepath)!!, rotation.toFloat()
                                            )
                                        )
                                    )
                                )
                            } else if (secondimg) {
                                binding.bannerImageCapture.visibility = View.VISIBLE
                                binding.bannerImageCapture.setImageBitmap(
                                    Utility.getBitmapByStringImage(
                                        Utility.bitmapToBASE64(
                                            Utility.rotateImage(
                                                Utility.getBitmap(filepath)!!, rotation.toFloat()
                                            )
                                        )
                                    )
                                )
                            } else {
                                binding.centerImageCapture.visibility = View.VISIBLE
                                binding.centerImageCapture.setImageBitmap(
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
        mActivity = context as FragmentActivity
    }
}