package com.example.vrades.ui.fragments

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Environment
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
import com.example.vrades.api.audio_detection.AudioDetectionAPI
import com.example.vrades.data.api.audio_detection.WebEmpath
import com.example.vrades.databinding.FragmentAudioTestBinding
import com.example.vrades.enums.AudioState
import com.example.vrades.interfaces.IOnTimerTickListener
import com.example.vrades.model.Response
import com.example.vrades.utils.Constants
import com.example.vrades.utils.UIUtils.toast
import com.example.vrades.viewmodels.TestViewModel
import com.example.vrades.widgets.Timer
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


@AndroidEntryPoint
class AudioTestFragment : VradesBaseFragment(), IOnTimerTickListener {

    private var _binding: FragmentAudioTestBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TestViewModel by activityViewModels()
    private var mediaRecorder: MediaRecorder? = null
    private lateinit var timer: Timer
    private var isPermissionGranted = false
    private var stateAudio by Delegates.notNull<Int>()
    private var contextNullSafe: Context? = null
    private lateinit var path: String
    private lateinit var recordPath: File
    private lateinit var recordFile: String

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAudioTestBinding.inflate(inflater)
        binding.viewModelTest = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        contextNullSafe = context
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        getDataAudioTest()
        isPermissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
        val navController = findNavController()

        stateAudio = viewModel.getCurrentAudioState().ordinal
        timer = Timer(this)
        binding.apply {
            val buttonRecord = fbtnVoiceRecord
            val buttonRestart = fbtnRestartRecording
            val buttonStop = fbtnStopRecording
            val buttonNext = btnProceed
            val visualizer = visualizer
            buttonRecord.setOnClickListener {
                println(stateAudio)
                if (checkPermissions()) {
                    when (stateAudio) {
                        0 -> {
                            path = startRecording()
                            buttonRestart.isVisible = true
                            buttonStop.isVisible = true
                            visualizer.isVisible = true
                            buttonRecord.isVisible = false
                        }
                        1 -> {
                            pauseRecording()
                            visualizer.isGone = true
                            visualizer.isVisible = false
                        }
                        2 -> {
                            resumeRecording()
                            visualizer.isGone = false
                            visualizer.isVisible = true
                        }
                    }
                }
                buttonStop.setOnClickListener {
                    viewModel.setAudioStateCount(2)
                    buttonStop.isVisible = false
                    buttonRestart.isVisible = false
                    buttonNext.isVisible = true
                    buttonRecord.isVisible = false
                    visualizer.isGone = true
                    if (stateAudio != AudioState.DONE_RECORDING.ordinal) {
                        stopRecording()
                        println(path)
                    }
//                    audioDetectionAPI.detectEmotion(path)
                    empathDetectionAPI.detect(path)
                }
                buttonRestart.setOnClickListener {
                    stopRecording()
                    path = startRecording()
                }
                buttonNext.setOnClickListener {
                    navController.navigate(AudioTestFragmentDirections.actionAudioTestFragmentToWritingTestFragment())

                }
            }
        }
    }

    private fun startRecording(): String {
        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(this.requireActivity())
        } else MediaRecorder()
       recordPath = ContextWrapper(activity).getExternalFilesDir(Environment.DIRECTORY_MUSIC)!!
       recordFile =
            "Recording - " + SimpleDateFormat(
                FILENAME_FORMAT,
                Locale.getDefault()
            ).format((Date())) + ".wav"
        val file = File(recordPath, recordFile)

        mediaRecorder!!.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(file.path)
            try {
                prepare()
                start()
                toast(requireContext(), "Recording started...")
            } catch (e: IOException) {
                e.printStackTrace()
            }
            timer.start()
            viewModel.setAudioStateCount(1)
            stateAudio = AudioState.START_RECORDING.ordinal
        }

        return "$recordPath/$recordFile"

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun pauseRecording() {
        mediaRecorder!!.pause()
        binding.fbtnVoiceRecord.setImageResource(R.drawable.ic_baseline_keyboard_voice_24)
        viewModel.setAudioStateCount(2)
        stateAudio = AudioState.PAUSE_RECORDING.ordinal
        timer.pause()
        toast(requireContext(), "Recording paused...")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun resumeRecording() {
        mediaRecorder!!.resume()
        binding.apply {
            fbtnVoiceRecord.setImageResource(R.drawable.ic_baseline_pause_24)
        }
        viewModel.setAudioStateCount(1)
        stateAudio = AudioState.START_RECORDING.ordinal
//        audioDetectionAPI.start(this.activity)
        timer.start()
        toast(requireContext(), "Recording resumed...")

    }

    private fun stopRecording() {
        mediaRecorder!!.apply {
            stop()
            release()
        }
        viewModel.setAudioStateCount(3)
        stateAudio = AudioState.DONE_RECORDING.ordinal
        mediaRecorder = null
        timer.stop()
        toast(requireContext(), "Recording saved!")
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

    private fun getDataAudioTest() {
        viewModel.getDataAudioTest().observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    val data = it.data
                    val randomQuestion = data.random()
                    binding.tvAudioTest.text = randomQuestion
                }
                is Response.Error -> {
                    println(Constants.ERROR_REF)
                }
                else -> {
                }
            }
        }
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
        private var audioDetectionAPI = AudioDetectionAPI()
        private var empathDetectionAPI = WebEmpath()
    }

    override fun onTimerTick(duration: String) {
        println(duration)

    }


}