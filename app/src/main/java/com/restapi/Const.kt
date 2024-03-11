package com.restapi

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.restapi.ui.services.ApiService
import kotlinx.coroutines.CoroutineExceptionHandler
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InterruptedIOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.TimeUnit


object Const {
//    private const val BASE_URL = "https://api.slingacademy.com/"
    private const val BASE_URL = "https://reqres.in/"
    private const val TIME = 30
    const val ID = "id"

    private var retrofit: Retrofit? = null

    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
    }

    val okHttpClient by lazy {
        OkHttpClient().newBuilder()
            .connectTimeout(TIME.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIME.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIME.toLong(), TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)

                when  {
                    response.code == 404 && response.body == null -> {

                        val notFoundMessage = "Data Not Found"

                        Log.e("API_ERROR", "Response Code: 404, Message: $notFoundMessage")

                        return@Interceptor response.newBuilder()
                            .body(okhttp3.ResponseBody.create(null, notFoundMessage))
                            .code(404)
                            .message(notFoundMessage)
                            .build()
                    }

                    response.code == 204  -> {

                        val successfulMessage = "Delete Successful"

                        return@Interceptor response.newBuilder()
                            .body(okhttp3.ResponseBody.create(null, successfulMessage))
                            .code(204)
                            .message(successfulMessage)
                            .build()
                    }

                    response.code == 401 && response.body == null -> {
                        val unauthorizedMessage = "Unauthorized"
                        Log.e("API_ERROR", "Response Code: 401, Message: $unauthorizedMessage")
                        return@Interceptor response.newBuilder()
                            .body(okhttp3.ResponseBody.create(null, unauthorizedMessage))
                            .code(401)
                            .message(unauthorizedMessage)
                            .build()
                    }

                    else -> {
                        return@Interceptor response
                    }
                }
            })
//           .addNetworkInterceptor(Interceptor { chain ->
//                val original: Request = chain.request()
//                val requestBuilder: Request.Builder = original.newBuilder()
//                if (Prefesmanager.containKey(Prefesmanager.Token)!!) {
//                    requestBuilder.addHeader("Authorization","Bearer "+ Prefesmanager.readStringPrefVal(Prefesmanager.Token).toString())
//                }
//                val request: Request = requestBuilder.build()
//                chain.proceed(request)
//            })
            .build()
    }

    fun create(): ApiService {

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        Log.e("TAG", "create: ", )
        return retrofit!!.create(ApiService::class.java)
    }

    fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private var progressDialog: Dialog? = null

    fun showProgressDialog(context: Context) {
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

    fun hideProgressDialog() {
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

    @SuppressLint("SimpleDateFormat")
    fun currentDateTime(format: String): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat(format)
        return formatter.format(time)
    }

    val scopeExceptionHandling: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { _, exception ->
            hideProgressDialog()
            when (exception) {
                is HttpException -> {
                    when (exception.code()) {
                        404 -> {
//                            val jsonObject = JSONObject(
//                                exception.response()?.errorBody()!!.charStream().readText()
//                            ).getString("message")
                            Log.e("TAG", ":${exception.message} " )
                        }
                    }
                }

                is InterruptedIOException ->
                    Log.e("TAG", ":${exception.message} " )

                else -> {
                    Log.e("TAG", ":${exception.message} " )
                }
            }
        }
    }
}