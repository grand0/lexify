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
                    BackgroundColorSpan(context.getColor(R.color.primary)),
                    it,
                    it + (item.word?.length ?: 0),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
                )
                span.setSpan(
                    ForegroundColorSpan(context.getColor(R.color.on_primary)),
                    it,
                    it + (item.word?.length ?: 0),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
                )
            }
        binding.tvText.text = span
        
        val sb = StringBuilder()
        val commaSeparator = context.getString(R.string.comma_separator)
        if (item.title != null) sb.append(item.title).append(commaSeparator)
        if (item.author != null) sb.append(item.author).append(commaSeparator)
        if (item.year != null) sb.append(item.year)
        val attrText = sb.toString().removeSuffix(commaSeparator)
        binding.tvAttr.text = attrText

        copyableText = if (attrText.isNotEmpty()) {
            context.getString(R.string.example_copyable_with_attr, item.text, attrText)
        } else {
            context.getString(R.string.example_copyable_without_attr, item.text)
        }

        if (URLUtil.isValidUrl(item.url)) {
            binding.tvAttr.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(item.url))
                context.startActivity(intent)
            }
        }
    }
}