package com.riski.greenadvisor.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CameraResponse(

	@field:SerializedName("data")
	val data: List<DataItemCamera>,

	@field:SerializedName("nama_tanaman")
	val namaTanaman: String,

	@field:SerializedName("status")
	val status: Boolean
)

@Parcelize
data class DataItemCamera(

	@field:SerializedName("habitat")
	val habitat: String,

	@field:SerializedName("referensi")
	val referensi: String,

	@field:SerializedName("kingdom")
	val kingdom: String,

	@field:SerializedName("persebaran")
	val persebaran: String,

	@field:SerializedName("foto")
	val foto: String,

	@field:SerializedName("ekologi")
	val ekologi: String,

	@field:SerializedName("konservasi")
	val konservasi: String,

	@field:SerializedName("nama_tanaman")
	val namaTanaman: String,

	@field:SerializedName("iklim")
	val iklim: String,

	@field:SerializedName("nama_latin")
	val namaLatin: String,

	@field:SerializedName("rekomendasi")
	val rekomendasi: String,

	@field:SerializedName("foto1")
	val foto1: String,

	@field:SerializedName("family")
	val family: String,

	@field:SerializedName("foto2")
	val foto2: String
):Parcelable
