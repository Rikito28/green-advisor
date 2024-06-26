package com.riski.greenadvisor.ui.detail.showlist

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riski.greenadvisor.R
import com.riski.greenadvisor.data.Preference
import com.riski.greenadvisor.data.response.DataLogin
import com.riski.greenadvisor.databinding.ActivityDetailShowListAnyPlantBinding
import com.riski.greenadvisor.ui.detail.adapter.DetailShowListAnyPlantAdapter
import com.riski.greenadvisor.ui.home.HomeViewModel
import com.riski.greenadvisor.ui.home.HomeViewModelFactory
import com.riski.greenadvisor.ui.home.adapter.AnyPlantAdapter
import com.riski.greenadvisor.ui.home.dataStore

class DetailShowListAnyPlantActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailShowListAnyPlantBinding
    private lateinit var detailShowAnyPlantViewModel: HomeViewModel
    private lateinit var adapter: DetailShowListAnyPlantAdapter
    private lateinit var user : DataLogin

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailShowListAnyPlantBinding.inflate(layoutInflater)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.detail_any_plant_list)
            setDisplayHomeAsUpEnabled(true)
        }

        homeViewModel1()
    }

    private fun homeViewModel1() {
        detailShowAnyPlantViewModel = ViewModelProvider(this, HomeViewModelFactory(Preference.getInstance(dataStore), this))[HomeViewModel::class.java]

        detailShowAnyPlantViewModel.getUser().observe(this) {
            user = DataLogin (
                it.name,
                it.token,
                true
            )
            setAnyPlant()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAnyPlant() {
        adapter = DetailShowListAnyPlantAdapter()
        binding.detailShowRecyclerAnyPlant.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.detailShowRecyclerAnyPlant.setHasFixedSize(true)
        binding.detailShowRecyclerAnyPlant.adapter = adapter
        detailShowAnyPlantViewModel.loadAnyPlant(user.token)
        detailShowAnyPlantViewModel.getDataAnyPlant.observe(this) {
            adapter.setAnyPlant(it)
            adapter.notifyDataSetChanged()
        }
    }

    @Suppress("DEPRECATION")
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}