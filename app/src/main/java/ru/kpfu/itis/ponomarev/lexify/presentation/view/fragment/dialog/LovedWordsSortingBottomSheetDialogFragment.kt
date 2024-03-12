package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.DialogLovedWordsSortingBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.sorting.LovedWordsSorting

class LovedWordsSortingBottomSheetDialogFragment(
    private val applySorting: (LovedWordsSorting) -> Unit,
    private val currentSorting: LovedWordsSorting,
) : BottomSheetDialogFragment() {

    private var _binding: DialogLovedWordsSortingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogLovedWordsSortingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sortingAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout, LovedWordsSorting.entries.map {
            getString(it.displayNameId)
        })
        sortingAdapter.setDropDownViewResource(R.layout.spinner_dropdown)
        binding.spSorting.adapter = sortingAdapter
        binding.spSorting.setSelection(currentSorting.ordinal)
        binding.spSorting.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                applySorting(LovedWordsSorting.entries[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                parent?.setSelection(0)
            }

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}