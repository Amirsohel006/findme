package com.findme.app.modules.frame.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.findme.app.R
import com.findme.app.databinding.RowListrectangleThreeBinding
import com.findme.app.modules.frame.`data`.model.ListrectangleThreeRowModel
import kotlin.Int
import kotlin.collections.List

class ListrectangleThreeAdapter(
  var list: List<ListrectangleThreeRowModel>
) : RecyclerView.Adapter<ListrectangleThreeAdapter.RowListrectangleThreeVH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowListrectangleThreeVH {
    val
        view=LayoutInflater.from(parent.context).inflate(R.layout.row_listrectangle_three,parent,false)
    return RowListrectangleThreeVH(view)
  }

  override fun onBindViewHolder(holder: RowListrectangleThreeVH, position: Int) {
    val listrectangleThreeRowModel = ListrectangleThreeRowModel()
    // TODO uncomment following line after integration with data source
    // val listrectangleThreeRowModel = list[position]
    holder.binding.listrectangleThreeRowModel = listrectangleThreeRowModel
  }

  override fun getItemCount(): Int = 8
  // TODO uncomment following line after integration with data source
  // return list.size

  public fun updateData(newData: List<ListrectangleThreeRowModel>) {
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
      item: ListrectangleThreeRowModel
    ) {
    }
  }

  inner class RowListrectangleThreeVH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    val binding: RowListrectangleThreeBinding = RowListrectangleThreeBinding.bind(itemView)
  }
}
