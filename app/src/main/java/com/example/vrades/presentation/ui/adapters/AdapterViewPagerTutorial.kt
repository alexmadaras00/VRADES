package com.example.vrades.presentation.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vrades.presentation.enums.TutorialState
import com.example.vrades.presentation.ui.fragments.tutorial.*

class AdapterViewPagerTutorial(fragment: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragment, lifecycle) {

    private val tutorialHomeFragment = TutorialHomeFragment()
    private val tutorialFaceDetectionFragment = TutorialFaceDetectionFragment()
    private val tutorialAudioFragment = TutorialAudioFragment()
    private val tutorialWritingFragment = TutorialWritingFragment()
    private val tutorialFinishedFragment = TutorialFinishedFragment()
    private val tutorialFinishedFragment2 = TutorialFinishedFragment()

    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TutorialState.START_PAGE.ordinal -> {
                tutorialHomeFragment
            }
            TutorialState.FACE_TEST_PAGE.ordinal -> {
                tutorialFaceDetectionFragment
            }
            TutorialState.FACE_TEST_PAGE_DONE.ordinal -> {
                tutorialFinishedFragment
            }
            TutorialState.AUDIO_TEST_PAGE.ordinal -> {
                tutorialAudioFragment
            }
            TutorialState.WRITING_TEST_PAGE.ordinal -> {
                tutorialWritingFragment
            }
            TutorialState.FINAL_PAGE.ordinal -> {
                tutorialFinishedFragment2
            }
            else -> {
                tutorialHomeFragment
            }
        }

    }
}