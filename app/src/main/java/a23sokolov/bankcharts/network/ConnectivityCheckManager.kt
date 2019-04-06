package a23sokolov.bankcharts.network

import a23sokolov.bankcharts.R
import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by a.v.sokolov
 */
class ConnectionCheckManager(private val context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun checkConnected() {
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo == null || !networkInfo.isConnected) {
            throw NoNetworkConnectionException(context.getString(R.string.error_no_internet_connection))
        }
    }

}

class NoNetworkConnectionException(msg: String) : Exception(msg)