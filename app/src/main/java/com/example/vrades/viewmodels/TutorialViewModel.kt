package com.example.vrades.viewmodels

import androidx.lifecycle.ViewModel
import com.example.vrades.models.Question

class TutorialViewModel: ViewModel() {

    private var questions = ArrayList<Question>()

    fun addQuestions(){
        questions.add(Question("How was your day?",null,false))
        questions.add(Question("How was your day today?",null,false))
        questions.add(Question("How was your day?",null,false))
    }

    fun getQuestions(): ArrayList<Question>{
        addQuestions()
        return questions
    }
}