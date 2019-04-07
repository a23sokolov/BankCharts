package a23sokolov.bankcharts.view.charts

import a23sokolov.bankcharts.BuildConfig
import a23sokolov.bankcharts.R
import a23sokolov.bankcharts.view.TableAdapter
import a23sokolov.bankcharts.view.common.BaseActivity
import a23sokolov.bankcharts.view.common.InternalIntent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.content_chart.*
import kotlinx.android.synthetic.main.include_toolbar.*
import java.util.*


/**
 * Created by a.v.sokolov
 */
class ChartActivity : BaseActivity<ChartsViewModel>() {
    private val adapter = TableAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(a23sokolov.bankcharts.R.layout.activity_chart)
        setToolbar(this.getString(R.string.chart_activity_title), R.drawable.ic_back, toolbar)
        initRecyclerView()
        initViewModel(ViewModelProviders.of(this).get(ChartsViewModel::class.java))
    }

    private fun initRecyclerView() {
        tableContent.layoutManager = LinearLayoutManager(this)
        tableContent.adapter = adapter
        tableContent.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            ).also {
                it.setDrawable(this.resources.getDrawable(R.drawable.divider_decorator))
            })
    }

    private fun initViewModel(viewModel: ChartsViewModel) {
        afterViewModelInited(viewModel)
        viewModel.getPoints().observe(this, Observer {
            adapter.setPoints(it)
            updateChart(it)
        })
    }

    private fun updateChart(points: List<Entry>) {
        val set1 = LineDataSet(points, "Label")
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)
        chart.data = LineData(dataSets)
        chart.notifyDataSetChanged()
    }

    companion object {
        fun buildIntent(
            points: List<Entry>
        ): InternalIntent {
            val extras = Bundle().apply {
                putParcelableArrayList(EXTRA_POINTS, points as ArrayList<Entry>)
            }
            return InternalIntent(ChartActivity::class.java, extras = extras)
        }

        const val EXTRA_POINTS = "${BuildConfig.APPLICATION_ID}.extra.EXTRA_POINTS"
    }
}