package com.example.vrades.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vrades.databinding.FragmentMyProfileBinding
import com.example.vrades.interfaces.IOnClickListener
import com.example.vrades.model.Response
import com.example.vrades.model.Test
import com.example.vrades.ui.adapters.AdapterTestHistory
import com.example.vrades.ui.dialogs.FeedbackRequestDialog
import com.example.vrades.utils.Constants
import com.example.vrades.viewmodels.MyProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment : Fragment() {

    companion object {
        fun newInstance() = MyProfileFragment()
    }

    private var _binding: FragmentMyProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyProfileBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        addTestsData()
        binding.apply {
            val buttonAnalysis = btnDiary
            val buttonBack = btnBackProfile
            val navController = findNavController()

            buttonAnalysis.setOnClickListener{
                navController.navigate(MyProfileFragmentDirections.actionNavProfileToNavDialog())
            }
            buttonBack.setOnClickListener{
                navController.navigate(MyProfileFragmentDirections.actionNavProfileToNavHome())
            }

        }
    }

    private fun addTestsData() {
        viewModel.getTests().observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    configureRecyclerView(it.data)
                    println("LIST: $it.data")
                }
                is Response.Error -> {
                    println(Constants.ERROR_REF)
                }
                else -> {
                    println(Constants.ERROR_REF)
                }
            }
        }
    }

    private fun configureRecyclerView(tests: List<Test>) {
        binding.apply {
            val recyclerViewTestHistory = rvTestResults

            val onClickListener = object : IOnClickListener {
                override fun onItemClick(position: Int) {
                    findNavController().navigate(MyProfileFragmentDirections.actionNavProfileToNavSolutions())
                }

            }
            val adapterTestHistory = AdapterTestHistory(onClickListener)
            adapterTestHistory.setDataSource(tests as ArrayList<Test>)
            recyclerViewTestHistory.adapter = adapterTestHistory
            recyclerViewTestHistory.layoutManager = LinearLayoutManager(context)
            recyclerViewTestHistory.setHasFixedSize(true)
            recyclerViewTestHistory.isScrollContainer = true
            recyclerViewTestHistory.hasNestedScrollingParent()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}