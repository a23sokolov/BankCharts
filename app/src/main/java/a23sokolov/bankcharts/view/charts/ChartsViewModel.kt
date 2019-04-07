package a23sokolov.bankcharts.view.charts

import a23sokolov.bankcharts.network.Point
import a23sokolov.bankcharts.view.charts.ChartActivity.Companion.EXTRA_POINTS
import a23sokolov.bankcharts.view.common.BaseViewModel
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Created by a.v.sokolov
 */
class ChartsViewModel: BaseViewModel() {
    private val points = MutableLiveData<List<Point>>()
    fun getPoints(): LiveData<List<Point>> = points

    override fun init(args: Bundle?) {
        super.init(args)
        if (args == null) throw IllegalStateException("ChartsViewModel args should not be null")
        points.value = args.getParcelableArrayList(EXTRA_POINTS)
    }
}