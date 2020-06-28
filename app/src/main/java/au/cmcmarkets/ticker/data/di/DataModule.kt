package au.cmcmarkets.ticker.data.di

import au.cmcmarkets.ticker.data.api.BitcoinApi
import au.cmcmarkets.ticker.data.repository.BitcoinChartRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideBitcoinChartRepo(bitcoinApi: BitcoinApi): BitcoinChartRepository {
        return BitcoinChartRepository(bitcoinApi)
    }

}