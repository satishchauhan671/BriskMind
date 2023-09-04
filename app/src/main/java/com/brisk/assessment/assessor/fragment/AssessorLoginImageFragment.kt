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
import com.brisk.assessment.assessor.activity.AssessorActivityMain
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.FragmentAssessorLoginImageBinding
import com.brisk.assessment.fragments.CameraFragment
import com.brisk.assessment.listner.ImageCallbackListener
import java.io.File

class AssessorLoginImageFragment : Fragment(), View.OnClickListener, ImageCallbackListener {

    private var _binding: FragmentAssessorLoginImageBinding? = null
    var mActivity = FragmentActivity()
    private val binding get() = _binding!!
    val REQUEST_PERMISSION_CAMERA = 203
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssessorLoginImageBinding.inflate(inflater, container, false)
        CameraFragment.getImageCallback(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardSaveNext.setOnClickListener(this)
        binding.lytAssessorLogin.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.cardSaveNext -> {
                val intent = Intent(context, AssessorActivityMain::class.java)
                startActivity(intent)
            }

            binding.lytAssessorLogin -> {
                checkCameraPermission()
            }

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


    override fun imageCallback(file: File) {
        try {
            if (file.exists()) {

                val filepath = file.absolutePath
                val rotation = Utility.getRotation(filepath)

                _binding!!.imgAssessorLogin.setImageBitmap(
                    Utility.getBitmapByStringImage(
                        Utility.bitmapToBASE64(
                            Utility.rotateImage(
                                Utility.getBitmap(filepath)!!, rotation.toFloat()
                            )
                        )
                    )
                )

            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.e("CKDemo", "Exception in photo callback")
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }
}