package com.brisk.assessment.fragments

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.brisk.assessment.BuildConfig
import com.brisk.assessment.R
import com.brisk.assessment.assessor.activity.AssessorActivityMain
import com.brisk.assessment.common.Utility
import com.brisk.assessment.common.Utility.showSnackBar
import com.brisk.assessment.database.BatchConfigDataHelper
import com.brisk.assessment.database.BatchDataHelper
import com.brisk.assessment.database.FeedbackDataHelper
import com.brisk.assessment.database.ImportLanguageDataHelper
import com.brisk.assessment.database.LanguageDataHelper
import com.brisk.assessment.database.LanguageTextDataHelper
import com.brisk.assessment.database.LoginDataHelper
import com.brisk.assessment.database.OptionArrayDataHelper
import com.brisk.assessment.database.PaperDataHelper
import com.brisk.assessment.database.PaperDataSource
import com.brisk.assessment.database.SubQuestionDataHelper
import com.brisk.assessment.database.TransOptionDataHelper
import com.brisk.assessment.database.UserArrayDataHelper
import com.brisk.assessment.databinding.AssessorLoginLayoutBinding
import com.brisk.assessment.model.ImportAssessmentResponse
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.PaperResponse
import com.brisk.assessment.retrofit.ApiClient
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment(), View.OnClickListener {

    private var loginAs: String = ""
    private var _binding: AssessorLoginLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: FragmentActivity
    private lateinit var dialog: Dialog
    private var loginId: String? = ""
    private var password: String? = ""
    private var loginType: String? = ""
    private var userId: String? = ""
    private val networkService = ApiClient.getApiClient()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AssessorLoginLayoutBinding.inflate(inflater, container, false)
        binding.txtSignInAssessor.setOnClickListener(this)
        binding.signInLayAssessor.setOnClickListener(this)

        setLoginAs()

        return binding.layoutRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = Utility.progressDialog(mActivity)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.txtSignInAssessor,
            binding.signInLayAssessor -> {
                if (loginCheck()) {
                    if (Utility.isOnline(mActivity)) {
                        userSignIn(
                            loginAs,
                            binding.etLoginId.text.toString(),
                            binding.edtPassword.text.toString()
                        )
                    } else {
                        Utility.snackBar(
                            binding.layoutRoot,
                            resources.getString(R.string.no_internet_connection),
                            1200,
                            ContextCompat.getColor(mActivity, R.color.red)
                        )
                    }
                }
            }
        }
    }

    private fun userSignIn(loginAs: String, loginId: String, password: String) {

        val progressDialog = ProgressDialog(mActivity, R.style.MyAlertDialogStyle)
        progressDialog.setMessage("Please Wait...")
        progressDialog.show()
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)


        val call: Call<LoginRes> = networkService.login(
            Utility.stringToBase64(Build.DEVICE),
            Utility.stringToBase64(password),
            Utility.stringToBase64(loginId),
            Utility.stringToBase64(BuildConfig.VERSION_NAME),
            mActivity.packageName,
            loginAs
        )

        val enqueue = call.enqueue(object : Callback<LoginRes?> {
            override fun onResponse(
                call: Call<LoginRes?>,
                response: Response<LoginRes?>
            ) {
                //progressDialog.cancel()
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        if (loginResponse.status.equals("success", ignoreCase = true)) {
                            LoginDataHelper.deleteAll(mActivity)
                            LoginDataHelper.saveUserDatabaseData(loginResponse, mActivity)

                            LanguageDataHelper.deleteAll(mActivity)
                            LanguageTextDataHelper.deleteAll(mActivity)
                            if (loginResponse.languages != null) {
                                for (languageRes in loginResponse.languages!!) {
                                    LanguageDataHelper.saveLanguageData(
                                        languageRes,
                                        mActivity
                                    )

                                    if (languageRes.texts != null) {
                                        LanguageTextDataHelper.saveLanguageTextData(
                                            languageRes.texts!!,
                                            mActivity
                                        )
                                    }
                                }
                            }

                            getImportData(
                                progressDialog,
                                loginResponse.user_id!!,
                                loginResponse.login_type!!
                            )

                            /* val intent = Intent(this@LoginActivity, ServicesActivity::class.java)
                             startActivity(intent)*/
                        } else {
                            progressDialog.cancel()
                            //================================================ In Case of User Not Found======================
                            Utility.snackBar(
                                binding.layoutRoot, loginResponse.message,
                                1200,
                                ContextCompat.getColor(mActivity, R.color.red)
                            )
                        }
                    }
                } else {
                    progressDialog.cancel()
                    Log.d("TAG", "onResponse: " + response.message())
                }
            }

            override fun onFailure(call: Call<LoginRes?>, t: Throwable) {
                progressDialog.cancel()
                call.cancel()
            }
        })
    }

    private fun getImportData(
        progressDialog: ProgressDialog,
        userId: String,
        loginAppType: String
    ) {
        progressDialog.setMessage("Please Wait...\n We Are Importing Your Data...")
        val call: Call<ImportAssessmentResponse> = networkService.importAssessment(
            Utility.stringToBase64(userId),
            Utility.stringToBase64(Build.DEVICE),
            Utility.stringToBase64(loginAppType),
            Utility.stringToBase64(BuildConfig.VERSION_NAME)
        )

        val enqueue = call.enqueue(object : Callback<ImportAssessmentResponse?> {
            override fun onResponse(
                call: Call<ImportAssessmentResponse?>,
                response: Response<ImportAssessmentResponse?>
            ) {

                if (response.isSuccessful) {
                    val importDataResponse = response.body()
                    if (importDataResponse != null) {

                        if (importDataResponse.batch_array != null) {
                            BatchDataHelper.deleteAll(mActivity)
                            for (batchData in importDataResponse.batch_array!!) {
                                BatchDataHelper.saveBatchData(
                                    batchData,
                                    mActivity
                                )

                                if (batchData.batch_config != null) {
                                    BatchConfigDataHelper.saveBatchConfigData(
                                        batchData.batch_config!!,
                                        mActivity
                                    )
                                }
                            }
                        }

                        if (importDataResponse.user_array != null) {
                            UserArrayDataHelper.deleteAll(mActivity)
                            for (userArrayData in importDataResponse.user_array!!) {
                                UserArrayDataHelper.saveUserArrayData(
                                    userArrayData,
                                    mActivity
                                )
                            }
                        }

                        if (importDataResponse.lang_record != null) {
                            ImportLanguageDataHelper.deleteAll(mActivity)
                            for (importLanuageData in importDataResponse.lang_record!!) {
                                ImportLanguageDataHelper.saveImportLanguageData(
                                    importLanuageData,
                                    mActivity
                                )
                            }
                        }

                        if (importDataResponse.feedback_array != null) {
                            FeedbackDataHelper.deleteAll(mActivity)
                            for (feedbackData in importDataResponse.feedback_array!!) {
                                FeedbackDataHelper.saveFeedbackData(
                                    feedbackData,
                                    mActivity
                                )
                            }
                        }

                        if (importDataResponse.paper_array != null && importDataResponse.paper_array!!.isNotEmpty()) {
                            PaperDataHelper.deleteAll(mActivity)
                            SubQuestionDataHelper.deleteAll(mActivity)
                            OptionArrayDataHelper.deleteAll(mActivity)
                            TransOptionDataHelper.deleteAll(mActivity)
                            for (item in importDataResponse.paper_array!!) {
                                if (item is ArrayList<*>) {
                                    // Handle the case where item is an ArrayList
                                    for (innerItem in item) {
                                        if (innerItem is LinkedTreeMap<*, *>) {
                                            val paperResponse = convertToPaperResponse(innerItem)
                                            PaperDataHelper.savePaperData(
                                                paperResponse,
                                                mActivity
                                            )

                                            if (paperResponse.sub_questions != null) {

                                                for(subQues in paperResponse.sub_questions!!){

                                                    SubQuestionDataHelper.saveSubQuestionData(
                                                        subQues,
                                                        mActivity
                                                    )

                                                    if (subQues.option_array != null && subQues.option_array!!.isNotEmpty()){

                                                        for (option in subQues.option_array!!){
                                                            option.subQuestionId = subQues.squestion_id?: ""
                                                            OptionArrayDataHelper.saveOptionArrayData(
                                                                option,
                                                                mActivity
                                                            )
                                                        }
                                                    }

                                                    if (subQues.trans_option_array != null && subQues.trans_option_array!!.isNotEmpty()){
                                                        for (option in subQues.trans_option_array!!){
                                                            option.subQuestionId = subQues.squestion_id?: ""
                                                            TransOptionDataHelper.saveOptionArrayData(
                                                                option,
                                                                mActivity
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        when (loginAs) {

                            "Candidate" -> {
                                progressDialog.cancel()
                                Utility.replaceFragment(
                                    StudentImagesFragments("Start"),
                                    mActivity.supportFragmentManager,
                                    R.id.layout_root
                                )
                            }

                            "Assessor" -> {
                                progressDialog.cancel()
                                val intent = Intent(context, AssessorActivityMain::class.java)
                                startActivity(intent)
                            }
                        }
                    }
                } else {
                    progressDialog.cancel()
                    Log.d("TAG", "onResponse: " + response.message())
                }
            }

            override fun onFailure(call: Call<ImportAssessmentResponse?>, t: Throwable) {
                progressDialog.cancel()
                call.cancel()
            }
        })
    }

    private fun convertToPaperResponse(item: LinkedTreeMap<*, *>): PaperResponse {
        // Use Gson to convert LinkedTreeMap to JSON string, and then parse it as PaperResponse
        val gson = Gson()
        val json = gson.toJson(item)
        return gson.fromJson(json, PaperResponse::class.java)
    }

    private fun loginCheck(): Boolean {
        if (Utility.isNullOrEmpty(loginAs) || loginAs.equals("Select", ignoreCase = true)) {
            Utility.snackBar(
                binding.layoutRoot,
                resources.getString(R.string.select_login_type),
                1200,
                ContextCompat.getColor(mActivity, R.color.red)
            )
            return false
        } else if (Utility.isNullOrEmpty(binding.etLoginId.text.toString())) {
            Utility.snackBar(
                binding.layoutRoot,
                resources.getString(R.string.Please_enter_login_id),
                1200,
                ContextCompat.getColor(mActivity, R.color.red)
            )
            return false
        } else if (Utility.isNullOrEmpty(binding.edtPassword.text.toString())) {
            Utility.snackBar(
                binding.layoutRoot,
                resources.getString(R.string.please_enter_password),
                1200,
                ContextCompat.getColor(mActivity, R.color.red)
            )
            return false
        }
        return true
    }


    private fun setLoginAs() {
        val extraCableList = ArrayList<String>()
        extraCableList.add("Select")
        extraCableList.add("Candidate")
        extraCableList.add("Assessor")

        val adapter = ArrayAdapter(
            mActivity,
            R.layout.spinner_item_type, extraCableList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        binding.spinnerLoginAs.adapter = adapter
        binding.spinnerLoginAs.setSelection(2)
        binding.spinnerLoginAs.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    loginAs = binding.spinnerLoginAs.selectedItem.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

    }


}