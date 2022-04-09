package com.example.vrades.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vrades.enums.TutorialState
import com.example.vrades.ui.fragments.tutorial.*

class AdapterViewPagerTutorial(fragment: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragment,lifecycle) {

    private val tutorialHomeFragment = TutorialHomeFragment()
    private val tutorialFaceDetectionFragment = TutorialFaceDetectionFragment()
    private val tutorialAudioFragment = TutorialAudioFragment()
    private val tutorialWritingFragment = TutorialWritingFragment()
    private val tutorialFinishedFragment = TutorialFinishedFragment()
    private val tutorialFinishedFragment2 = TutorialFinishedFragment()

    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        when (position) {
            TutorialState.START_PAGE.ordinal -> {
                return tutorialHomeFragment
            }
            TutorialState.FACE_TEST_PAGE.ordinal -> {
                return tutorialFaceDetectionFragment
            }
            TutorialState.FACE_TEST_PAGE_DONE.ordinal -> {
                return tutorialFinishedFragment
            }
            TutorialState.AUDIO_TEST_PAGE.ordinal -> {
                return tutorialAudioFragment
            }
            TutorialState.WRITING_TEST_PAGE.ordinal -> {
                return tutorialWritingFragment
            }
            TutorialState.FINAL_PAGE.ordinal -> {
                return tutorialFinishedFragment2
            }
            else -> {
                return tutorialHomeFragment
            }
        }

    }
}