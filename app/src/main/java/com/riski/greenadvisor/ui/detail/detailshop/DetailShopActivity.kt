package com.riski.greenadvisor.ui.detail.detailshop

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.riski.greenadvisor.R
import com.riski.greenadvisor.data.response.DataItem
import com.riski.greenadvisor.databinding.ActivityDetailShopBinding

class DetailShopActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailShopBinding
    private lateinit var dataItem: DataItem

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailShopBinding.inflate(layoutInflater)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = getString(R.string.detail_shop_name)
            setDisplayHomeAsUpEnabled(true)
        }

        dataItem = intent.getParcelableExtra(EXTRA_SHOP) ?: return

        setDetailShop()

        binding.detailShopImage2.setOnClickListener { clickImage(binding.detailShopImage2) }
        binding.detailShopImage3.setOnClickListener { clickImage(binding.detailShopImage3) }
        binding.detailShopImage4.setOnClickListener { clickImage(binding.detailShopImage4) }
    }

    private fun clickImage(imageView: ImageView) {
        when (imageView) {
            binding.detailShopImage2 -> loadImage(dataItem.foto)
            binding.detailShopImage3 -> loadImage(dataItem.foto1)
            binding.detailShopImage4 -> loadImage(dataItem.foto2)
        }
    }

    @Suppress("DEPRECATION")
    private fun setDetailShop() {
            Glide.with(this)
                .load(dataItem.foto)
                .into(binding.detailShopImage)
            Glide.with(this)
                .load(dataItem.foto)
                .into(binding.detailShopImage2)
            Glide.with(this)
                .load(dataItem.foto1)
                .into(binding.detailShopImage3)
            Glide.with(this)
                .load(dataItem.foto2)
                .into(binding.detailShopImage4)
            binding.detailShopName.text = dataItem.namaBarang
            binding.detailShopPrice.text = dataItem.harga
            binding.detailShopKondisi.text = dataItem.kondisi
            binding.detailShopMinPem.text = dataItem.pemesanan
            binding.detailShopToko.text = dataItem.toko
            binding.detailShopDeskripsi.text = dataItem.deskripsi

            binding.detailShopBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dataItem.referensi))
                startActivity(intent)
            }
    }

    private fun loadImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .into(binding.detailShopImage)
    }

    @Suppress("DEPRECATION")
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_SHOP = "DetailShop"
    }
}