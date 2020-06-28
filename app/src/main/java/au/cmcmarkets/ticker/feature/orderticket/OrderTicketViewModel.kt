package au.cmcmarkets.ticker.feature.orderticket

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import au.cmcmarkets.ticker.data.repository.BitcoinChartRepository
import javax.inject.Inject


class OrderTicketViewModel @Inject constructor(val bitcoinChartRepository: BitcoinChartRepository) :
    ViewModel() {

    val buyPrice = ObservableField<String>()
    val sellPrice = ObservableField<String>()

    init {
        bitcoinChartRepository.bitcoinPricesChartData.observeForever {
            buyPrice.set("${it.bitcoinChartPrices.GBP.buy}")
            sellPrice.set("${it.bitcoinChartPrices.GBP.sell}")
        }
    }

}