package com.findme.app.responses

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SimilarImage(
    @SerializedName("similar_images" ) var similarImages : ArrayList<SimilarImages> = arrayListOf()
)



data class SimilarImages(

    @SerializedName("image_url"        ) var imageUrl: String? = null,
    @SerializedName("similarity_score" ) var similarityScore: String? = null

): Parcelable {
    // Parcelable implementation
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageUrl)
        parcel.writeString(similarityScore)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SimilarImages> {
        override fun createFromParcel(parcel: Parcel): SimilarImages {
            return SimilarImages(parcel)
        }

        override fun newArray(size: Int): Array<SimilarImages?> {
            return arrayOfNulls(size)
        }
    }
}
