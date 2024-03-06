package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemWordDefinitionBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordDefinitionModel

class WordDefinitionHolder(
    private val binding: ItemWordDefinitionBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: DictionaryWordDefinitionModel, context: Context) {
        val span = SpannableStringBuilder()
        val labels = mutableListOf<String>()
        if (item.partOfSpeech != null) {
            labels.add(item.partOfSpeech)
        }
        labels.addAll(item.labels.map { it.text })
        if (labels.isNotEmpty()) {
            span.append(labels.joinToString(", "))
            span.setSpan(
                UnderlineSpan(),
                0,
                span.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
            span.setSpan(
                ForegroundColorSpan(context.getColor(R.color.gray)),
                0,
                span.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
            span.append(" ")
        }
        span.append(item.text)
        binding.tvText.text = span
    }
}