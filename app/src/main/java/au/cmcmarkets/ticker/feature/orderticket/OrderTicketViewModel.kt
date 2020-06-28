package au.cmcmarkets.ticker.feature.orderticket

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import au.cmcmarkets.ticker.data.models.BitcoinPricesChart
import au.cmcmarkets.ticker.data.repository.BitcoinChartRepository
import javax.inject.Inject


class OrderTicketViewModel @Inject constructor(private val bitcoinChartRepository: BitcoinChartRepository) :
    ViewModel() {

    val buyPrice = ObservableField<String>()
    val sellPrice = ObservableField<String>()
    val spread = ObservableField<String>()
    val error = ObservableField<String>()

    init {
        bitcoinChartRepository.bitcoinPricesChartData.observeForever {
            if (it == null) {
                postDataNotAvailable()
            } else try {
                buyPrice.set("${it.buy(BitcoinPricesChart.UK_KEY)}")
                sellPrice.set("${it.sell(BitcoinPricesChart.UK_KEY)}")
                spread.set("${it.spread(BitcoinPricesChart.UK_KEY)}")
            } catch (e: BitcoinPricesChart.PriceUnavailableException) {
                postDataNotAvailable()
            }
        }
    }

    private fun postDataNotAvailable() {
        buyPrice.set("N/A")
        sellPrice.set("N/A")
        spread.set("N/A")
    }

}