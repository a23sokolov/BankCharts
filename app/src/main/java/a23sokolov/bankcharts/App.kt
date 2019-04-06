package a23sokolov.bankcharts

import a23sokolov.bankcharts.network.NetworkService
import android.app.Application

/**
 * Created by a.v.sokolov
 */
class App: Application() {


    override fun onCreate() {
        super.onCreate()
        NetworkService.init(this)
    }
}