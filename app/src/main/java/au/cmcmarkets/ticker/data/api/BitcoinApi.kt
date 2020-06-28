package au.cmcmarkets.ticker.data.api

import au.cmcmarkets.ticker.data.api.dto.BitcoinPricesChartDTO
import retrofit2.http.GET

interface BitcoinApi {

    @GET("/ticker")
    suspend fun bitcoinPrices(): BitcoinPricesChartDTO
}