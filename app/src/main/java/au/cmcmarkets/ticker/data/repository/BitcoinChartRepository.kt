package au.cmcmarkets.ticker.data.repository

import au.cmcmarkets.ticker.data.api.BitcoinApi
import au.cmcmarkets.ticker.data.models.BitcoinPricesChart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject

class BitcoinChartRepository @Inject constructor(private val bitcoinApi: BitcoinApi) {

    private var bitcoinPricesChart: BitcoinPricesChart? = null

    suspend fun getChart(): BitcoinPricesChart = if (bitcoinPricesChart == null)
        this.updateChartAsync().await()
    else
        bitcoinPricesChart!!

    suspend fun updateChartAsync(): Deferred<BitcoinPricesChart> {
        return GlobalScope.async {
            bitcoinPricesChart = BitcoinPricesChart(bitcoinApi.bitcoinPrices())
            bitcoinPricesChart!!
        }
    }
}