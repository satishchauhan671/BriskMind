package com.brisk.assessment.fragments

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
import com.brisk.assessment.R
import com.brisk.assessment.activities.CameraActivity
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.StudentIdProfileLayoutBinding
import com.brisk.assessment.listner.ImageCallbackListener
import java.io.File

class StudentImagesFragments : Fragment, View.OnClickListener, ImageCallbackListener {
    private var _binding: StudentIdProfileLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: FragmentActivity
    private var type : String =""
    val REQUEST_PERMISSION_CAMERA = 203
    var firstImg: Boolean = false
    var secondImg: Boolean = false
    var thirdImg: Boolean = false
    constructor()

    constructor(type : String)
    {
        this.type = type
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = StudentIdProfileLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CameraFragment.getImageCallback(this)
        binding.buttonLoginNow.setOnClickListener(this)
        binding.lytAadharFront.setOnClickListener(this)
        binding.lytAadharBack.setOnClickListener(this)
        binding.lytStudentProfile.setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onClick(p0: View?) {
        when (p0) {

            binding.buttonLoginNow -> {

                if (type.equals("Start"))
                {
                    Utility.replaceFragment(StudentProfileFragment(),mActivity.supportFragmentManager, R.id.layout_root)
                }
                else
                {
                    Utility.replaceFragment(StudentFeedbackFragment(),mActivity.supportFragmentManager, R.id.layout_root)
                }

            }
            binding.lytAadharFront -> {
                firstImg = true
                secondImg = false
                thirdImg = false
                checkCameraPermission()
            }

            binding.lytAadharBack -> {
                firstImg = false
                secondImg = true
                thirdImg = true
                checkCameraPermission()
            }
            binding.lytStudentProfile -> {
                firstImg = false
                secondImg = false
                thirdImg = true
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
                                binding.imgAadharFront.visibility = View.VISIBLE
                                binding.imgAadharFront.setImageBitmap(
                                    Utility.getBitmapByStringImage(
                                        Utility.bitmapToBASE64(
                                            Utility.rotateImage(
                                                Utility.getBitmap(filepath)!!, rotation.toFloat()
                                            )
                                        )
                                    )
                                )
                            } else if (secondImg){
                                binding.imgAadharBack.visibility = View.VISIBLE
                                binding.imgAadharBack.setImageBitmap(
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
                                binding.imgStudentProfile.visibility = View.VISIBLE
                                binding.imgStudentProfile.setImageBitmap(
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
}