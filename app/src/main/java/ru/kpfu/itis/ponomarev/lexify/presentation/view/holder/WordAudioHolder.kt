package ru.kpfu.itis.ponomarev.lexify.presentation.view.holder

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.ponomarev.lexify.databinding.ItemWordAudioBinding
import ru.kpfu.itis.ponomarev.lexify.presentation.model.DictionaryWordAudioModel

class WordAudioHolder(
    private val binding: ItemWordAudioBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: DictionaryWordAudioModel, context: Context) {
        binding.tvAttr.text = item.attributionText
        binding.tvDuration.text = item.duration.toString()

        if (URLUtil.isValidUrl(item.attributionUrl)) {
            binding.tvAttr.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(item.attributionUrl))
                context.startActivity(intent)
            }
        }
    }
}