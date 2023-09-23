package com.brisk.assessment.assessor.activity

import android.icu.text.DateFormatSymbols.FORMAT
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.brisk.assessment.R
import com.brisk.assessment.assessor.adapter.AssessorTestPageNoAdapter
import com.brisk.assessment.assessor.adapter.PagerAdapter
import com.brisk.assessment.assessor.listener.ChooseAssessorMainListener
import com.brisk.assessment.common.Constants
import com.brisk.assessment.databinding.ActivityAssessorTestBinding
import com.brisk.assessment.model.PaperResponse
import com.brisk.assessment.repositories.LoginRepo
import com.brisk.assessment.viewmodels.MainViewModel
import com.brisk.assessment.viewmodels.MainViewModelFactory
import java.util.concurrent.TimeUnit


class AssessorTestActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAssessorTestBinding
    private lateinit var view: View
    private lateinit var assessorTestPageNoAdapter: AssessorTestPageNoAdapter
    private var isVisible = false
    private lateinit var paperSetId : String
    private lateinit var batchId : String
    private lateinit var paperList : List<PaperResponse>
    private lateinit var repo: LoginRepo
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var viewPager : ViewPager2
    lateinit var pagerListener : PagerListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssessorTestBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)

        repo = LoginRepo(this.application)

        // initialize model factory
        mainViewModelFactory = MainViewModelFactory(repo)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]


        paperSetId = intent.getStringExtra(Constants.paperSetId).toString()
        batchId = intent.getStringExtra(Constants.batchId).toString()

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

    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.toolbar.instructionDialogIv -> {
                if (isVisible) {
                    binding.cardInstruction.visibility = View.GONE
                    binding.rlDimBg.visibility = View.GONE
                    isVisible = false
                } else {
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


    private fun bindPaperListData(paperSetId: String){
        mainViewModel.getPaperListByPaperSetId(paperSetId).observeForever{
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
}