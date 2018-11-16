package ru.cherryperry.precomputedtext

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_SLOW = 0
        private const val VIEW_TYPE_FAST = 1
    }

    var data: List<CharSequence> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }
    var fast: Boolean by Delegates.observable(false) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int) =
        if (fast) VIEW_TYPE_FAST else VIEW_TYPE_SLOW

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        if (viewType == VIEW_TYPE_FAST) NewViewHolder(parent) else OldViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    abstract class ViewHolder(
        viewGroup: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(R.layout.item, viewGroup, false)
    ) {

        protected val textView1: AppCompatTextView = itemView.findViewById(R.id.textView1)
        protected val textView2: AppCompatTextView = itemView.findViewById(R.id.textView2)
        protected val textView3: AppCompatTextView = itemView.findViewById(R.id.textView3)

        abstract fun bind(charSequence: CharSequence)
    }

    class OldViewHolder(
        viewGroup: ViewGroup
    ) : ViewHolder(viewGroup) {

        override fun bind(charSequence: CharSequence) {
            textView1.text = charSequence
            textView2.text = charSequence
            textView3.text = charSequence
        }
    }

    class NewViewHolder(
        viewGroup: ViewGroup
    ) : ViewHolder(viewGroup) {

        override fun bind(charSequence: CharSequence) {
            textView1.setTextFuture(
                PrecomputedTextCompat.getTextFuture(
                    charSequence,
                    textView1.textMetricsParamsCompat,
                    null
                )
            )
            textView2.setTextFuture(
                PrecomputedTextCompat.getTextFuture(
                    charSequence,
                    textView2.textMetricsParamsCompat,
                    null
                )
            )
            textView3.setTextFuture(
                PrecomputedTextCompat.getTextFuture(
                    charSequence,
                    textView3.textMetricsParamsCompat,
                    null
                )
            )
        }
    }
}