package com.riski.greenadvisor.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.riski.greenadvisor.R
import com.riski.greenadvisor.data.Preference
import com.riski.greenadvisor.data.api.ApiConfig
import com.riski.greenadvisor.data.greetings.ArticlesData
import com.riski.greenadvisor.data.response.AnyPlantResponse
import com.riski.greenadvisor.data.response.DataAnyItem
import com.riski.greenadvisor.data.response.DataLogin
import com.riski.greenadvisor.utils.ArticlesDataList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val preference: Preference) : ViewModel() {
    private val _response = MutableLiveData<Boolean>()
    val response: LiveData<Boolean> = _response

    private val _getDataAnyPlant = MutableLiveData<List<DataAnyItem>>()
    val getDataAnyPlant : LiveData<List<DataAnyItem>> = _getDataAnyPlant

    private val _plantCareList = MutableLiveData<List<Int>>()
    val plantCareList: LiveData<List<Int>>
        get() = _plantCareList

    private val _articlesList = MutableLiveData<List<ArticlesData>>()
    val articlesList: LiveData<List<ArticlesData>>
    get() = _articlesList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        loadImages()
        loadArticles()

    }

    fun getUser(): LiveData<DataLogin> {
        return preference.getLogin().asLiveData()
    }

    private fun loadImages() {
        val images = listOf(R.drawable.plant_care1, R.drawable.plant_care2)
        _plantCareList.value = images
    }

    private fun loadArticles() {
        val articlesData = ArticlesDataList.getArticles()
        _articlesList.value = articlesData
    }

    fun loadAnyPlant(token: String) {
        _loading.value = true

        val api =ApiConfig.getApiElevation().getAllPlant("Bearer $token")
        api.enqueue(object : Callback<AnyPlantResponse> {
            override fun onResponse(
                call: Call<AnyPlantResponse>,
                response: Response<AnyPlantResponse>
            ) { _loading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _getDataAnyPlant.value = response.body()?.data
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<AnyPlantResponse>, t: Throwable) {
             _loading.value = false
             Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

    }

    companion object {
        private const val TAG = "AnyPlantViewModel"
    }
}