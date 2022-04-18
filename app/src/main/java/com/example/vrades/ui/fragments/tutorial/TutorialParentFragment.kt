package com.example.vrades.ui.fragments.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentTutorialParentBinding
import com.example.vrades.ui.adapters.AdapterViewPagerTutorial
import com.example.vrades.ui.fragments.VradesBaseFragment
import com.example.vrades.viewmodels.TutorialViewModel
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

class TutorialParentFragment : VradesBaseFragment() {

    private val viewModel: TutorialViewModel by activityViewModels()
    private var _binding: FragmentTutorialParentBinding? = null
    private lateinit var adapterViewPagerTutorial: AdapterViewPagerTutorial
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTutorialParentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.apply {
            val viewPagerTutorial = vpTutorial
            adapterViewPagerTutorial = AdapterViewPagerTutorial(childFragmentManager, lifecycle)
            viewPagerTutorial.adapter = adapterViewPagerTutorial
            viewPagerTutorial.isUserInputEnabled = false
            sdTutorialndicators.apply {
                setSliderWidth(40f)
                setSliderHeight(40f)
                setSlideMode(IndicatorSlideMode.SMOOTH)
                setHasTransientState(true)
                setIndicatorStyle(IndicatorStyle.CIRCLE)
                setupWithViewPager(vpTutorial)
            }
            vpTutorial.setPageTransformer(DepthPageTransformer())
        }
    }

    private val onNextPage = Observer<Void> {
            binding.vpTutorial.currentItem += 1
    }
    private val onNavigateToHomeScreen = Observer<Void> {
        val navController = findNavController()
        navController.navigate(TutorialParentFragmentDirections.actionNavTutorialToNavHome())
    }

    override fun connectViewModelEvents() {
        viewModel.onNextPage.observe(this, onNextPage)
        viewModel.onNavigateToHome.observe(this, onNavigateToHomeScreen)
    }

    override fun disconnectViewModelEvents() {
        viewModel.onNextPage.removeObserver(onNextPage)
        viewModel.onNavigateToHome.removeObserver(onNavigateToHomeScreen)
        viewModelStore.clear()

    }

    override fun onPause() {
        super.onPause()
        disconnectViewModelEvents()
    }

    override fun onResume() {
        super.onResume()
        connectViewModelEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}