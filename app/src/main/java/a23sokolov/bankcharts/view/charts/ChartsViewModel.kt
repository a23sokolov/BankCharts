package a23sokolov.bankcharts.view.charts

import a23sokolov.bankcharts.view.charts.ChartActivity.Companion.EXTRA_POINTS
import a23sokolov.bankcharts.view.common.BaseViewModel
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.Entry

/**
 * Created by a.v.sokolov
 */
class ChartsViewModel : BaseViewModel() {
    private val points = MutableLiveData<List<Entry>>()
    fun getPoints(): LiveData<List<Entry>> = points

    override fun init(args: Bundle?) {
        super.init(args)
        if (args == null) throw IllegalStateException("ChartsViewModel args should not be null")
        points.value = args.getParcelableArrayList(EXTRA_POINTS)
    }
}