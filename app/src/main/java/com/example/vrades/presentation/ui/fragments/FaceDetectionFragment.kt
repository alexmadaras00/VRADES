package com.example.vrades.presentation.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.vrades.data.api.face_detection.FaceDetectionAPI
import com.example.vrades.databinding.DialogLoadingBinding
import com.example.vrades.databinding.FragmentFaceDetectionBinding
import com.example.vrades.domain.model.Response
import com.example.vrades.presentation.utils.Constants
import com.example.vrades.presentation.utils.UIUtils.toast
import com.example.vrades.presentation.utils.camerax.CameraManager
import com.example.vrades.presentation.utils.camerax.CameraManager.Companion.isReadyCamera
import com.example.vrades.presentation.viewmodels.TestViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.internal.ContextUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.File
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@AndroidEntryPoint
class FaceDetectionFragment : Fragment() {

    private var _binding: FragmentFaceDetectionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TestViewModel by activityViewModels()
    private lateinit var safeContext: Context
    private lateinit var outputDirectory: File
    private lateinit var cameraManager: CameraManager
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isCameraPermissionGranted = false
    private var isRecordingPermissionGranted = false
    private var isReadExternalStoragePermissionGranted = false
    private var isWriteExternalStoragePermissionGranted = false
    private var isManageExternalStoragePermissionGranted = false
    private lateinit var instance: FaceDetectionFragment
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private var cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
    private var saveToCameraRoll = true
    private var outputPath: String? = null
    private var dialog: Dialog? = null
    private var dialogBinding: DialogLoadingBinding? = null
    private lateinit var result: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
        instance = this
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFaceDetectionBinding.inflate(inflater)
        instance = this
        binding.apply {

            binding.viewModelTest = viewModel
            binding.executePendingBindings()
        }
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                isCameraPermissionGranted =
                    permissions[Manifest.permission.CAMERA] ?: isCameraPermissionGranted
                isRecordingPermissionGranted =
                    permissions[Manifest.permission.RECORD_AUDIO] ?: isRecordingPermissionGranted
                isReadExternalStoragePermissionGranted =
                    permissions[Manifest.permission.READ_EXTERNAL_STORAGE]
                        ?: isReadExternalStoragePermissionGranted
                isReadExternalStoragePermissionGranted =
                    permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE]
                        ?: isWriteExternalStoragePermissionGranted
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    isManageExternalStoragePermissionGranted =
                        permissions[Manifest.permission.MANAGE_EXTERNAL_STORAGE]
                            ?: isManageExternalStoragePermissionGranted
                }
            }
        requestAllPermissions()
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        return binding.root
    }

    private fun requestAllPermissions() {
        isRecordingPermissionGranted = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
        isCameraPermissionGranted = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        isReadExternalStoragePermissionGranted = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        isWriteExternalStoragePermissionGranted = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        isManageExternalStoragePermissionGranted = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val requestPermissions = mutableListOf<String>()
        if (!isCameraPermissionGranted) {
            requestPermissions.add(Manifest.permission.CAMERA)
        }
        if (!isRecordingPermissionGranted)
            requestPermissions.add(Manifest.permission.RECORD_AUDIO)
        if (!isReadExternalStoragePermissionGranted)
            requestPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (!isWriteExternalStoragePermissionGranted)
            requestPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!isManageExternalStoragePermissionGranted)
            requestPermissions.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        if (requestPermissions.isNotEmpty()) {
            permissionLauncher.launch(requestPermissions.toTypedArray())
            startCamera()
        }
    }


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onStart() {
        super.onStart()

        val navController = findNavController()
        binding.apply {
            val buttonCamera = fbtnCamera
            val buttonGallery = fbtnGallery
            val buttonNext = btnNextFace
            val buttonSwitch = binding.btnChangeCamera
            buttonCamera.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    animateFlash()
                }
                takePhoto()
                openDialog()
                buttonCamera.visibility = View.GONE
                buttonGallery.visibility = View.GONE
                buttonNext.visibility = View.VISIBLE
                viewModel.setStateCount(1)
            }

            buttonSwitch.setOnClickListener {
                if (isReadyCamera)
                    switchCamera()

            }
            buttonNext.setOnClickListener {
                navController.navigate(FaceDetectionFragmentDirections.actionFaceDetectionFragmentToAudioTestFragment())
            }
            createNewExecutor()
        }
    }

    override fun onPause() {
        super.onPause()
        isOffline = true
    }

    override fun onResume() {
        super.onResume()

        isOffline = false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun animateFlash() {
        binding.vfCameraPreview.postDelayed({
            binding.vfCameraPreview.foreground = ColorDrawable(Color.WHITE)
            binding.vfCameraPreview.postDelayed({
                binding.vfCameraPreview.foreground = null
            }, 50)
        }, 100)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        shutdownExecutor()
    }

    private fun setFacePictureOnStorage(picture: Uri) {
        viewModel.setPictureInStorage(picture)
            .observe(viewLifecycleOwner) {
                when (it) {
                    is Response.Success -> {
                        val uri = it.data
                        println(uri)
                        lifecycleScope.launch(Dispatchers.IO) {
                            result = FaceDetectionAPI.detectFaces(it.data)!!
                            println("RESULTS ARRAY: $result")
                            val jsonArray = JSONArray(result)
                            if (jsonArray.length() > 0) {
                                println("FIRST ELEMENT: ${jsonArray.getJSONObject(0)}")
                                val emotionsMap = configJsonToMap(jsonArray)
                                val testResult = calculateMaxValue(emotionsMap)
                                lifecycleScope.launch(Dispatchers.Main) {
                                    toast(requireContext(), "Result on Face Detection: $testResult")
                                }
                                println("Values emotions: $emotionsMap")
                                viewModel.setFaceDetectedResult(emotionsMap)

                            } else restartPhoto()
                        }

                    }
                    is Response.Error -> {
                        println(it.message)
                    }
                    else -> {
                        println(Constants.ERROR_REF)
                    }

                }

                dismissDialog()
            }

    }

    private fun calculateMaxValue(emotionsMap: MutableMap<String, Float>): String {
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

    private fun configJsonToMap(jsonArray: JSONArray): MutableMap<String, Float> {
        val emotionsMap = mutableMapOf<String, Float>()
        val jsonObject = jsonArray.getJSONObject(0)
        val testResultsMapped =
            jsonObject.getJSONObject("emotion").getJSONObject("sentiments")
        val testResult =
            jsonObject.getJSONObject("emotion").getString("value")
        emotionsMap["angry"] =
            roundTheNumber(BigDecimal(testResultsMapped.getDouble("angry"))).toFloat()
        emotionsMap["disgust"] = roundTheNumber(
            testResultsMapped.getDouble("disgust").toBigDecimal()
        ).toFloat()
        emotionsMap["fear"] = roundTheNumber(
            testResultsMapped.getDouble("fear").toBigDecimal()
        ).toFloat()
        emotionsMap["happy"] = roundTheNumber(
            testResultsMapped.getDouble("happy").toBigDecimal()
        ).toFloat()
        emotionsMap["sad"] = roundTheNumber(
            testResultsMapped.getDouble("sad").toBigDecimal()
        ).toFloat()
        emotionsMap["surprise"] = roundTheNumber(
            testResultsMapped.getDouble("surprise").toBigDecimal()
        ).toFloat()
        emotionsMap["neutral"] = roundTheNumber(
            testResultsMapped.getDouble("neutral").toBigDecimal()
        ).toFloat()
        return emotionsMap
    }

    private fun restartPhoto() {
        TODO("Not yet implemented")
    }

    private fun createNewExecutor() {
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun shutdownExecutor() {
        cameraExecutor.shutdown()
    }

    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.vfCameraPreview.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()
            // Select back camera as a default
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()
                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e(CameraManager.TAG, "Use case binding failed", exc)
            }
            isReadyCamera = true
        }, ContextCompat.getMainExecutor(requireContext()))
    }


    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("RestrictedApi")
    fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val name =
                "Picture " + SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                    .format(Date()) + ".jpg"
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image/")
                }
            }
            val outputOptions = ImageCapture.OutputFileOptions
                .Builder(
                    requireContext().contentResolver,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )
                .build()
            // Create output options object which contains file + metadata

            // Set up image capture listener, which is triggered after photo has
            // been taken
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(requireContext()),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(exc: ImageCaptureException) {
                        Log.e(CameraManager.TAG, "Photo capture failed: ${exc.message}", exc)
                    }

                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        val msg = "Photo capture succeeded: ${output.savedUri}"
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        setFacePictureOnStorage(output.savedUri!!)

                    }
                })

        } else {

            val contentValues = ContentValues()
            contentValues.put(
                MediaStore.MediaColumns.DISPLAY_NAME,
                CameraManager.name
            )
            println("FILENAME: ${CameraManager.name}")
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            val outputPath = when {
                saveToCameraRoll -> null
                outputPath != null -> outputPath
                else -> {
                    val out = File.createTempFile("vrades-", ".jpg", requireContext().cacheDir)
                    out.deleteOnExit();
                    out.canonicalPath
                }
            }

            val outputFile: File?

            val outputOptions = if (saveToCameraRoll) {
                ImageCapture.OutputFileOptions
                    .Builder(
                        requireContext().contentResolver,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                    )
                    .build()
            } else {
                outputFile = File(outputPath!!)
                ImageCapture.OutputFileOptions
                    .Builder(outputFile)
                    .build()
            }
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(ContextUtils.getActivity(requireContext())!!),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(ex: ImageCaptureException) {
                        Log.e(
                            CameraManager.TAG,
                            "CameraView: Photo capture failed: ${ex.message}",
                            ex
                        )
                    }

                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        try {
                            val uri = output.savedUri

                            setFacePictureOnStorage(uri!!)

                            Log.d(
                                CameraManager.TAG,
                                "CameraView: Photo capture succeeded: ${output.savedUri}"
                            )


                        } catch (ex: Exception) {
                            Log.e(
                                CameraManager.TAG,
                                "Error while saving or decoding saved photo: ${ex.message}",
                                ex
                            )

                        }


                    }
                })
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

    private fun switchCamera() {
        //change the cameraSelector
        cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) {
            CameraSelector.DEFAULT_BACK_CAMERA
        } else {
            CameraSelector.DEFAULT_FRONT_CAMERA
        }
        // restart the camera
        startCamera()
    }

    private fun roundTheNumber(numInDouble: BigDecimal): String {

        return "%.35f".format(numInDouble)

    }

    companion object {
        fun newInstance() = FaceDetectionFragment()

        @SuppressLint("StaticFieldLeak")
        private lateinit var instance: FaceDetectionFragment
        fun getInstance(): FaceDetectionFragment {
            return instance
        }

        private const val TAG = "CameraXApp"
        var isOffline = false
        const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        const val REQUEST_CODE_PERMISSIONS = 10

    }

}