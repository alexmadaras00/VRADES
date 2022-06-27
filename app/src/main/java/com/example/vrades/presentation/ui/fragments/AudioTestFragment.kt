package com.example.vrades.presentation.ui.fragments

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.vrades.data.api.text_detection.TextDetectionAPI
import com.example.vrades.databinding.DialogLoadingBinding
import com.example.vrades.databinding.FragmentAudioTestBinding
import com.example.vrades.domain.model.Response
import com.example.vrades.presentation.enums.AudioState
import com.example.vrades.presentation.interfaces.IOnTimerTickListener
import com.example.vrades.presentation.utils.Constants
import com.example.vrades.presentation.utils.UIUtils.toast
import com.example.vrades.presentation.viewmodels.TestViewModel
import com.example.vrades.presentation.widgets.Timer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*
import kotlin.properties.Delegates

@AndroidEntryPoint
class AudioTestFragment : VradesBaseFragment(), IOnTimerTickListener {

    private var _binding: FragmentAudioTestBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TestViewModel by activityViewModels()
    private var stateAudio by Delegates.notNull<Int>()
    private var contextNullSafe: Context? = null
    private var dialog: Dialog? = null
    private lateinit var timer: Timer
    private var isRecordingPermissionGranted = false
    private var mediaRecorder: MediaRecorder? = null
    private var dialogBinding: DialogLoadingBinding? = null
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        contextNullSafe = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAudioTestBinding.inflate(inflater)
        binding.viewModelTest = viewModel
        binding.executePendingBindings()

        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.MODIFY_AUDIO_SETTINGS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermissions()
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        println("SPEECH INITITALIZER: $speechRecognizer")


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        getDataAudioTest()
        val navController = findNavController()
        timer = Timer(this)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                println("Ready")
            }

            override fun onBeginningOfSpeech() {
                println("Start")

            }

            override fun onRmsChanged(rmsdB: Float) {
                // Do nothing
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                // Do nothing
            }

            override fun onEndOfSpeech() {
                println("Speech done")
            }

            override fun onError(error: Int) {
                println("THE ERROR IS: $error ")
            }

            override fun onResults(results: Bundle?) {
                val test: ArrayList<String> =
                    results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) as ArrayList<String>
                println("Results")
                println(test)
                Log.i("TAG", "The array size is : $test");
                lifecycleScope.launch(Dispatchers.IO) {
                    val resultText = detectEmotionFromAudio(test[0])
                    val emotionsMap = configJsonToMap(resultText)
                    val finalResult = calculateMaximumValue(emotionsMap)
                    println(resultText)
                    lifecycleScope.launch(Dispatchers.Main) {
                        toast(requireContext(), "Result on Audio Detection: $finalResult")
                    }
                    viewModel.setAudioDetectedResult(emotionsMap)
                    dismissDialog()
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {

            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                TODO("Not yet implemented")
            }

        })
        stateAudio = viewModel.getCurrentAudioState().ordinal
        binding.apply {
            val buttonRecord = fbtnVoiceRecord
            val buttonRestart = fbtnRestartRecording
            val buttonStop = fbtnStopRecording
            val buttonNext = btnProceed
            val visualizer = visualizer
            buttonRecord.setOnClickListener {
                println(stateAudio)
                when (stateAudio) {
                    0 -> {
                        println("STARTED RECORDING...")
                        startRecording()
                        buttonRestart.isVisible = true
                        buttonStop.isVisible = true
                        visualizer.isVisible = true
                        buttonRecord.isVisible = false
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
                }
            }
            buttonRestart.setOnClickListener {
                stopRecording()
                startRecording()
            }
            buttonNext.setOnClickListener {
                navController.navigate(AudioTestFragmentDirections.actionAudioTestFragmentToWritingTestFragment())

            }

        }
    }

    private fun configJsonToMap(resultText: String): MutableMap<String, Float> {
        val emotionsMap = mutableMapOf<String, Float>()
        val pathJson = JSONObject(resultText).getJSONObject("sentence")
        emotionsMap["angry"] = pathJson.getDouble("anger").toFloat()
        emotionsMap["disgust"] = pathJson.getDouble("disgust").toFloat()
        emotionsMap["fear"] = pathJson.getDouble("fear").toFloat()
        emotionsMap["happy"] = pathJson.getDouble("joy").toFloat()
        emotionsMap["neutral"] = pathJson.getDouble("noemo").toFloat()
        emotionsMap["sad"] = pathJson.getDouble("sadness").toFloat()
        emotionsMap["surprise"] = pathJson.getDouble("surprise").toFloat()
        emotionsMap["love"] = pathJson.getDouble("love").toFloat()
        return emotionsMap
    }

    private fun calculateMaximumValue(emotionsMap: Map<String, Float>): String {
        val maxValue = emotionsMap.maxOf {
            it.value
        }
        val maxValueKeys: MutableList<String> = mutableListOf()
        var finalResult = ""
        for ((key, value) in emotionsMap) {
            if (value == maxValue) {
                maxValueKeys.add(key)
            }
        }
        if (maxValueKeys.size > 1)
            maxValueKeys.indices.forEach {
                if (it > 0)
                    finalResult += ", $it"
                else finalResult += it
            }
        else finalResult = maxValueKeys[0]
        return finalResult
    }

    private suspend fun detectEmotionFromAudio(text: String?): String {
        return TextDetectionAPI.detectText(text.toString())

    }

    private fun startRecording() {

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "RO-ro"
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("ro_RO"))
        speechRecognizer.startListening(intent)
        viewModel.setAudioStateCount(1)
        stateAudio = AudioState.START_RECORDING.ordinal

    }

    private fun stopRecording() {

        viewModel.setAudioStateCount(3)
        stateAudio = AudioState.DONE_RECORDING.ordinal
        speechRecognizer.stopListening()
        openDialog()
        toast(requireContext(), "Recording saved!")
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

    private fun openDialog() {
        dialog = Dialog(this.requireContext())
        dialogBinding = DialogLoadingBinding.inflate(LayoutInflater.from(context), null, false)
        dialog!!.setContentView(dialogBinding!!.root)
        dialog!!.show()
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setView(binding.root)
    }

    private fun dismissDialog() {
        if (dialog!!.isShowing)
            dialog!!.dismiss()
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.MODIFY_AUDIO_SETTINGS
                ),
                REQUEST_RECORD_AUDIO_PERMISSION
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toast(requireContext(), "Permission granted!")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
        if (dialog != null && dialog!!.isShowing) {
            dismissDialog()
        }
        _binding = null
    }

    companion object {
        fun newInstance() = FaceDetectionFragment()
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 1
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

    }

    override fun onTimerTick(duration: String) {
        println(duration)
    }


}