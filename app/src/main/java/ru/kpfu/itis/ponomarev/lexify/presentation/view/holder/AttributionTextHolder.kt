package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemAttributionTextBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryAttributionTextModel

class AttributionTextHolder(
    private val binding: ItemAttributionTextBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: DictionaryAttributionTextModel, context: Context) {
        binding.tvText.text = item.text
        if (URLUtil.isValidUrl(item.url)) {
            binding.tvText.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(item.url))
                context.startActivity(intent)
            }
        }
    }
}
