package com.brisk.assessment.assessor.activity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Html
import android.util.Log
import android.util.Size
import android.view.Surface
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.brisk.assessment.BriskMindApplication
import com.brisk.assessment.R
import com.brisk.assessment.assessor.adapter.AssessorTestPageNoAdapter
import com.brisk.assessment.assessor.adapter.PagerAdapter
import com.brisk.assessment.assessor.listener.ChooseAssessorMainListener
import com.brisk.assessment.common.Constants
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.ActivityAssessorTestBinding
import com.brisk.assessment.model.ImportLanguageResponse
import com.brisk.assessment.model.PaperResponse
import com.brisk.assessment.repositories.LoginRepo
import com.brisk.assessment.viewmodels.MainViewModel
import com.brisk.assessment.viewmodels.MainViewModelFactory
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class AssessorTestActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAssessorTestBinding
    private lateinit var view: View
    private lateinit var assessorTestPageNoAdapter: AssessorTestPageNoAdapter
    private var isVisible = false
    private lateinit var paperSetId : String
    private lateinit var batchId : String
    private lateinit var paperType : String
    private lateinit var paperList : List<PaperResponse>
    private lateinit var repo: LoginRepo
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var viewPager : ViewPager2
    lateinit var pagerListener : PagerListener
    private lateinit var cameraExecutor: ExecutorService
    private val handler = Handler()
    private val captureIntervalMillis = 10000 // Capture an image every 5 seconds (adjust as needed)
    private val CAMERA_PERMISSION_CODE = 101
    private val STORAGE_PERMISSION_CODE = 102
    private lateinit var importLanguageRes: List<ImportLanguageResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssessorTestBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)
        cameraExecutor = Executors.newSingleThreadExecutor()

        repo = LoginRepo(this.application)

        // initialize model factory
        mainViewModelFactory = MainViewModelFactory(repo)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]


        paperSetId = intent.getStringExtra(Constants.paperSetId).toString()
        batchId = intent.getStringExtra(Constants.batchId).toString()
        paperType = intent.getStringExtra(Constants.paperType).toString()

        bindPaperListData(paperSetId)

        getPaperDuration(batchId)

        binding.assessorTestLay.setOnClickListener(this)
        binding.toolbar.instructionDialogIv.setOnClickListener(this)
        binding.bottomToolbar.nextLay.setOnClickListener(this)
        binding.bottomToolbar.previousLay.setOnClickListener(this)

        viewPager = binding.viewPager


        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                updateButtonValues(position)
            }
        })

        camera()
        startImageCapture()
        // Check and request camera and storage permissions
        if (checkCameraPermission() && checkStoragePermission()) {
            // Permissions are already granted
            startImageCapture()
        } else {
            requestPermissions()
        }

    }


    private val captureRunnable = object : Runnable {
        override fun run() {
            // Capture and save the image
            captureAndSaveImage()

            // Schedule the next capture
            handler.postDelayed(this, captureIntervalMillis.toLong())
        }
    }

    // Start capturing images when needed (e.g., in onCreate)
    private fun startImageCapture() {
        handler.postDelayed(captureRunnable, captureIntervalMillis.toLong())
        if (checkCameraPermission() && checkStoragePermission()) {
            handler.postDelayed(captureRunnable, captureIntervalMillis.toLong())
        } else {
            requestPermissions()
        }
    }

    // Stop capturing images when the activity is no longer needed (e.g., in onDestroy)
    private fun stopImageCapture() {
        handler.removeCallbacks(captureRunnable)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.toolbar.instructionDialogIv -> {
                if (isVisible) {
                    binding.cardInstruction.visibility = View.GONE
                    binding.rlDimBg.visibility = View.GONE
                    isVisible = false
                } else {
                    getInstructionData()
                    binding.cardInstruction.visibility = View.VISIBLE
                    binding.rlDimBg.visibility = View.VISIBLE
                    isVisible = true
                }

            }

            binding.cardInstruction,
            binding.assessorTestLay -> {
                if (isVisible) {
                    binding.cardInstruction.visibility = View.GONE
                    binding.rlDimBg.visibility = View.GONE
                    isVisible = false
                }
            }

            binding.bottomToolbar.previousLay -> {
                val currentItem = viewPager.currentItem
                if (currentItem > 0) { // Check if not on the first page
                    viewPager.currentItem = currentItem - 1 // Go to the previous page

                    updateButtonValues(viewPager.currentItem)
                }
            }

            binding.bottomToolbar.nextLay -> {
                val currentItem = viewPager.currentItem
                if (currentItem < (viewPager.adapter?.itemCount ?: 0) - 1) { // Check if not on the last page
                    viewPager.currentItem = currentItem + 1 // Go to the next page

                    updateButtonValues(viewPager.currentItem)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopImageCapture()
    }

    private fun getInstructionData() {
        mainViewModel.getInstructionList().observeForever {
            importLanguageRes = it
            if (importLanguageRes != null && importLanguageRes.size > 0) {
                if (paperType.equals(Constants.practical))
                {
                    if (!Utility.isNullOrEmpty(importLanguageRes[0].practical_instructions)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            binding.instructionDialog.txtInstruction.text = Html.fromHtml(
                                importLanguageRes[0].practical_instructions!!.replace("\n","\n\n"),
                                Html.FROM_HTML_MODE_COMPACT
                            )
                        } else {
                            binding.instructionDialog.txtInstruction.text =
                                Html.fromHtml(importLanguageRes[0].practical_instructions!!.replace("\n","\n\n"))
                        }
                    }
                }
                else
                {
                    if (!Utility.isNullOrEmpty(importLanguageRes[0].viva_instructions)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            binding.instructionDialog.txtInstruction.text = Html.fromHtml(
                                importLanguageRes[0].viva_instructions!!.replace("\n","\n\n"),
                                Html.FROM_HTML_MODE_COMPACT
                            )
                        } else {
                            binding.instructionDialog.txtInstruction.text =
                                Html.fromHtml(importLanguageRes[0].viva_instructions!!.replace("\n","\n\n"))
                        }
                    }
                }

            }
        }
    }

    private fun bindPaperListData(paperSetId: String){
        mainViewModel.getPaperListByPaperSetId(paperSetId).observe(this){
            paperList = it
            // Set Paper No Rv
            assessorTestPageNoAdapter = AssessorTestPageNoAdapter(this, supportFragmentManager, paperList)
            assessorTestPageNoAdapter.setAdapterListener(pageChangeListener)
            binding.assessorTestPageNoRv.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.assessorTestPageNoRv.adapter = assessorTestPageNoAdapter

            binding.questionSerialNoTv.text = resources.getString(R.string.question_serial_number,
                viewPager.currentItem+1,paperList.size)

            val pagerAdapter = PagerAdapter(this@AssessorTestActivity, paperList, this)
            viewPager.adapter = pagerAdapter

        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle
            // the Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    fun setPaperListener(pagerListener: PagerListener){
        this.pagerListener = pagerListener
    }

    interface  PagerListener{
        fun pagerChangeListener(position: Int, paperResponse: PaperResponse)
    }

    private val pageChangeListener = object : ChooseAssessorMainListener {
        override fun chooseMemberAdapterListener(pos: Int, batchNo : String, batchId : String) {
                viewPager.currentItem = pos

            updateButtonValues(pos)
        }
    }

    fun updateButtonValues(position: Int){
        binding.marksTv.text = resources.getString(R.string.total_marks, paperList[position].question_marks)

        binding.questionSerialNoTv.text = resources.getString(
            R.string.question_serial_number,
            viewPager.currentItem + 1,
            paperList.size
        )

        pagerListener.pagerChangeListener(
            viewPager.currentItem + 1,
            paperList[viewPager.currentItem]
        )

    }

    private fun getPaperDuration(batchId : String){
        mainViewModel.getPaperDuration(batchId).observeForever{
            var time = it
            object : CountDownTimer(time.toLong()* 1000+1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                    var seconds = (millisUntilFinished / 1000).toInt()
                    val minutes = seconds / 60
                    val hour = minutes / 60
                    seconds %= 60
                    var h = hour.toString()
                    var m = minutes.toString()
                    var s = seconds.toString()
                    if (h.length == 1) h = "0$h"
                    if (m.length == 1) m = "0$m"
                    if (s.length == 1) s = "0$s"
                    binding.toolbar.paperTimerTv.text = "${h}:${m}:${s}"


                }

                override fun onFinish() {
                    binding.toolbar.paperTimerTv.text =  "done!"
                }
            }.start()
            binding.toolbar.paperTimerTv
        }
    }


    private fun captureAndSaveImage() {
        val outputDirectory = File(BriskMindApplication.getInstance().filesDir.absolutePath)

        // Create output directory if it doesn't exist
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs()
        }

        // Create a unique file name for the captured image
        val currentTimeMillis = System.currentTimeMillis()
        val photoFile = File(outputDirectory, "$currentTimeMillis.jpg")

        // Initialize ImageCapture use case
        val imageCapture = ImageCapture.Builder()
            .setTargetRotation(Surface.ROTATION_0)
            .setTargetResolution(Size(1920, 1080)) // Set the resolution as needed
            .build()

        // Create OutputFileOptions
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

       // Capture the image
        imageCapture.takePicture(cameraExecutor, object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                // Get the captured image data as a ByteBuffer
                val imageBuffer = image.planes[0].buffer
                val imageData = ByteArray(imageBuffer.remaining())
                imageBuffer.get(imageData)

                // Now, you can work with the image data (byte array) here
                // For example, you can convert it to a Bitmap if needed
                val capturedBitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)

                // Don't forget to close the ImageProxy to release resources
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                // Handle error while capturing the image
                Log.d("TAG", "onError: ${exception.message}")
            }
        })

        // Capture the image
