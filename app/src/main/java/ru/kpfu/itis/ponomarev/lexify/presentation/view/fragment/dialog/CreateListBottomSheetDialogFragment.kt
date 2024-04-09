package ru.kpfu.itis.ponomarev.lexify.presentation.view.fragment.dialog

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.DialogCreateListBinding
import java.util.concurrent.CompletableFuture

class CreateListBottomSheetDialogFragment(
    private val createList: (String) -> CompletableFuture<Boolean>,
) : BottomSheetDialogFragment() {

    private var _binding: DialogCreateListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCreateListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.etName.setOnEditorActionListener { v, actionId, event ->
            if (v.text.isNotBlank() && (event?.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE)) {
                val name = v.text.toString()
                createList(name).thenAccept { success ->
                    if (success) {
                        dismiss()
                    } else {
                        binding.etName.error = getString(R.string.list_already_exists)
                    }
                }
            }
            true
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}