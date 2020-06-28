package au.cmcmarkets.ticker.feature.orderticket

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import au.cmcmarkets.ticker.data.models.BitcoinPricesChart
import au.cmcmarkets.ticker.data.repository.BitcoinChartRepository
import java.math.BigDecimal
import javax.inject.Inject


class OrderTicketViewModel @Inject constructor(private val bitcoinChartRepository: BitcoinChartRepository) :
    ViewModel() {

    private var lastPricesChart: BitcoinPricesChart? = null

    val buyPrice = ObservableField<String>()
    val sellPrice = ObservableField<String>()
    val spread = ObservableField<String>()
    val confirmAvailable = ObservableField<Boolean>()

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

    private fun postPrices(it: BitcoinPricesChart) {
        this.lastPricesChart = it
        confirmAvailable.set(true)
        buyPrice.set("${it.buy(BitcoinPricesChart.UK_KEY)}")
        sellPrice.set("${it.sell(BitcoinPricesChart.UK_KEY)}")
        spread.set("${it.spread(BitcoinPricesChart.UK_KEY)}")
    }

    private fun postDataNotAvailable() {
        confirmAvailable.set(false)
        buyPrice.set("N/A")
        sellPrice.set("N/A")
        spread.set("N/A")
    }

    fun unitsAt(amount: BigDecimal): BigDecimal? =
        lastPricesChart?.unitsFor(BitcoinPricesChart.UK_KEY, amount)

    fun amountAt(units: BigDecimal): BigDecimal? =
        lastPricesChart?.amountFor(BitcoinPricesChart.UK_KEY, units)

}