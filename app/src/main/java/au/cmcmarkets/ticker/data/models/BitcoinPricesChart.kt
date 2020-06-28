package au.cmcmarkets.ticker.data.models

import au.cmcmarkets.ticker.data.api.dto.BitcoinPricesChartDTO
import java.math.BigDecimal

class BitcoinPricesChart(private val bitcoinChartPrices: BitcoinPricesChartDTO) {

    companion object {
        const val UK_KEY = "GBP"
    }

    fun buy(key: String) = try {
        when (key) {
            UK_KEY -> this.bitcoinChartPrices.GBP!!.buy
            else -> BigDecimal(0) // TODO
        }
    } catch (e: Exception) {
        throw PriceUnavailableException()
    }

    fun sell(key: String) = try {
        when (key) {
            UK_KEY -> this.bitcoinChartPrices.GBP!!.sell
            else -> BigDecimal(0) // TODO
        }
    } catch (e: Exception) {
        throw PriceUnavailableException()
    }

    fun spread(key: String): BigDecimal = try {
        when (key) {
            UK_KEY -> this.bitcoinChartPrices.GBP!!.let { it.buy.subtract(it.sell) }
            else -> BigDecimal(0) // TODO
        }
    } catch (e: Exception) {
        throw PriceUnavailableException()
    }

    override fun toString() = bitcoinChartPrices.toString()

    class PriceUnavailableException : Exception()
}