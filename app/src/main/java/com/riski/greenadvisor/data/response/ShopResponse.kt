package com.riski.greenadvisor.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ShopResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("status")
	val status: String
)

@Parcelize
data class DataItem(

	@field:SerializedName("foto")
	val foto: String,

	@field:SerializedName("kondisi")
	val kondisi: String,

	@field:SerializedName("harga")
	val harga: String,

	@field:SerializedName("referensi")
	val referensi: String,

	@field:SerializedName("toko")
	val toko: String,

	@field:SerializedName("nama_barang")
	val namaBarang: String,

	@field:SerializedName("pemesanan")
	val pemesanan: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("foto1")
	val foto1: String,

	@field:SerializedName("foto2")
	val foto2: String
): Parcelable
