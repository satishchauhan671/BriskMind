package com.brisk.assessment.common

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.location.LocationManager
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.brisk.assessment.BriskMindApplication
import com.brisk.assessment.R
import com.brisk.assessment.activities.LoginActivity
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Math.abs
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Random
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern


object Utility {
    private const val MY_PREFS_NAME = "MyPrefsFile"
    private val TAG = Utility::class.java.simpleName


    fun bitmapToBASE64(bitmap: Bitmap?): String {
        return if (bitmap != null) {
            val baos = ByteArrayOutputStream()
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageBytes = baos.toByteArray()
            Base64.encodeToString(imageBytes, Base64.DEFAULT)
        } else ""
    }

    fun holidayFormat(date: Date?): String? {
        var date1: String? = null
        try {
            //         SimpleDateFormat formatter1 = new SimpleDateFormat("EEEE, dd MMMM", Locale.ENGLISH);
            val formatter = SimpleDateFormat("EEEE, MMMM dd", Locale.ENGLISH)
            date1 = formatter.format(date)
        } catch (pe: java.lang.Exception) {
            pe.printStackTrace()
        }
        return date1
    }

    fun isValidEmailNew(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun isValidMobile(view: View?, mobile: String): Boolean {
        val check: Boolean
        val mobileRegex = "[6-9][0-9]{9}"
        check = mobile.matches(mobileRegex.toRegex())
        if (!check) {
            snackBar(view, "Mobile Number is not valid", 3000, Color.parseColor("#f32013"))
        }
        return check
    }


    fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }


