package a23sokolov.bankcharts.view

import a23sokolov.bankcharts.network.NetworkService
import a23sokolov.bankcharts.network.PointStr
import a23sokolov.bankcharts.view.charts.ChartActivity
import a23sokolov.bankcharts.view.common.BaseViewModel
import a23sokolov.bankcharts.view.common.LoadingState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.Entry
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by a.v.sokolov
 */
class MainViewModel : BaseViewModel() {
    private val points = MutableLiveData<List<PointStr>>()
    fun getPoints(): LiveData<List<PointStr>> = points

    fun getPointsBtnClicked(pointCount: Int) {
        updateState(LoadingState.ofProgress(true))
        NetworkService.getBankApi().getChartPoints(count = pointCount)
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.response.pointsStr!!
                    .map { Entry(it.x.toFloat(), it.y.toFloat()) }
                    .sortedBy { it.x }
                    .toList()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ points ->
                updateState(LoadingState.ofProgress(false))
                startIntent(ChartActivity.buildIntent(points))
            }, {
                updateState(LoadingState.ofError(it.message))
            })
            .also { subscribe(it) }
    }
}