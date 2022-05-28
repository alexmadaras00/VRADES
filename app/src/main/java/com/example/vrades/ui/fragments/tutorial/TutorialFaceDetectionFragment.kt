package com.example.vrades.ui.fragments.tutorial

import android.Manifest
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
import android.view.animation.AnimationUtils
import android.widget.ImageView
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
import androidx.fragment.app.activityViewModels
import com.example.vrades.R
import com.example.vrades.databinding.FragmentTutorialFaceDetectionBinding
import com.example.vrades.ui.fragments.FaceDetectionFragment
import com.example.vrades.ui.fragments.VradesBaseFragment
import com.example.vrades.viewmodels.TutorialViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class TutorialFaceDetectionFragment : VradesBaseFragment() {
    // TODO: Rename and change types of parameters
    private val viewModel: TutorialViewModel by activityViewModels()
    private var _binding: FragmentTutorialFaceDetectionBinding? = null
    val binding get() = _binding!!
    private lateinit var safeContext: Context
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var outputDirectory: File
    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
    }

    override fun connectViewModelEvents() {

    }

    override fun disconnectViewModelEvents() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorialFaceDetectionBinding.inflate(inflater)
        binding.viewModel = viewModel

        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val viewModel = viewModel

        binding.apply {
            val imageViewArrow = ivArrowTutorial
            val buttonCamera = fbtnCamera
            val buttonSwitch = btnChangeCamera
            startAnimate(imageViewArrow)
            if (allPermissionsGranted())
                startCamera()
            else ActivityCompat.requestPermissions(
                requireActivity(),
                FaceDetectionFragment.REQUIRED_PERMISSIONS,
                FaceDetectionFragment.REQUEST_CODE_PERMISSIONS
            )
            buttonCamera.setOnClickListener {
                takePhoto()
                viewModel.setCurrentStateData(0)
                viewModel.onNextPageClicked()
            }
            buttonSwitch.setOnClickListener {
                if (isReadyCameraTutorial)
                    switchCamera()

            }
            outputDirectory = getOutputDirectory()
            cameraExecutor = Executors.newSingleThreadExecutor()
        }
    }

    private fun startAnimate(imageViewArrow: ImageView) {
        val animUpDown = AnimationUtils.loadAnimation(
            context,
            R.anim.arrow_animation
        );
        imageViewArrow.startAnimation(animUpDown);
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



    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.vfCameraPreviewTutorial.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()
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
            isReadyCameraTutorial = true
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

                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults) {
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

    private fun getOutputDirectory(): File {
        val mediaDir = activity?.externalMediaDirs?.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else activity?.filesDir!!
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(safeContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        fun newInstance() = FaceDetectionFragment()
        private const val TAG = "CameraXApp"
        var isOffline = false
        var isReadyCameraTutorial = false
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        _binding = null
    }
}