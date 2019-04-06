package a23sokolov.bankcharts.network

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by a.v.sokolov
 */
interface BankApi {
    @POST("mobws/json/pointsList")
    fun getChartPoints(@Body emptyRequest: EmptyRequest = EmptyRequest(), @Query("version") version: Float = 1.1F, @Query("count") count: Int): Single<BankResponse>
}

class EmptyRequest