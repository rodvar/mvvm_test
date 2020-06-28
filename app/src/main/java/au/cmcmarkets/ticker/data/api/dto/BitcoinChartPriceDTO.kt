package au.cmcmarkets.ticker.data.api.dto

data class BitcoinChartPriceDTO(
    val `15m`: Double,
    val buy: Double,
    val last: Double,
    val sell: Double,
    val symbol: String
)