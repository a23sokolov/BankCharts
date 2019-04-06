package a23sokolov.bankcharts

import a23sokolov.bankcharts.common.BaseViewModel
import a23sokolov.bankcharts.common.LoadingState
import a23sokolov.bankcharts.network.NetworkService
import a23sokolov.bankcharts.network.Point
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by a.v.sokolov
 */
class MainViewModel : BaseViewModel() {
    private val points = MutableLiveData<List<Point>>()
    fun getPoints(): LiveData<List<Point>> = points

    fun getPointsBtnClicked(pointCount: Int) {
        updateState(LoadingState.ofProgress(true))
        NetworkService.getBankApi().getChartPoints(count = pointCount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                updateState(LoadingState.ofProgress(false))
                points.value = it.response.points
            }, {
                updateState(LoadingState.ofError(it.message))
            })
            .also { subscribe(it) }
    }
}