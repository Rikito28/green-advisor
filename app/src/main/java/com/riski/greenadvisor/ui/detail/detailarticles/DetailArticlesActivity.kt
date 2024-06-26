package com.riski.greenadvisor.ui.detail.detailarticles

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.riski.greenadvisor.R
import com.riski.greenadvisor.data.greetings.ArticlesData
import com.riski.greenadvisor.databinding.ActivityDetailArticlesBinding

class DetailArticlesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticlesBinding

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailArticlesBinding.inflate(layoutInflater)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = getString(R.string.detail_articles)
            setDisplayHomeAsUpEnabled(true)
        }

        setArticles()

    }


    @Suppress("DEPRECATION")
    private fun setArticles() {
        val detail = intent.getParcelableExtra<ArticlesData>(EXTRA_ARTICLES)
        if (detail != null) {
            binding.detailArticlesJudul.text = detail.judul
            binding.detailArticlesTime.text = detail.tahun
            binding.detailArticlesDeskripsi.text = detail.deskripsi
            Glide.with(this)
                .load(detail.imageId)
                .into(binding.detailArticlesImage)
            }
        }


    @Suppress("DEPRECATION")
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_ARTICLES = "articles"
    }
}