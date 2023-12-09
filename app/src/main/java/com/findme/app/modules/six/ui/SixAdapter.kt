package com.findme.app.modules.six.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.findme.app.R
import com.findme.app.databinding.RowSixBinding
import com.findme.app.modules.six.`data`.model.SixRowModel
import com.findme.app.responses.SimilarImage
import com.findme.app.responses.SimilarImages
import kotlin.Int
import kotlin.collections.List

class SixAdapter(
    private val list: List<SimilarImages>
) : RecyclerView.Adapter<SixAdapter.RowSixVH>() {
  private var clickListener: OnItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowSixVH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_six,parent,false)
    return RowSixVH(view)
  }

  override fun onBindViewHolder(holder: RowSixVH, position: Int) {
   return holder.bindView(list[position])
  }

  override fun getItemCount(): Int {
  return list.size
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
   val image:ImageView=itemView.findViewById(R.id.imageRectangleTwelve)

    // Define the corner radius in pixels (converted from dp)
    val cornerRadiusInPixels = 15 // Change to your dimension resource

    // Create a RequestOptions object with the RoundedCorners transformation
    val requestOptions = RequestOptions()
      .transform(RoundedCorners(cornerRadiusInPixels))
    fun bindView(posModel:SimilarImages){
      Glide.with(itemView.context)
        .load(posModel.imageUrl) // Replace with your image URL or resource ID
        .apply(requestOptions)
        .into(image)
    }
  }
}
