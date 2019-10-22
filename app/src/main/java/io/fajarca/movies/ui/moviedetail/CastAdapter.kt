package io.fajarca.movies.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.fajarca.movies.data.local.entity.Cast
import io.fajarca.movies.databinding.ItemCastBinding

class CastAdapter(private var casts: List<Cast>, var listener: OnCastPressed) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCastBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = casts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(casts[position], listener)

    class ViewHolder(private var binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Cast, listener: OnCastPressed) {
            binding.cast = model
            binding.root.setOnClickListener { listener.onCastPressed(model) }
            binding.executePendingBindings()
        }
    }

    fun refreshData(casts: List<Cast>) {
        this.casts = casts
        notifyDataSetChanged()
    }

    interface OnCastPressed {
        fun onCastPressed(cast: Cast)
    }
}
