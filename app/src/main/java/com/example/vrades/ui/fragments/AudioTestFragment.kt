package com.example.vrades.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentAudioTestBinding
import com.example.vrades.enums.AudioState
import com.example.vrades.viewmodels.TestViewModel
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AudioTestFragment : VradesBaseFragment() {


    private var _binding: FragmentAudioTestBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TestViewModel
    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var isPermissionGranted = false
    private var isRecording = false


    companion object {
        fun newInstance() = FaceDetectionFragment()
        private const val LOG_TAG = "AudioRecordTest"
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
        private var permission = Manifest.permission.RECORD_AUDIO
        private const val PERMISSION_CODE = 21
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private var stateAudio = AudioState.IDLE.ordinal

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[TestViewModel::class.java]
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
        binding.apply {
            val buttonRecord = fbtnVoiceRecord
            val buttonRestart = fbtnRestartRecording
            val buttonStop = fbtnStopRecording
            val buttonNext = btnProceed
            buttonRecord.setOnClickListener {
                viewModel.setAudioStateCount(1)
                buttonRecord.isVisible = false
                buttonRestart.isVisible = true
                buttonStop.isVisible = true
                if (!isRecording) {
                    if (checkPermissions()) {
                        startRecording()
                        isRecording = true
                    }
                }
            }
            buttonStop.setOnClickListener {
                viewModel.setAudioStateCount(2)
                buttonStop.isVisible = false
                buttonRestart.isVisible = false
                buttonNext.isVisible = true
                if (isRecording) {
                    stopRecording()
                    isRecording = false
                }
            }
            buttonRestart.setOnClickListener {
                viewModel.setAudioStateCount(1)
                if (isRecording) {
                    stopRecording()
                    startRecording()
                    isRecording = true
                }
            }
            buttonNext.setOnClickListener {
                navController.navigate(AudioTestFragmentDirections.actionAudioTestFragmentToWritingTestFragment())
            }

        }
    }

    private fun stopRecording() {
        mediaRecorder!!.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }


    private fun startRecording() {
        mediaRecorder = MediaRecorder()
        val recordPath = requireActivity().getExternalFilesDir("/")!!.absolutePath
        val recordFile =
            "Recording - " + SimpleDateFormat(
                FILENAME_FORMAT,
                Locale.getDefault()
            ).format((Date())) + ".3gp"
        mediaRecorder!!.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile("$recordPath/$recordFile")
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB)
            try {
                prepare()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            start()
        }

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}