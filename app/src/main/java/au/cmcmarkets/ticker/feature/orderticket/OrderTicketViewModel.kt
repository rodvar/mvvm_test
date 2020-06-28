package au.cmcmarkets.ticker.feature.orderticket

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import au.cmcmarkets.ticker.data.models.BitcoinPricesChart
import au.cmcmarkets.ticker.data.repository.BitcoinChartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject


class OrderTicketViewModel @Inject constructor(private val bitcoinChartRepository: BitcoinChartRepository) :
    ViewModel() {

    companion object {
        const val NOT_AVAILABLE = "N/A"
    }

    private var lastPricesChart: BitcoinPricesChart? = null

    val buyPrice = ObservableField<String>()
    val sellPrice = ObservableField<String>()
    val spread = ObservableField<String>()
    val confirmAvailable = ObservableField<Boolean>(false)
    val retryEnabled = ObservableField<Boolean>(false)
    val retryVisible = ObservableField<Boolean>(false)
    val retryFailed = ObservableField<Boolean>(false)

    init {
        bitcoinChartRepository.bitcoinPricesChartData.observeForever {
            if (it == null) {
                postDataNotAvailable()
            } else try {
                postPrices(it)
            } catch (e: BitcoinPricesChart.PriceUnavailableException) {
                postDataNotAvailable()
            }
        }
    }

    fun reloadPrices() {
        this.retryFailed.set(false)
        this.retryEnabled.set(false)
        GlobalScope.launch(Dispatchers.IO) { bitcoinChartRepository.updateChartAsync() }
    }

    private fun postPrices(it: BitcoinPricesChart) {
        this.lastPricesChart = it
        confirmAvailable.set(true)
        retryVisible.set(false)
        buyPrice.set("${it.buy(BitcoinPricesChart.UK_KEY)}")
        sellPrice.set("${it.sell(BitcoinPricesChart.UK_KEY)}")
        spread.set("${it.spread(BitcoinPricesChart.UK_KEY)}")
    }

    private fun postDataNotAvailable() {
        this.retryFailed.set(true)
        confirmAvailable.set(false)
        retryEnabled.set(true)
        retryVisible.set(true)
        buyPrice.set(NOT_AVAILABLE)
        sellPrice.set(NOT_AVAILABLE)
        spread.set(NOT_AVAILABLE)
    }

    fun unitsAt(amount: BigDecimal): BigDecimal? =
        lastPricesChart?.unitsFor(BitcoinPricesChart.UK_KEY, amount)

    fun amountAt(units: BigDecimal): BigDecimal? =
        lastPricesChart?.amountFor(BitcoinPricesChart.UK_KEY, units)

}