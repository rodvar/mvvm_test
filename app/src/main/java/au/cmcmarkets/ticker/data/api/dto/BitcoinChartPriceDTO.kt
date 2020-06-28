package au.cmcmarkets.ticker.data.api.dto

import java.math.BigDecimal

data class BitcoinChartPriceDTO(
    val `15m`: BigDecimal = BigDecimal(0),
    val buy: BigDecimal,
    val last: BigDecimal = BigDecimal(0),
    val sell: BigDecimal,
    val symbol: String = ""
)