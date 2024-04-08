package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.webkit.URLUtil
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemWordExampleBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordExampleModel
import ru.kpfu.itis.ponomarev.lexify.util.indexesOf

class WordExampleHolder(
    private val binding: ItemWordExampleBinding,
) : RecyclerView.ViewHolder(binding.root) {

    var copyableText = ""

    fun bindItem(item: DictionaryWordExampleModel, context: Context) {
        val span = SpannableString(item.text)
        item.text.indexesOf(item.word)
            .forEach {
                span.setSpan(
                    BackgroundColorSpan(context.getColor(R.color.surface_inverse)),
                    it,
                    it + (item.word?.length ?: 0),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
                )
                span.setSpan(
                    ForegroundColorSpan(context.getColor(R.color.on_surface_inverse)),
                    it,
                    it + (item.word?.length ?: 0),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
                )
            }
        binding.tvText.text = span
        
        val sb = StringBuilder()
        if (item.title != null) sb.append(item.title).append(", ")
        if (item.author != null) sb.append(item.author).append(", ")
        if (item.year != null) sb.append(item.year)
        val attrText = sb.toString().removeSuffix(", ")
        binding.tvAttr.text = attrText

        copyableText = item.text + (if (attrText.isNotEmpty()) " ($attrText)" else "")

        if (URLUtil.isValidUrl(item.url)) {
            binding.tvAttr.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(item.url))
                context.startActivity(intent)
            }
        }
    }
}