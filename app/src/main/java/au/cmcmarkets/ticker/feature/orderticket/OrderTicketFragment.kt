package au.cmcmarkets.ticker.feature.orderticket

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import au.cmcmarkets.ticker.core.di.viewmodel.ViewModelFactory
import au.cmcmarkets.ticker.databinding.FragmentOrderTicketBinding
import au.cmcmarkets.ticker.service.UpdateBitcoinChartService
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class OrderTicketFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: OrderTicketViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(OrderTicketViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOrderTicketBinding.inflate(inflater)
        binding.viewModel = this.viewModel
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().startService(Intent(this.context, UpdateBitcoinChartService::class.java))
    }

    override fun onPause() {
        requireActivity().stopService(Intent(this.context, UpdateBitcoinChartService::class.java))
        super.onPause()
    }
}