//        imageCapture.takePicture(outputOptions, cameraExecutor, object : ImageCapture.OnImageSavedCallback {
//            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                // Image saved successfully
//                // You can perform additional tasks here if needed
//                val savedUri = outputFileResults.savedUri
//            }
//
//            override fun onError(exception: ImageCaptureException) {
//                // Handle error while capturing/saving the image
//                Log.d("TAG", "onError: "+exception.message)
//            }
//        })
    }


    /// Camera & Storage Permission
    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        if (!checkCameraPermission()) {
            permissionsToRequest.add(Manifest.permission.CAMERA)
        }

        if (!checkStoragePermission()) {
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        ActivityCompat.requestPermissions(
            this,
            permissionsToRequest.toTypedArray(),
            CAMERA_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_CODE || requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start image capture
                startImageCapture()
            } else {
                // Permission denied, handle it as needed (e.g., show a message to the user)
            }
        }
    }


    private fun camera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()

            // Select a camera or use the default camera
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            // Build the image capture use case
            val imageCapture = ImageCapture.Builder()
                .setTargetRotation(Surface.ROTATION_0)
                .setTargetResolution(Size(1920, 1080)) // Set the resolution as needed
                .build()

            try {
                // Unbind any existing use cases before rebinding
                cameraProvider.unbindAll()

                // Bind the use cases to the lifecycle of your activity or fragment
                val camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, imageCapture
                )
            } catch (exception: Exception) {
                Log.e("TAG", "Use case binding failed: ${exception.message}")
            }
        }, ContextCompat.getMainExecutor(this))

    }
}