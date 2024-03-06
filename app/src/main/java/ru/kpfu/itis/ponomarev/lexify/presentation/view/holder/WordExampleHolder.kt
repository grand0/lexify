package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemWordExampleBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordExampleModel

class WordExampleHolder(
    private val binding: ItemWordExampleBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: DictionaryWordExampleModel, context: Context) {
        binding.tvText.text = item.text
        val sb = StringBuilder()
        if (item.title != null) sb.append(item.title).append(", ")
        if (item.author != null) sb.append(item.author).append(", ")
        if (item.year != null) sb.append(item.year)
        binding.tvAttr.text = sb.toString().removeSuffix(", ")

        if (URLUtil.isValidUrl(item.url)) {
            binding.tvAttr.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(item.url))
                context.startActivity(intent)
            }
        }
    }
}