    fun addFragment(fragment: Fragment?, fragmentManager: FragmentManager, resId: Int) {
        try {
            if (fragment != null) {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.add(resId, fragment).commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun replaceFragment(fragment: Fragment?, fragmentManager: FragmentManager, resId: Int) {
        if (fragment != null) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            //   fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(resId, fragment)
            // fragmentTransaction.commit();
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commitAllowingStateLoss()
        }
    }

    @JvmStatic
    fun replaceFragmentWithBundle(
        fragment: Fragment?,
        fragmentManager: FragmentManager,
        resId: Int,
        bundle: Bundle
    ) {
        if (fragment != null) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragment.arguments = bundle
            fragmentTransaction.replace(resId, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commitAllowingStateLoss()
        }
    }

    fun clearAllFragment(fragmentActivity: FragmentActivity) {
        val fm = fragmentActivity.supportFragmentManager // or 'getSupportFragmentManager();'
        val count = fm.backStackEntryCount
        for (i in 0 until count) {
            fm.popBackStack()
        }
    }

    fun isOnline(context: Context): Boolean {
        var connection = false
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            connection = netInfo != null && netInfo.isConnectedOrConnecting
            if (connection) {
                // INVENTIVE_WIFI device does not have internet access
                val wifiManager =
                    BriskMindApplication.mInstance.applicationContext?.getSystemService(
                        Context.WIFI_SERVICE
                    ) as WifiManager
                val ssid = wifiManager.connectionInfo.ssid
                if (ssid.contains("INVENTIA")) connection = false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return connection
    }

    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }


    fun saveBooleanPreference(key: String?, value: Boolean) {
        val editor: SharedPreferences.Editor = BriskMindApplication.mInstance.getSharedPreferences(
            MY_PREFS_NAME, Context.MODE_PRIVATE
        )!!.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun saveStringPreference(key: String?, value: String?) {
        val editor: SharedPreferences.Editor = BriskMindApplication.mInstance.getSharedPreferences(
            MY_PREFS_NAME, Context.MODE_PRIVATE
        )!!.edit()
        editor.putString(key, value)
        editor.apply()
    }


    fun getStringPreference(key: String?): String {
        return BriskMindApplication.mInstance.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
            ?.getString(key, null)!!
    }

    fun getBooleanPreference(key: String?): Boolean {
        return BriskMindApplication.mInstance?.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
            ?.getBoolean(key, false)!!
    }



    fun dateTimeToStringForUniqueId(date: Date?): String? {
        var date1: String? = null
        try {
            val formatter = SimpleDateFormat("dd-MM-yyyy-hh-mm-ss", Locale.ENGLISH)
            date1 = formatter.format(date)
        } catch (pe: Exception) {
            pe.printStackTrace()
        }
        return date1
    }

    fun setDownloadDate(strDate: String?): String? {
        var date1: String? = null
        var dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
        try {
            val varDate = dateFormat.parse(strDate)
            dateFormat = SimpleDateFormat("dd-MMM-yyyy")
            date1 = dateFormat.format(varDate)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date1
    }


    fun setDateInMM(strDate: String?): String? {
        var date1: String? = null
        var dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        try {
            val varDate = dateFormat.parse(strDate)
            dateFormat = SimpleDateFormat("dd-MM-yyyy")
            date1 = dateFormat.format(varDate)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date1
    }

    val currentDate: String?
        get() = dateStringWithTimeHrMinute(Date())

    val currentDateDay: String?
        get() = dayToString(Date())


    fun dayToString(date: Date?): String? {
        var date1: String? = null
        try {
            val formatter = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
            date1 = formatter.format(date)
        } catch (pe: Exception) {
            pe.printStackTrace()
        }
        return date1
    }



    val currentDateTime: String?
        get() = dateStringWithTime(Date())


    private fun dateStringWithTime(date: Date?): String? {
        var date1: String? = null
        try {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            date1 = formatter.format(date)
        } catch (pe: Exception) {
            pe.printStackTrace()
        }
        return date1
    }

    private fun dateStringWithTimeHrMinute(date: Date?): String? {
        var date1: String? = null
        try {
            val formatter = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.ENGLISH)
            date1 = formatter.format(date)
        } catch (pe: Exception) {
            pe.printStackTrace()
        }
        return date1
    }

    val date: String?
        get() {
            var dateVal = "";
            val sdf = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH)
            val cal = Calendar.getInstance()
            val date = cal.time
            if (setDownloadDate(sdf.format(date))!!.contains("Sept")) {
                dateVal = setDownloadDate(sdf.format(date))!!.replace("Sept", "Sep")
            } else {
                dateVal = setDownloadDate(sdf.format(date))!!
            }

            return dateVal
        }


    val dateInMM: String?
        get() {
            var dateVal: String
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            val cal = Calendar.getInstance()
            val date = cal.time
            dateVal = setDateInMM(sdf.format(date))!!

            return dateVal
        }


    @JvmStatic
    fun getIntegerValue(value: String?): Int {
        var i = 0
        try {
            i = value!!.toInt()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return i
    }


    fun getStringValue(value: String?): String? {
        var str = value
        try {
            if (str!!.isEmpty() || str.toLowerCase() == "null") str = null
        } catch (e: Exception) {
        }
        return str
    }

    fun getDoubleValue(value: String): Double {
        var value = value
        var i = 0.0
        try {
            value = value.trim { it <= ' ' }
            i = value.toDouble()
        } catch (e: Exception) {
        }
        return i
    }

    fun isNullOrEmpty(var0: String?): Boolean {
        return var0 == null || var0.trim { it <= ' ' }.isEmpty() || var0.trim { it <= ' ' }
            .equals("null", ignoreCase = true)
    }

    @JvmStatic
    fun snackBar(view: View?, msg: String?, duration: Int?, color: Int) {
        val sb = Snackbar.make(view!!, msg!!, duration!!)
        val sbView = sb.view
        sbView.setBackgroundColor(color)
        sb.show()
    }

    fun setPic(imagePath: String?, maxHeight: Int, maxWidth: Int) {
        try {
            var photoBm = getBitmap(imagePath)
            val bmOriginalWidth = photoBm!!.width
            val bmOriginalHeight = photoBm.height
            val originalWidthToHeightRatio = 1.0 * bmOriginalWidth / bmOriginalHeight
            val originalHeightToWidthRatio = 1.0 * bmOriginalHeight / bmOriginalWidth
            photoBm = getScaledBitmap(
                photoBm, bmOriginalWidth, bmOriginalHeight,
                originalWidthToHeightRatio, originalHeightToWidthRatio,
                maxHeight, maxWidth
            )
            val bytes = ByteArrayOutputStream()
            photoBm!!.compress(Bitmap.CompressFormat.PNG, 80, bytes)
            val fo = FileOutputStream(imagePath)
            fo.write(bytes.toByteArray())
            fo.close()
        } catch (se: Exception) {
        }
    }

    fun getScaledBitmap(
        bm: Bitmap?,
        bmOriginalWidth: Int,
        bmOriginalHeight: Int,
        originalWidthToHeightRatio: Double,
        originalHeightToWidthRatio: Double,
        maxHeight: Int,
        maxWidth: Int
    ): Bitmap? {
        var bm = bm
        if (bmOriginalWidth > maxWidth || bmOriginalHeight > maxHeight) {
            Log.v(
                TAG,
                String.format("RESIZING bitmap FROM %sx%s ", bmOriginalWidth, bmOriginalHeight)
            )
            bm = if (bmOriginalWidth > bmOriginalHeight) {
                scaleDeminsFromWidth(
                    bm,
                    maxWidth,
                    bmOriginalHeight,
                    originalHeightToWidthRatio
                )
            } else {
                scaleDeminsFromHeight(
                    bm,
                    maxHeight,
                    bmOriginalHeight,
                    originalWidthToHeightRatio
                )
            }
            Log.v(TAG, String.format("RESIZED bitmap TO %sx%s ", bm!!.width, bm.height))
        }
        return bm
    }

    private fun scaleDeminsFromHeight(
        bm: Bitmap?,
        maxHeight: Int,
        bmOriginalHeight: Int,
        originalWidthToHeightRatio: Double
    ): Bitmap? {
        var bm = bm
        val newHeight = Math.min(maxHeight, bmOriginalHeight)
        val newWidth = (newHeight * originalWidthToHeightRatio).toInt()
        bm = Bitmap.createScaledBitmap(bm!!, newWidth, newHeight, true)
        return bm
    }

    private fun scaleDeminsFromWidth(
        bm: Bitmap?,
        maxWidth: Int,
        bmOriginalWidth: Int,
        originalHeightToWidthRatio: Double
    ): Bitmap? {
        //scale the width
        var bm = bm
        val newWidth = Math.min(maxWidth, bmOriginalWidth)
        val newHeight = (newWidth * originalHeightToWidthRatio).toInt()
        bm = Bitmap.createScaledBitmap(bm!!, newWidth, newHeight, true)
        return bm
    }

    fun getBitmapByStringImage(base64: String?): Bitmap {
        val decodedString = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    fun getBitmap(path: String?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            bitmap = BitmapFactory.decodeFile(path, options)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }



    fun logout(context: Context) {
        try {

            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            (context as AppCompatActivity).finishAffinity()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun publishStatus(
        progressDialog: ProgressDialog,
        totalCount: Int,
        uploadedCount: Int,
        failedCount: Int,
        uploadingType: String,
        failedMessage: String
    ) {
        Handler(Looper.getMainLooper()).post {
            progressDialog.setMessage(
                """
    Uploading $uploadingType......
    
    Total Count    :$totalCount
    Upload Count :$uploadedCount
    Failed Count  :$failedCount
    Failed Message:$failedMessage
    """.trimIndent()
            )
        }
    }


    fun getBooleanValue(value: String?): Boolean {
        var i = false
        try {
            i = java.lang.Boolean.parseBoolean(value)
        } catch (e: Exception) {
        }
        return i
    }


    fun deleteImages(context: Context): Boolean {
        try {
            val dir = File(
                Environment.getExternalStorageDirectory()
                    .toString() + "/Android/media/com.inventia.ugo_mici/" + context.resources.getString(
                    R.string.app_name
                )
            )
            if (dir.isDirectory) {
                for (file in dir.listFiles()) {
                    file.delete()
                }
                return true
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }


    fun isGPSEnabled(context: Context): Boolean {
        var gpsEnabled = false
        var networkEnabled = false
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
            e.message
        }
        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (e: Exception) {
            e.message
        }
        return gpsEnabled || networkEnabled
    }


    fun getRotation(photoPath: String?): Int {
        var ei: ExifInterface? = null
        var rotation = 0
        try {
            ei = ExifInterface(photoPath!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var orientation = 0
        if (ei != null) {
            orientation = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
        }
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotation = 90
            ExifInterface.ORIENTATION_ROTATE_180 -> rotation = 180
            ExifInterface.ORIENTATION_ROTATE_270 -> rotation = 270
            ExifInterface.ORIENTATION_TRANSVERSE -> rotation = -90
        }
        return rotation
    }

    fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }


    fun toTitleCase(str: String?): String? {
        if (isNullOrEmpty(str)) {
            return null
        }
        var space = true
        val builder = StringBuilder(str)
        val len = builder.length
        for (i in 0 until len) {
            val c = builder[i]
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c))
                    space = false
                }
            } else if (Character.isWhitespace(c)) {
                space = true
            } else {
                builder.setCharAt(i, Character.toLowerCase(c))
            }
        }
        return builder.toString()
    }



    //--------------------------------------------------------------------------------------------------------
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getIMEINumber(): String? {
        var myuniqueID: String? = ""
        try {
            val myversion = Integer.valueOf(Build.VERSION.SDK_INT)
            if (myversion < 23) {
                val manager = BriskMindApplication.mInstance!!.getApplicationContext().getSystemService(
                    Context.WIFI_SERVICE
                ) as WifiManager
                val info = manager.connectionInfo
                myuniqueID = info.macAddress
                if (myuniqueID == null) {
                    val mngr = BriskMindApplication.mInstance!!
                        .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    if (ActivityCompat.checkSelfPermission(
                            BriskMindApplication.mInstance!!,
                            Manifest.permission.READ_PHONE_STATE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return ""
                    }
                    myuniqueID = mngr.deviceId
                }
            } else if (myversion > 23 && myversion < 29) {
                val mngr = BriskMindApplication.mInstance!!
                    .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                if (ActivityCompat.checkSelfPermission(
                        BriskMindApplication.mInstance!!,
                        Manifest.permission.READ_PHONE_STATE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return ""
                }
                myuniqueID = mngr.deviceId
            } else {
                myuniqueID = Settings.Secure.getString(
                    BriskMindApplication.mInstance!!.getContentResolver(),
                    Settings.Secure.ANDROID_ID
                )
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return myuniqueID
    }


    @Throws(IOException::class)
    fun drawableFromUrl(url: String?): Drawable? {


        var bmp: Bitmap? = null

        var x: Bitmap? = null
        val thread = Thread {
            try {

                val aryURI = URL(url)
                val conn = aryURI.openConnection()
                val `is` = conn.getInputStream()
                bmp = BitmapFactory.decodeStream(`is`)

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        thread.start()

        return BitmapDrawable(bmp)
    }


    fun isAirplaneModeOn(context: Context): Boolean {
        return try {
            Settings.System.getInt(
                context.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON, 0
            ) != 0
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            false
        }
    }

    fun isInternetEnabled(context: Context): Boolean {
        //we are connected to a network
        var mobileDataEnabled = false // Assume disabled
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            val cmClass = Class.forName(cm.javaClass.name)
            val method = cmClass.getDeclaredMethod("getMobileDataEnabled")
            method.isAccessible = true // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = method.invoke(cm) as Boolean
        } catch (e: java.lang.Exception) {
            // Some problem accessible private API
            return false
            // TODO do whatever error handling you want here
        }
        return mobileDataEnabled
    }


    fun getTimeHour(hour: Int): Long {
        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = hour
        cal[Calendar.MINUTE] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.timeInMillis
    }


    fun getCompleteAddressString(context: Context?, LATITUDE: Double, LONGITUDE: Double): String? {
        var strAdd = ""
        val geocoder = Geocoder(context!!, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                val returnedAddress = addresses[0]
                val strReturnedAddress = java.lang.StringBuilder("")
                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                strAdd = strReturnedAddress.toString().trim { it <= ' ' }
                Log.e("Location1 : ", strReturnedAddress.toString())
            } else {
                Log.e("Location2 : ", "No Address returned!")
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.e("Location3 : ", "Canont get Address!")
        }
        return strAdd
    }


    fun getCurrenTime(): String? {
        return timeToString(Date())
    }

    fun timeToString(date: Date?): String? {
        var date1: String? = null
        try {
            val formatter = SimpleDateFormat("hh:mm aaa", Locale.ENGLISH)
            date1 = formatter.format(date)
        } catch (pe: java.lang.Exception) {
            pe.printStackTrace()
        }
        return date1
    }

    fun getRandom(maxNumber: Int): Long {
        val random = Random()
        return random.nextInt(maxNumber).toLong()
    }



    fun printReceipt(printData: String, mBluetoothSocket: BluetoothSocket?): Boolean {
        var printData = printData
        var Is_Bill_Printed = false
        try {
            if (!isNullOrEmpty(getStringPreference("Payment_Printer")) && getStringPreference("Payment_Printer").equals(
                    "Other",
                    ignoreCase = true
                )
            )
                printData = printData.replace("\n", "\r")
            if (mBluetoothSocket != null && mBluetoothSocket.isConnected) {
                val os = mBluetoothSocket.outputStream
                val format = byteArrayOf(27, 33, 0)
                format[2] = (0x8 or format[2].toInt()).toByte()
                val format1 = byteArrayOf(27, 33, 0)
                //width
                format[2] = (0x20 or format[2].toInt()).toByte()
                os.write(format1)
                os.write(printData.toByteArray())
                Is_Bill_Printed = true
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return Is_Bill_Printed
    }




    @JvmStatic
    fun getIntPreference(key: String?): Int {
        return BriskMindApplication.mInstance?.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
            ?.getInt(key, 0)!!
    }



    fun dateDifference(date1: String, date2: String): Long {
        val dateInitial = date1
        val dateFinal = date2

        val date1: Date
        val date2: Date
        val dates = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH)
        date1 = dates.parse(dateInitial)
        date2 = dates.parse(dateFinal)
        val duration: Long = abs(date1.time - date2.time)

        val days = TimeUnit.MILLISECONDS.toDays(duration)
        val hours = TimeUnit.MILLISECONDS.toHours(duration)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(duration)

        return minutes
    }

    fun stringToBase64(inputString: String): String {
        val encodedBytes = Base64.encode(inputString.toByteArray(), Base64.DEFAULT)
        return String(encodedBytes)
    }

    fun showSnackBar(view: View, message: String) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackBar.show()
    }

    fun progressDialog(context: Context): Dialog {
        val dialog = Dialog(context)
        val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        dialog.setContentView(inflate)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        return dialog
    }

    fun progressDialogWithMessage(context: Context, message: String): Dialog {
        val dialog = Dialog(context)
        val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        dialog.setContentView(inflate)
        dialog.setCancelable(false)
        val dialogMessage = dialog.findViewById<TextView>(R.id.progressText)
        dialogMessage.text = message
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}