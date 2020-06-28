package au.cmcmarkets.ticker.feature.orderticket

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import au.cmcmarkets.ticker.core.di.viewmodel.ViewModelFactory
import au.cmcmarkets.ticker.databinding.FragmentOrderTicketBinding
import au.cmcmarkets.ticker.service.UpdateBitcoinChartService
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_order_ticket.*
import javax.inject.Inject


class OrderTicketFragment : DaggerFragment() {

    private lateinit var unitsTextWatcher: TextWatcher
    private lateinit var amountTextWatcher: TextWatcher

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        amountTextWatcher = this.amountInput.doOnTextChanged { text, start, count, after ->
            unitsInput.removeTextChangedListener(unitsTextWatcher)
            try {
                unitsInput.setText(viewModel.unitsAt(text.toString().toBigDecimal()).toString())
            } catch (e: Exception) {
                Log.e(javaClass.simpleName, "Failed to update unit", e)
            } finally {
                unitsInput.addTextChangedListener(unitsTextWatcher)
            }
        }
        unitsTextWatcher = this.unitsInput.doOnTextChanged { text, start, count, after ->
            amountInput.removeTextChangedListener(amountTextWatcher)
            try {
                amountInput.setText(viewModel.amountAt(text.toString().toBigDecimal()).toString())
            } catch (e: Exception) {
                Log.e(javaClass.simpleName, "Failed to update unit", e)
            } finally {
                amountInput.addTextChangedListener(amountTextWatcher)
            }
        }
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

