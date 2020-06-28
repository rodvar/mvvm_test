package au.cmcmarkets.ticker.service.di

import au.cmcmarkets.ticker.service.UpdateBitcoinChartService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceModule {

    @ContributesAndroidInjector
    abstract fun contributeUppdateChartService(): UpdateBitcoinChartService

}