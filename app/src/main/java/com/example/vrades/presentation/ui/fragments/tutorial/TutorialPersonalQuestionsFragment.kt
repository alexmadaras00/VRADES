package com.example.vrades.presentation.ui.fragments.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vrades.R
import com.example.vrades.databinding.FragmentTutorialPersonalQuestionsBinding
import com.example.vrades.presentation.ui.adapters.AdapterQuestions
import com.example.vrades.presentation.viewmodels.TutorialViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TutorialPersonalQuestionsFragment : Fragment(R.layout.fragment_tutorial_personal_questions) {
    // TODO: Rename and change types of parameters
    private val _bindings: FragmentTutorialPersonalQuestionsBinding? =null
    private var bindings = _bindings!!
    private lateinit var viewModel: TutorialViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bindings= FragmentTutorialPersonalQuestionsBinding.inflate(inflater)
        bindings.viewModel =  viewModel
        bindings.lifecycleOwner = this
        bindings.executePendingBindings()
        return bindings.root
    }

    override fun onStart() {
        super.onStart()
        val recyclerViewQuestions = bindings.rvQuestions
        val adaperQuestions = AdapterQuestions()
//        val questions = viewModel.getQuestions()
//        adaperQuestions.setDataSource(questions)
//        recyclerViewQuestions.adapter = adaperQuestions
//        recyclerViewQuestions.layoutManager = LinearLayoutManager(context)

    }


}