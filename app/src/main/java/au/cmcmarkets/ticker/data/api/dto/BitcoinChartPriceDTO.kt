package au.cmcmarkets.ticker.data.api.dto

import java.math.BigDecimal

data class BitcoinChartPriceDTO(
    val `15m`: BigDecimal,
    val buy: BigDecimal,
    val last: BigDecimal,
    val sell: BigDecimal,
    val symbol: String
)