package a23sokolov.bankcharts.view

import a23sokolov.bankcharts.network.NetworkService
import a23sokolov.bankcharts.view.charts.ChartActivity
import a23sokolov.bankcharts.view.common.BaseViewModel
import a23sokolov.bankcharts.view.common.LoadingState
import com.github.mikephil.charting.data.Entry
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by a.v.sokolov
 */
class MainViewModel : BaseViewModel() {

    fun getPointsBtnClicked(fieldValue: String) {
        val pointsCount: Int
        try {
            pointsCount = fieldValue.toInt()
        } catch (e: NumberFormatException) {
            updateState(LoadingState.ofError("Write only Integer for counts"))
            return
        }
        updateState(LoadingState.ofProgress(true))
        NetworkService.getBankApi().getChartPoints(count = pointsCount)
            .subscribeOn(Schedulers.io())
            .map { response ->
                when {
                    response.result == -100 -> throw IllegalParamsException(response.response.message ?: "Illegal params problem")
                    response.result == -1 -> throw AnotherBackendProblem(response.response.message ?: "AnotherBackend problem")
                    response.result == 0 -> {
                        response.response.pointsStr!!
                            .map { Entry(it.x.toFloat(), it.y.toFloat()) }
                            .sortedBy { it.x }
                            .toList()
                    }
                    else -> throw SomeException(response.response.message ?: "Something going wrong, try later")
                }
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

class IllegalParamsException(msg: String) : Exception(msg)
class SomeException(msg: String) : Exception(msg)
class AnotherBackendProblem(msg: String) : Exception(msg)