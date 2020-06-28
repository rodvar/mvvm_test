package au.cmcmarkets.ticker.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import au.cmcmarkets.ticker.data.api.BitcoinApi
import au.cmcmarkets.ticker.data.models.BitcoinPricesChart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject

class BitcoinChartRepository @Inject constructor(private val bitcoinApi: BitcoinApi) {

    private var bitcoinPricesChart: BitcoinPricesChart? = null
    val bitcoinPricesChartData = MutableLiveData<BitcoinPricesChart?>()

    suspend fun getChart(): BitcoinPricesChart? = if (bitcoinPricesChart == null)
        this.updateChartAsync().await() ?: null
    else
        bitcoinPricesChart!!

    suspend fun updateChartAsync(): Deferred<BitcoinPricesChart?> {
        return GlobalScope.async {
            try {
                bitcoinPricesChart = BitcoinPricesChart(bitcoinApi.bitcoinPrices())
                bitcoinPricesChartData.postValue(bitcoinPricesChart)
                bitcoinPricesChart!!
            } catch (e: Exception) {
                Log.e(javaClass.simpleName, "Failed to update chart", e)
                bitcoinPricesChartData.postValue(null)
                null
            }
        }
    }
}