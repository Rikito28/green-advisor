package com.riski.greenadvisor.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MapsResponse(

	@field:SerializedName("data")
	val data: List<DataItemMaps>,

	@field:SerializedName("status")
	val status: String
)

@Parcelize
data class DataItemMaps(

	@field:SerializedName("foto")
	val foto: String,

	@field:SerializedName("foto1")
	val foto1: String,

	@field:SerializedName("foto2")
	val foto2: String,

	@field:SerializedName("nama_tanaman")
	val namaTanaman: String,

	@field:SerializedName("rekomendasi")
	val rekomendasi: String

): Parcelable
