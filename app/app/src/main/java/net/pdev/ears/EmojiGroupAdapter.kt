package net.pdev.ears

import android.bluetooth.le.ScanResult
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.pdev.ears.databinding.EmojiPresetBinding

class EmojiGroup (val id: Int, val left: CharSequence, val right: CharSequence) {}

class EmojiGroupAdapter(
    private val items: List<EmojiGroup>,
    private val onClickListener: ((device: EmojiGroup) -> Unit)
) : RecyclerView.Adapter<EmojiGroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = EmojiPresetBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false
        )

        return ViewHolder(view, onClickListener)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    class ViewHolder(
        private val view: EmojiPresetBinding,
        private val onClickListener: ((device: EmojiGroup) -> Unit)
    ) : RecyclerView.ViewHolder(view.root) {

        fun bind(result: EmojiGroup) {
            view.groupId.text = result.id.toString()
            view.lEmoji.text = result.left
            view.rEmoji.text = result.right
            view.root.setOnClickListener { onClickListener.invoke(result) }
        }
    }
}