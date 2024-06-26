@file:Suppress("DEPRECATION")

package com.riski.greenadvisor.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.riski.greenadvisor.BetaActivity
import com.riski.greenadvisor.data.Preference
import com.riski.greenadvisor.data.greetings.ArticlesData
import com.riski.greenadvisor.data.response.DataLogin
import com.riski.greenadvisor.databinding.FragmentHomeBinding
import com.riski.greenadvisor.ui.detail.showlist.DetailShowListAnyPlantActivity
import com.riski.greenadvisor.ui.detail.showlist.DetailShowListArticlesActivity
import com.riski.greenadvisor.ui.home.adapter.AnyPlantAdapter
import com.riski.greenadvisor.ui.home.adapter.ArticlesAdapter
import com.riski.greenadvisor.ui.home.adapter.HomeViewModelAdapter
import com.riski.greenadvisor.ui.home.adapter.PlantCareAdapter
import com.riski.greenadvisor.ui.login.LoginActivity

// DataStore Menyimpan Cache aplikasi/ data sementara secara lokal seperti user id, email, password, token, access login
val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
class HomeFragment : Fragment() {
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var user: DataLogin
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var adapterAnyPlant: AnyPlantAdapter

    @Suppress("DEPRECATION")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (activity as AppCompatActivity).supportActionBar?.hide()
        @Suppress("DEPRECATION")
        setHasOptionsMenu(true)
//        (activity as AppCompatActivity).invalidateOptionsMenu()
        homeUserViewModel()
        progressBar()
        setViewPager()
        setArticles()
        setPlantList()
        clickArticles()
        notification()
        userLogin()
        clickAnyPlant()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val editor = sharedPreferences.edit()
        editor.putBoolean("IS_LOGIN", true)
        editor.apply()


        return root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataStore = requireContext().dataStore
    }

    private fun homeUserViewModel() {
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(Preference.getInstance(dataStore), requireContext()))[HomeViewModel::class.java]
    }

    private fun userLogin() {
        val pref = Preference.getInstance(dataStore)
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(pref, requireContext()))[homeViewModel::class.java]
        homeViewModel.getUser().observe(requireActivity()) { user->
            this.user = user
            if (!user.isLogin) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            } else {
                this.user = DataLogin(
                    user.name,
                    user.token,
                    true
                )
                binding.homeGreetings.text = user.name
                setAnyPlant()
            }
        }
    }

    private fun setViewPager() {
        viewPager = binding.homeViewpager
        val adapter =  HomeViewModelAdapter(this)
        viewPager.adapter = adapter
        adapter.startAutoSwipe(viewPager)
    }

    private fun setPlantList() {
            val recyclerView: RecyclerView = binding.homeRecyclerViewPlant
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            homeViewModel.plantCareList.observe(viewLifecycleOwner) { plantCareList ->
            val plantCareAdapter = PlantCareAdapter(plantCareList)
            recyclerView.adapter = plantCareAdapter
        }
    }

    private fun setArticles() {
        val recyclerView: RecyclerView = binding.homeRecyclerViewArticles
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        homeViewModel.articlesList.observe(viewLifecycleOwner) { articlesList ->
            val slideArticles = articlesList.take(3)
            val articlesAdapter = ArticlesAdapter(slideArticles as ArrayList<ArticlesData>)
            recyclerView.adapter = articlesAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAnyPlant() {
        adapterAnyPlant = AnyPlantAdapter()
        binding.homeRecyclerAnyPlant.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.homeRecyclerAnyPlant.setHasFixedSize(true)
        binding.homeRecyclerAnyPlant.adapter = adapterAnyPlant
        homeViewModel.loadAnyPlant(user.token)
        homeViewModel.getDataAnyPlant.observe(requireActivity()) {
            adapterAnyPlant.setAnyPlant(it)
            adapterAnyPlant.notifyDataSetChanged()

        }
    }

    private fun progressBar() {
        homeViewModel.loading.observe(requireActivity()) {
            binding.apply {
                if (it) {
                    homeLoading.visibility = View.VISIBLE
                    homeRecyclerAnyPlant.visibility = View.INVISIBLE
                    blur()
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        homeLoading.visibility = View.GONE
                        homeRecyclerAnyPlant.visibility = View.VISIBLE
                        noblur()
                    }, 1500L)
                }
            }
        }
    }

    private fun blur() {
        binding.homeProfile.alpha = 0.3f
        binding.homeWelcome.alpha = 0.3f
        binding.homeGreetings.alpha = 0.3f
        binding.homeNotification.alpha = 0.3f
        binding.homeViewpager.alpha = 0.3f
        binding.homeAnyPlantText.alpha = 0.3f
        binding.homeSeeAllAnyPlant.alpha = 0.3f
        binding.homePlantCare.alpha = 0.3f
        binding.homeRecyclerViewPlant.alpha = 0.3f
        binding.homeArticles.alpha = 0.3f
        binding.homeSeeAllArticles.alpha = 0.3f
        binding.homeRecyclerViewArticles.alpha = 0.3f
    }

    private fun noblur() {
            binding.homeProfile.alpha = 1f
            binding.homeWelcome.alpha = 1f
            binding.homeGreetings.alpha = 1f
            binding.homeNotification.alpha = 1f
            binding.homeViewpager.alpha = 1f
            binding.homeAnyPlantText.alpha = 1f
            binding.homeSeeAllAnyPlant.alpha = 1f
            binding.homePlantCare.alpha = 1f
            binding.homeRecyclerViewPlant.alpha = 1f
            binding.homeArticles.alpha = 1f
            binding.homeSeeAllArticles.alpha = 1f
            binding.homeRecyclerViewArticles.alpha = 1f
    }

    private fun notification() {
        binding.homeNotification.setOnClickListener {
            startActivity(Intent(requireContext(), BetaActivity::class.java))
        }
    }

    private fun clickAnyPlant() {
        binding.homeSeeAllAnyPlant.setOnClickListener {
            startActivity(Intent(requireContext(), DetailShowListAnyPlantActivity::class.java))
        }
    }

    private fun clickArticles() {
        binding.homeSeeAllArticles.setOnClickListener {
                startActivity(Intent(requireContext(), DetailShowListArticlesActivity::class.java))
            }
        }
}