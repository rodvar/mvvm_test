package au.cmcmarkets.ticker.data.models

import au.cmcmarkets.ticker.data.api.dto.BitcoinChartPriceDTO
import au.cmcmarkets.ticker.data.api.dto.BitcoinPricesChartDTO
import java.math.BigDecimal

class BitcoinPricesChart(private val bitcoinChartPrices: BitcoinPricesChartDTO) {

    companion object {
        const val UK_KEY = "GBP"
    }

    fun buy(key: String) = find(key).buy

    fun sell(key: String) = find(key).sell

    fun spread(key: String): BigDecimal = find(key).let { it.buy.subtract(it.sell) }

    fun unitsFor(key: String, amount: BigDecimal): BigDecimal = find(key).let { amount.div(it.buy) }

    fun amountFor(key: String, units: BigDecimal): BigDecimal =
        find(key).let { units.multiply(it.buy) }

    private fun find(key: String): BitcoinChartPriceDTO {
        return try {
            when (key) {
                UK_KEY -> this.bitcoinChartPrices.GBP!!
                else -> null!!
            }
        } catch (e: Exception) {
            throw PriceUnavailableException()
        }
    }

    override fun toString() = bitcoinChartPrices.toString()

    class PriceUnavailableException : Exception()
}