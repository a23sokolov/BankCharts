package a23sokolov.bankcharts.network

import com.google.gson.annotations.SerializedName

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
    @SerializedName("points") val pointsStr: List<PointStr>?
)

class PointStr(
    val x: String,
    val y: String
)