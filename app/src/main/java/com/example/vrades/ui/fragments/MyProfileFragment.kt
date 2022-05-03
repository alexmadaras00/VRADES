package com.example.vrades.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vrades.databinding.FragmentMyProfileBinding
import com.example.vrades.interfaces.IOnClickListener
import com.example.vrades.ui.adapters.AdapterTestHistory
import com.example.vrades.ui.dialogs.FeedbackRequestDialog
import com.example.vrades.viewmodels.MyProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment : Fragment() {

    companion object {
        fun newInstance() = MyProfileFragment()
    }

    private var _binding: FragmentMyProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MyProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MyProfileViewModel::class.java]
        _binding = FragmentMyProfileBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        binding.apply {
            val recyclerViewTestHistory = rvTestResults
            val buttonAnalysis = btnDiary
            val buttonBack = btnBackProfile
            val navController = findNavController()
            val onClickListener = object : IOnClickListener {
                override fun onItemClick(position: Int) {
                    navController.navigate(MyProfileFragmentDirections.actionNavProfileToNavSolutions())
                }

            }
            val adapterTestHistory = AdapterTestHistory(onClickListener)
            val tests = viewModel!!.getTests()
            adapterTestHistory.setDataSource(tests)
            recyclerViewTestHistory.adapter = adapterTestHistory
            recyclerViewTestHistory.layoutManager = LinearLayoutManager(context)
            recyclerViewTestHistory.setHasFixedSize(true)
            recyclerViewTestHistory.isScrollContainer = true
            recyclerViewTestHistory.hasNestedScrollingParent()
            buttonAnalysis.setOnClickListener{
                navController.navigate(MyProfileFragmentDirections.actionNavProfileToNavDialog())
            }
            buttonBack.setOnClickListener{
                navController.navigate(MyProfileFragmentDirections.actionNavProfileToNavHome())
            }

        }
    }

    private fun showDialog(){
        val dialog = FeedbackRequestDialog()
        dialog.show(requireActivity().supportFragmentManager,"customDialog")
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}