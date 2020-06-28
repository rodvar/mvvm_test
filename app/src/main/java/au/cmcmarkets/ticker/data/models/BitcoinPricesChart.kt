package au.cmcmarkets.ticker.data.models

import au.cmcmarkets.ticker.data.api.dto.BitcoinPricesChartDTO

class BitcoinPricesChart(val bitcoinChartPrices: BitcoinPricesChartDTO) {


    override fun toString() = bitcoinChartPrices.toString()
}