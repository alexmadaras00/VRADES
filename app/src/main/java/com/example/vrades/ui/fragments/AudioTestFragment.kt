package com.example.vrades.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.vrades.R
import com.example.vrades.databinding.FragmentAudioTestBinding
import com.example.vrades.enums.AudioState
import com.example.vrades.viewmodels.TestViewModel
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class AudioTestFragment : VradesBaseFragment() {

    private var _binding: FragmentAudioTestBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TestViewModel by activityViewModels()
    private var mediaRecorder: MediaRecorder? = null
    private lateinit var amplitudes: ArrayList<Float>
    private var isPermissionGranted = false
    private var stateAudio by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAudioTestBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModelTest = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        isPermissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
        val navController = findNavController()
        stateAudio = viewModel.getCurrentAudioState().ordinal
        binding.apply {
            val buttonRecord = fbtnVoiceRecord
            val buttonRestart = fbtnRestartRecording
            val buttonStop = fbtnStopRecording
            val buttonNext = btnProceed
            val waveView = waveFromView
            buttonRecord.setOnClickListener {
                println(stateAudio)
                if (checkPermissions()) {

                    when (stateAudio) {
                        0 -> {
                            buttonRestart.isVisible = true
                            buttonStop.isVisible = true
                            waveView.isVisible = true
                            startRecording()
                        }
                        1 -> {
                            pauseRecording()
                            waveView.isGone = true
                        }
                        2 -> {
                            resumeRecording()
                            waveView.isGone = false
                            waveView.isVisible = true
                        }
                    }
                }
                buttonStop.setOnClickListener {
                    viewModel.setAudioStateCount(2)
                    buttonStop.isVisible = false
                    buttonRestart.isVisible = false
                    buttonNext.isVisible = true
                    buttonRecord.isVisible = false
                    waveView.isGone = true
                    if (stateAudio != AudioState.DONE_RECORDING.ordinal) {
                        stopRecording()
                    }
                }
                buttonRestart.setOnClickListener {
                    stopRecording()
                    startRecording()
                }
                buttonNext.setOnClickListener { navController.navigate(AudioTestFragmentDirections.actionAudioTestFragmentToWritingTestFragment()) }
            }
        }
    }

    private fun startRecording() {

        mediaRecorder = MediaRecorder()
        val recordPath = requireActivity().getExternalFilesDir("/")!!.absolutePath
        val recordFile =
            "Recording - " + SimpleDateFormat(
                FILENAME_FORMAT,
                Locale.getDefault()
            ).format((Date())) + ".mp3"
        mediaRecorder!!.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile("$recordPath/$recordFile")
            try {
                prepare()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            start()
            viewModel.setAudioStateCount(1)
            stateAudio = AudioState.START_RECORDING.ordinal
        }
        binding.apply {
            waveFromView.addAmplitude(mediaRecorder!!.maxAmplitude.toFloat())
            fbtnVoiceRecord.setImageResource(R.drawable.ic_baseline_pause_24)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun pauseRecording() {
        mediaRecorder!!.pause()
        binding.fbtnVoiceRecord.setImageResource(R.drawable.ic_baseline_keyboard_voice_24)
        viewModel.setAudioStateCount(2)
        stateAudio = AudioState.PAUSE_RECORDING.ordinal
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun resumeRecording() {
        mediaRecorder!!.resume()
        binding.apply {
            fbtnVoiceRecord.setImageResource(R.drawable.ic_baseline_pause_24)
            waveFromView.addAmplitude(mediaRecorder!!.maxAmplitude.toFloat())
        }
        viewModel.setAudioStateCount(1)
        stateAudio = AudioState.START_RECORDING.ordinal

    }

    private fun stopRecording() {
        mediaRecorder!!.apply {
            stop()
            release()
        }
        viewModel.setAudioStateCount(3)
        stateAudio = AudioState.DONE_RECORDING.ordinal
        mediaRecorder = null
    }


    private fun checkPermissions(): Boolean {
        return if (isPermissionGranted) {
            true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(permission),
                PERMISSION_CODE
            )
            false
        }
    }

    private val currentAudioStateCount = Observer<Int> {
        stateAudio = it
    }

    override fun connectViewModelEvents() {
        viewModel.currentAudioStateCount.observe(this, currentAudioStateCount)
    }

    override fun disconnectViewModelEvents() {
        viewModel.currentAudioStateCount.removeObserver(currentAudioStateCount)
        viewModelStore.clear()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = FaceDetectionFragment()
        private const val LOG_TAG = "AudioRecordTest"
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
        private var permission = Manifest.permission.RECORD_AUDIO
        private const val PERMISSION_CODE = 21
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

    }


}