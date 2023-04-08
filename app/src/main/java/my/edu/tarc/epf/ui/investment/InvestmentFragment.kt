package my.edu.tarc.epf.ui.investment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import my.edu.tarc.epf.R
import my.edu.tarc.epf.databinding.FragmentInvestmentBinding
import java.util.Calendar
import java.util.Calendar.*
import kotlin.math.pow

/**
 * A simple [Fragment] subclass.
 * Use the [InvestmentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InvestmentFragment : Fragment() {
    private var _binding: FragmentInvestmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInvestmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewAge.text = "0"
        binding.textViewAmountInvestment.text = "0"
        binding.buttonDOB.setOnClickListener {
            val dateDialogFragment = DateDialogFragment {
                year, month, day -> onDateSelected(year, month, day)
            }
            dateDialogFragment.show(parentFragmentManager, "DateDialog")

        }
        binding.buttonCalculate.setOnClickListener {
            if (binding.editTextBalanceAccount1.text.isEmpty()){
                binding.editTextBalanceAccount1.error = getString(R.string.value_required)
                return@setOnClickListener //terminate the program
            }
            var minBasicSaving = 0
            when (binding.textViewAge.text.toString().toFloat()) {
                in 16.0..20.0 -> {
                    minBasicSaving = 5000
                }
                in 21.0..25.0 -> {
                    minBasicSaving = 14000
                }
                in 26.0..30.0 -> {
                    minBasicSaving = 29000
                }
                in 31.0..35.0 -> {
                    minBasicSaving = 50000
                }
                in 36.0..40.0 -> {
                    minBasicSaving = 78000
                }
                in 41.0..45.0 -> {
                    minBasicSaving = 116000
                }
                in 46.0..50.0 -> {
                    minBasicSaving = 165000
                }
                in 51.0..55.0 -> {
                    minBasicSaving = 228000
                }
                else -> {
                    minBasicSaving = 0
                }
            }
            var excessOfBasic = binding.editTextBalanceAccount1.text.toString().toFloat() - minBasicSaving
            var amountOfInvestment = excessOfBasic * 0.3
            binding.textViewAmountInvestment.text = amountOfInvestment.toString()

        }
        binding.buttonReset.setOnClickListener {
            binding.editTextBalanceAccount1.setText("")
            binding.buttonDOB.text = getString(R.string.dob)
            binding.textViewAge.text = "0"
            binding.textViewAmountInvestment.text = "0"
        }
    }

    private fun onDateSelected(year: Int, month: Int, day: Int) {
        binding.buttonDOB.text = String.format("%02d/%02d/%d", day, month, year)
        val dob = getInstance()
        with(dob) {
            set(YEAR, year)
            set(MONTH, month)
            set(DAY_OF_MONTH, day)
        }
        val today = getInstance()
        val age = daysBetween(dob, today).div(365)
        binding.textViewAge.text = age.toString()
    }

    fun daysBetween(startDate: Calendar, endDate: Calendar): Long {
        var daysBetween: Long = 0
        val date = startDate.clone() as Calendar
        while (date.before(endDate)) {
            date.add(DAY_OF_MONTH, 1)
            daysBetween++
        }
        return  daysBetween
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class DateDialogFragment(val dateSetListener:(year: Int, month: Int, day: Int) -> Unit): DialogFragment(), DatePickerDialog.OnDateSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            val year = c.get(YEAR)
            val month = c.get(MONTH)
            val day = c.get(DAY_OF_MONTH)

            return DatePickerDialog(requireContext(), this, year, month, day)
        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            dateSetListener(year, month + 1, dayOfMonth)
        }

    }
}