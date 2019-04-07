package a23sokolov.bankcharts.view

import a23sokolov.bankcharts.R
import a23sokolov.bankcharts.network.Point
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.point_item.view.*

/**
 * Created by a.v.sokolov
 */
class TableAdapter(
    val context: Context
) : RecyclerView.Adapter<RowTableHolder>() {

    private val inflater by lazy { LayoutInflater.from(context) }
    private val xPointRowName by lazy { context.getString(R.string.x_point) }
    private val yPointRowName by lazy { context.getString(R.string.y_point) }
    private val backroundOne by lazy { context.resources.getColor(R.color.table_back_1)}
    private val backroundTwo by lazy { context.resources.getColor(R.color.table_back_2)}

    private var points: List<DataToShow> = emptyList()

    fun setPoints(newpoints: List<Point>) {
        val header = if (newpoints.isEmpty()){
            emptyList()
        } else {
            listOf(DataToShow(xPointRowName, yPointRowName, backroundTwo))
        }
        this.points = header + newpoints.mapIndexed { index, point -> DataToShow(point.x, point.y, getColor(index)) }
        notifyDataSetChanged()
    }

    private fun getColor(index: Int): Int {
        return if (index % 2 == 0) {
            backroundOne
        } else {
            backroundTwo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowTableHolder {
        return RowTableHolder(inflater.inflate(R.layout.point_item, parent, false))
    }

    override fun getItemCount(): Int {
        return points.size
    }

    override fun onBindViewHolder(holder: RowTableHolder, position: Int) {
        holder.bind(points[position], position)
    }
}

class RowTableHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(point: DataToShow, position: Int) {
        if (position == 0) {
            itemView.indexPoint.text = ""
        } else {
            itemView.indexPoint.text = position.toString()
        }
        itemView.xPoint.text = point.firstRow
        itemView.yPoint.text = point.secondRow
        itemView.setBackgroundColor(point.backgroundColor)
    }
}

data class DataToShow(
    val firstRow: String,
    val secondRow: String,
    val backgroundColor: Int
)
