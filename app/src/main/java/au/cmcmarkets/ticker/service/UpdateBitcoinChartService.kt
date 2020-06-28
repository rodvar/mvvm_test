package au.cmcmarkets.ticker.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import au.cmcmarkets.ticker.data.repository.BitcoinChartRepository
import dagger.android.AndroidInjection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateBitcoinChartService : Service() {

    companion object {
        const val UPDATE_TIME = 15000L
    }

    @Inject
    lateinit var bitcoinChartRepository: BitcoinChartRepository

    private lateinit var loopThread: Thread
    private var job: Job? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        AndroidInjection.inject(this)
        Log.d("Service", "Service started")
        this.job = GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                bitcoinChartRepository.updateChartAsync().await().let {
                    Log.d("Service", bitcoinChartRepository.getChart().toString())
                }
                Log.d("Service", "Service updated repository")
                Thread.sleep(UPDATE_TIME)
            }
        }
    }

    override fun onDestroy() {
        job?.cancel()
        Log.d("Service", "Service finished")
        super.onDestroy()
    }

}