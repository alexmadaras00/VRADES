package com.example.vrades.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vrades.R
import com.example.vrades.databinding.FragmentFaceDetectionBinding
import com.example.vrades.viewmodels.TestViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class FaceDetectionFragment : Fragment() {

    private var _binding: FragmentFaceDetectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TestViewModel
    private lateinit var safeContext: Context
    private var imageCapture: ImageCapture? = null
    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var outputDirectory: File
    private var cameraProvider: ProcessCameraProvider? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[TestViewModel::class.java]
        _binding = FragmentFaceDetectionBinding.inflate(inflater)
        binding.viewModelTest = viewModel
        binding.executePendingBindings()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        cameraProviderResult.launch(Manifest.permission.CAMERA)
        val state = viewModel.getCurrentState()
        val navController = findNavController()
        binding.apply {
            val buttonCamera = fbtnCamera
            val buttonGallery = fbtnGallery
            val buttonNext = btnNextFace
            val buttonSwitch = binding.btnChangeCamera
            if (allPermissionsGranted())
                startCamera()
            else ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
            buttonCamera.setOnClickListener {
                takePhoto()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    animateFlash()
                }
                buttonCamera.visibility = View.INVISIBLE
                buttonGallery.visibility = View.INVISIBLE
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
            outputDirectory = getOutputDirectory()
            cameraExecutor = Executors.newSingleThreadExecutor()
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

    @SuppressLint("RestrictedApi")
    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            // Preview
            val preview = Preview.Builder().apply {

            }
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
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
            isReadyCamera = true
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.GERMANY)
            .format(System.currentTimeMillis()) + ".jpg"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                requireActivity().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(safeContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            }
        )
    }

    private val cameraProviderResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            if (permissionGranted) {
                // cut and paste the previous startCamera() call here.
                startCamera()
            } else {
                Snackbar.make(
                    binding.root,
                    "The camera permission is required",
                    Snackbar.LENGTH_INDEFINITE
                ).show()
            }
        }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun animateFlash() {
        binding.root.postDelayed({
            binding.root.foreground = ColorDrawable(Color.WHITE)
            binding.root.postDelayed({
                binding.root.foreground = null
            }, 50)
        }, 100)
    }
    private fun getOutputDirectory(): File {
        val mediaDir = activity?.externalMediaDirs?.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else activity?.filesDir!!
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(safeContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun switchCamera() {
        //change the cameraSelector
        cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
            CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            CameraSelector.DEFAULT_BACK_CAMERA
        }
        // restart the camera
        startCamera()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        cameraExecutor.shutdown()
    }

    companion object {
        fun newInstance() = FaceDetectionFragment()
        private const val TAG = "CameraXApp"
        var isOffline = false
        var isReadyCamera = false
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        const val REQUEST_CODE_PERMISSIONS = 10
        val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}