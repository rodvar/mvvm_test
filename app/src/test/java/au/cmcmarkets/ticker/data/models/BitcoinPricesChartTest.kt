package au.cmcmarkets.ticker.data.models

import au.cmcmarkets.ticker.data.api.dto.BitcoinChartPriceDTO
import au.cmcmarkets.ticker.data.api.dto.BitcoinPricesChartDTO
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class BitcoinPricesChartTest {
    @Before
    @Throws(Exception::class)
    fun setUp() {
    }

    @Test
    fun `span is buy - sell price`() {
        val bitcoinPricesChart = BitcoinPricesChart(
            BitcoinPricesChartDTO(
                GBP = BitcoinChartPriceDTO(
                    buy = BigDecimal(1), sell = BigDecimal(0)
                )
            )
        )
        assert(bitcoinPricesChart.spread(BitcoinPricesChart.UK_KEY) == BigDecimal(1))
    }

    @Test
    fun `span is buy - sell price 2`() {
        val bitcoinPricesChart = BitcoinPricesChart(
            BitcoinPricesChartDTO(
                GBP = BitcoinChartPriceDTO(
                    buy = BigDecimal(124.32), sell = BigDecimal(123.32)
                )
            )
        )
        assert(
            bitcoinPricesChart.spread(BitcoinPricesChart.UK_KEY) == BigDecimal(124.32).subtract(
                BigDecimal(123.32)
            )
        )
    }
}