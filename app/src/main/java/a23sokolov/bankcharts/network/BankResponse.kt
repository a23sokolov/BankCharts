package a23sokolov.bankcharts.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by a.v.sokolov
 */
class BankResponse(
    val result: Int?,
    val response: Response
)

class Response(
    val result: Int?,
    val message: String?,
    val points: List<Point>?
)

@Parcelize
class Point(
    val x: String,
    val y: String
): Parcelable