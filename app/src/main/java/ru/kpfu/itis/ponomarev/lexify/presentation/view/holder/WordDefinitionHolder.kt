package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.R
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemWordDefinitionBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordDefinitionModel

class WordDefinitionHolder(
    private val binding: ItemWordDefinitionBinding,
    private val onXrefClickListener: (String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    var copyableText: String = ""
    var item: DictionaryWordDefinitionModel? = null

    fun bindItem(item: DictionaryWordDefinitionModel, context: Context) {
        copyableText = item.text
        this.item = item

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
                ForegroundColorSpan(context.getColor(R.color.medium_emphasis)),
                0,
                span.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
            span.append(" ")
        }
        span.append(item.text)
        item.xrefs.forEach { xref ->
            val index = span.indexOf(xref)
            if (index != -1) {
                span.setSpan(
                    object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            onXrefClickListener(xref)
                        }
                        override fun updateDrawState(ds: TextPaint) {
                            ds.isUnderlineText = true
                        }
                    },
                    index,
                    index + xref.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
                )
            }
        }

        binding.tvText.text = span
        binding.tvText.movementMethod = LinkMovementMethod.getInstance()
    }
}