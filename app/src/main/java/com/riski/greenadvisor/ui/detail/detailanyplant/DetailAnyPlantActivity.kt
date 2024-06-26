package com.riski.greenadvisor.ui.detail.detailanyplant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.riski.greenadvisor.R
import com.riski.greenadvisor.data.response.DataAnyItem
import com.riski.greenadvisor.databinding.ActivityDetailAnyPlantBinding

class DetailAnyPlantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAnyPlantBinding
    private lateinit var dataItemAny: DataAnyItem

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailAnyPlantBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = getString(R.string.name_title_any_plant)
            setDisplayHomeAsUpEnabled(true)
        }

        dataItemAny = intent.getParcelableExtra(EXTRA_ANY) ?: return

        setDetailAnyPlant()

        binding.detailAnyPlantImage2.setOnClickListener { clickImage(binding.detailAnyPlantImage2) }
        binding.detailAnyPlantImage3.setOnClickListener { clickImage(binding.detailAnyPlantImage3) }
        binding.detailAnyPlantImage4.setOnClickListener { clickImage(binding.detailAnyPlantImage4) }

    }

    private fun clickImage(imageView: ImageView) {
        when (imageView) {
            binding.detailAnyPlantImage2 -> loadImage(dataItemAny.foto)
            binding.detailAnyPlantImage3 -> loadImage(dataItemAny.foto1)
            binding.detailAnyPlantImage4 -> loadImage(dataItemAny.foto2)
        }
    }

    private fun setDetailAnyPlant() {
    Glide.with(this)
        .load(dataItemAny.foto)
        .into(binding.detailAnyPlantImage)
    Glide.with(this)
        .load(dataItemAny.foto)
        .into(binding.detailAnyPlantImage2)
    Glide.with(this)
        .load(dataItemAny.foto1)
        .into(binding.detailAnyPlantImage3)
    Glide.with(this)
        .load(dataItemAny.foto2)
        .into(binding.detailAnyPlantImage4)
    binding.detailAnyPlantName.text = dataItemAny.namaTanaman
    binding.detailAnyPlantLatin.text = dataItemAny.namaLatin
    binding.family.text = dataItemAny.family
    binding.kingdom.text = dataItemAny.kingdom
    binding.anyIklim.text = dataItemAny.iklim
    binding.habitat.text = dataItemAny.habitat
    binding.persebaran.text = dataItemAny.persebaran
    binding.anyEkologi.text = dataItemAny.ekologi
    binding.konservasi.text = dataItemAny.konservasi
    }

    private fun loadImage(imageUrl: String?) {
        Glide.with(this)
            .load(imageUrl)
            .into(binding.detailAnyPlantImage)
    }

    @Suppress("DEPRECATION")
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_ANY = "Detail_Any_Plant"
    }
}