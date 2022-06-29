package com.example.vrades.presentation.ui.fragments.tutorial

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.vrades.R
import com.example.vrades.presentation.utils.camerax.CameraManager
import com.example.vrades.presentation.utils.camerax.CameraManager.Companion.isReadyCamera
import com.example.vrades.databinding.FragmentTutorialFaceDetectionBinding
import com.example.vrades.presentation.ui.fragments.FaceDetectionFragment
import com.example.vrades.presentation.ui.fragments.VradesBaseFragment
import com.example.vrades.presentation.viewmodels.TutorialViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class TutorialFaceDetectionFragment : VradesBaseFragment() {

    private val viewModel: TutorialViewModel by activityViewModels()
    private var _binding: FragmentTutorialFaceDetectionBinding? = null
    val binding get() = _binding!!
    private lateinit var safeContext: Context
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var outputDirectory: File
    private lateinit var cameraManager: CameraManager

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
        cameraManager =
            CameraManager(requireActivity(), binding.vfCameraPreviewTutorial, viewLifecycleOwner)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onStart() {
        super.onStart()

        val viewModel = viewModel
        binding.apply {
            val imageViewArrow = ivArrowTutorial
            val buttonCamera = fbtnCamera
            val buttonSwitch = btnChangeCamera
            startAnimate(imageViewArrow)
            if (allPermissionsGranted())
                cameraManager.startCamera()

            buttonCamera.setOnClickListener {
                cameraManager.takePhoto()
                viewModel.setCurrentStateData(0)
                viewModel.onNextPageClicked()
            }
            buttonSwitch.setOnClickListener {
                if (isReadyCamera)
                    cameraManager.switchCamera()

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