package com.example.vrades.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vrades.enums.TestState
import com.example.vrades.ui.fragments.AudioTestFragment
import com.example.vrades.ui.fragments.FaceDetectionFragment
import com.example.vrades.ui.fragments.WritingTestFragment

class AdapterViewPagerTest(fragment: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragment, lifecycle) {

    private val faceDetectionFragment = FaceDetectionFragment()
    private val audioDetectionFragment = AudioTestFragment()
    private val writingDetectionFragment = WritingTestFragment()

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TestState.TEST_STARTED.ordinal -> {
                faceDetectionFragment
            }
            TestState.FACE_DETECTION_COMPLETED.ordinal -> {
                audioDetectionFragment
            }
            TestState.AUDIO_DETECTION_COMPLETED.ordinal -> {
                writingDetectionFragment
            }
            else -> faceDetectionFragment
        }

    }
}