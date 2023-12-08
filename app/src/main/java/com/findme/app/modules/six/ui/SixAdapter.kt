package com.findme.app.modules.six.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.findme.app.R
import com.findme.app.databinding.RowSixBinding
import com.findme.app.modules.six.`data`.model.SixRowModel
import kotlin.Int
import kotlin.collections.List

class SixAdapter(
  var list: List<SixRowModel>
) : RecyclerView.Adapter<SixAdapter.RowSixVH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowSixVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_six,parent,false)
    return RowSixVH(view)
  }

  override fun onBindViewHolder(holder: RowSixVH, position: Int) {
    val sixRowModel = SixRowModel()
    // TODO uncomment following line after integration with data source
    // val sixRowModel = list[position]
    holder.binding.sixRowModel = sixRowModel
  }

  override fun getItemCount(): Int = 8
  // TODO uncomment following line after integration with data source
  // return list.size

  public fun updateData(newData: List<SixRowModel>) {
    list = newData
    notifyDataSetChanged()
  }

  fun setOnItemClickListener(clickListener: OnItemClickListener) {
    this.clickListener = clickListener
  }

  interface OnItemClickListener {
    fun onItemClick(
      view: View,
      position: Int,
      item: SixRowModel
    ) {
    }
  }

  inner class RowSixVH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val binding: RowSixBinding = RowSixBinding.bind(itemView)
  }
}
