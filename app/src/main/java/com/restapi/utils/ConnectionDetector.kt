@file:Suppress("DEPRECATION")

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import android.view.Window
import android.widget.Button
import com.restapi.R

class ConnectionDetector(private val context: Context) {

    private var dialog: Dialog? = null

    fun isConnectingToInternet(): Boolean {
        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.allNetworkInfo
        for (i in info.indices) {
            if (info[i].state == NetworkInfo.State.CONNECTED) {
                dismissDialog() // Dismiss the dialog if the internet is connected
                return true
            }
        }
        return false
    }

    fun connectionDetect(): Boolean {
        if (isConnectingToInternet()) {
            return true
        } else {
            dialog = Dialog(context)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setContentView(R.layout.connection_checker)
            val retry = dialog?.findViewById<Button>(R.id.buttonRetry)
            val connect = dialog?.findViewById<Button>(R.id.buttonConnect)
            dialog?.show()
            dialog?.setCancelable(false)

            retry?.setOnClickListener {
                isConnectingToInternet() // Check again if internet is connected
            }

            connect?.setOnClickListener {
                dismissDialog()
                context.startActivity(Intent(Settings.ACTION_SETTINGS))
            }

            return false
        }
    }

    private fun dismissDialog() {
        dialog?.dismiss()
        dialog = null
    }
}
