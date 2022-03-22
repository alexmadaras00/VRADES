package com.example.vrades.ui.fragments

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vrades.R
import com.example.vrades.databinding.FragmentMyProfileBinding
import com.example.vrades.databinding.LoginFragmentBinding
import com.example.vrades.ui.adapters.AdapterTestHistory
import com.example.vrades.viewmodels.LoginViewModel
import com.example.vrades.viewmodels.MyProfileViewModel

class MyProfileFragment : Fragment() {

    companion object {
        fun newInstance() = MyProfileFragment()
    }
    private val _bindings: FragmentMyProfileBinding? = null
    private var bindings = _bindings!!
    private lateinit var viewModel: MyProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MyProfileViewModel::class.java]
        bindings = FragmentMyProfileBinding.inflate(inflater)
        bindings.lifecycleOwner = this
        bindings.viewModel = viewModel
        bindings.executePendingBindings()
        return bindings.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        val recyclerViewTestHistory = bindings.rvTestResults
        val adapterTestHistory = AdapterTestHistory()
        val tests = viewModel.getTests()
        adapterTestHistory.setDataSource(tests)
        recyclerViewTestHistory.adapter = adapterTestHistory
        recyclerViewTestHistory.layoutManager= LinearLayoutManager(context)

    }

}