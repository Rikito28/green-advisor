package com.riski.greenadvisor.ui.detail.detailcamera

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.riski.greenadvisor.R
import com.riski.greenadvisor.data.Preference
import com.riski.greenadvisor.data.response.DataItemCamera
import com.riski.greenadvisor.data.response.DataLogin
import com.riski.greenadvisor.databinding.ActivityDetailCameraDetectBinding
import com.riski.greenadvisor.ui.camera.CameraViewModel
import com.riski.greenadvisor.ui.camera.CameraViewModelFactory
import com.riski.greenadvisor.ui.home.HomeViewModel
import com.riski.greenadvisor.ui.home.HomeViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
class DetailCameraDetectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailCameraDetectBinding
    private lateinit var cameraViewModel: CameraViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var user: DataLogin
    private lateinit var dataItemCamera: DataItemCamera

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailCameraDetectBinding.inflate(layoutInflater)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = getString(R.string.detail_camera_detect)
            setDisplayHomeAsUpEnabled(true)
        }
        cameraViewModel1()
        homeViewModel1()

        dataItemCamera = intent.getParcelableExtra<DataItemCamera>(EXTRA_CAMERA)!!

        binding.detailCameraPlantImage2.setOnClickListener { clickImage(binding.detailCameraPlantImage2) }
        binding.detailCameraPlantImage3.setOnClickListener { clickImage(binding.detailCameraPlantImage3) }
        binding.detailCameraPlantImage4.setOnClickListener { clickImage(binding.detailCameraPlantImage4) }

    }

    private fun homeViewModel1() {
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(Preference.getInstance(dataStore), this))[HomeViewModel::class.java]

        homeViewModel.getUser().observe(this) {
            user = DataLogin(
                it.name,
                it.token,
                true
            )
            setDetectCamera()
        }
    }

    private fun cameraViewModel1() {
        cameraViewModel = ViewModelProvider(this, CameraViewModelFactory(Preference.getInstance(dataStore), this))[CameraViewModel::class.java]

    }

    private fun clickImage(imageView: ImageView) {
        when (imageView) {
            binding.detailCameraPlantImage2 -> loadImage(dataItemCamera.foto)
            binding.detailCameraPlantImage3 -> loadImage(dataItemCamera.foto1)
            binding.detailCameraPlantImage4 -> loadImage(dataItemCamera.foto2)
        }
    }

    private fun setDetectCamera() {
        Glide.with(this)
            .load(dataItemCamera.foto)
            .into(binding.detailCameraPlantImage)
        Glide.with(this)
            .load(dataItemCamera.foto)
            .into(binding.detailCameraPlantImage2)
        Glide.with(this)
            .load(dataItemCamera.foto1)
            .into(binding.detailCameraPlantImage3)
        Glide.with(this)
            .load(dataItemCamera.foto2)
            .into(binding.detailCameraPlantImage4)
        binding.detailCameraPlantName.text = dataItemCamera.namaTanaman
        binding.detailCameraPlantLatin.text = dataItemCamera.namaLatin
        binding.family.text = dataItemCamera.family
        binding.kingdom.text = dataItemCamera.kingdom
        binding.cameraIklim.text = dataItemCamera.iklim
        binding.habitat.text = dataItemCamera.habitat
        binding.persebaran.text = dataItemCamera.persebaran
        binding.anyEkologi.text = dataItemCamera.ekologi
        binding.konservasi.text = dataItemCamera.konservasi
    }

    private fun loadImage(imageUrl: String?) {
        Glide.with(this)
            .load(imageUrl)
            .into(binding.detailCameraPlantImage)
    }

    @Suppress("DEPRECATION")
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_CAMERA = "extra_camera"
    }
}