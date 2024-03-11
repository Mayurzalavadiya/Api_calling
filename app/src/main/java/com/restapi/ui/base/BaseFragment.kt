package com.restapi.ui.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.restapi.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.text.SimpleDateFormat
import java.util.Calendar

open class BaseFragment : Fragment() {

    companion object {
        const val MY_PREF = ""
        const val IS_LOGIN = "IS_LOGIN"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(
            MY_PREF, Context.MODE_PRIVATE
        )
    }

//    protected val scope by lazy { CoroutineScope(Dispatchers.Main) + scopeExceptionHandling }


    protected val scopeExceptionHandling: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { _, exception ->
//            hideProgressDialog()
            when (exception) {
                is HttpException -> {
                    when (exception.code()) {
                        404 -> {
                            val jsonObject = JSONObject(
                                exception.response()?.errorBody()!!.charStream().readText()
                            ).getString("message")
                            showMessage(requireActivity(), jsonObject) }
                    }
                }

                is InterruptedIOException ->
                    showMessage(requireActivity(), "${exception.message}")

                else -> {
                    showMessage(requireActivity(), exception.message.toString())

                }
            }
        }
    }


    fun runMainThread(unit: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            unit()
        }
    }


    protected fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    protected fun moveToNextScreen(context: Context, nextClass: Class<*>) {
        val intent = Intent(context, nextClass)
        context.startActivity(intent)
    }

    @SuppressLint("SimpleDateFormat")
    fun currentDateTime(format: String): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat(format)
        return formatter.format(time)
    }


    protected fun showProgressDialog(context: Context) {
        try {
            hideProgressDialog()
            val dialog = Dialog(context)
            progressDialog = dialog
            dialog.setCancelable(false)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.custom_progress_view)
            if (dialog.window != null) {
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            dialog.show()
        } catch (_: Exception) {
        }
    }

    private var progressDialog: Dialog? = null

    protected fun hideProgressDialog() {
        try {
            if (progressDialog != null) {
                if (progressDialog!!.isShowing) {
                    progressDialog!!.dismiss()
                }
            }

        } catch (_: Exception) {
        }
        progressDialog = null
    }


    fun savePrefsVal(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    fun savePrefsVal(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor?.putInt(key, value)
        editor?.apply()
    }

    fun savePrefsVal(key: String, value: Float) {
        val editor = sharedPreferences.edit()
        editor?.putFloat(key, value)
        editor?.apply()
    }

    protected fun savePrefsVal(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    protected fun readBooleanPrefsVal(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    protected fun readStringPrefVal(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    protected fun readBooleanPrefVal(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    protected fun readFloatPrefVal(key: String): Float {
        return sharedPreferences.getFloat(key, 0.0F)
    }

    protected fun readIntPrefVal(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    protected fun containKey(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    protected fun removeKey(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    protected fun removeAllKey() {
        sharedPreferences.edit().clear().apply()
    }


    // no internet message
    protected fun showNoInternetAvailable(activity: Activity) {
        val snackBar = Snackbar.make(
            activity.findViewById(android.R.id.content),
            "No internet available",
            Snackbar.LENGTH_LONG
        )
        val textView =
            snackBar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.RED)
        snackBar.show()
    }

    private fun capitalize(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true
        var phrase = ""
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c)
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase += c
        }
        return phrase
    }

    protected fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else capitalize(manufacturer) + " " + model
    }

}