package com.example.vrades.api.camerax

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.vrades.model.Response
import com.example.vrades.ui.fragments.FaceDetectionFragment
import com.example.vrades.utils.Constants
import com.google.android.material.internal.ContextUtils.getActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraManager(
    private val context: Context,
    private val finderView: PreviewView,
    private val lifecycleOwner: LifecycleOwner
) {
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private var cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
    private var saveToCameraRoll = true
    private var outputPath: String? = null

    init {
        createNewExecutor()
    }

    fun createNewExecutor() {
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    fun shutdownExecutor() {
        cameraExecutor.shutdown()
    }

    fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(finderView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()
            // Select back camera as a default
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()
                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
            isReadyCamera = true
        }, ContextCompat.getMainExecutor(context))
    }


    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("RestrictedApi")
    fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val name =
                "Picture " + SimpleDateFormat(FaceDetectionFragment.FILENAME_FORMAT, Locale.US)
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
                    context.contentResolver,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )
                .build()
            // Create output options object which contains file + metadata

            // Set up image capture listener, which is triggered after photo has
            // been taken
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(exc: ImageCaptureException) {
                        Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                    }

                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        val msg = "Photo capture succeeded: ${output.savedUri}"
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        FaceDetectionFragment.getInstance()?.setFacePictureOnStorage(output.savedUri!!)

                    }
                })

        } else {

            val contentValues = ContentValues()
            contentValues.put(
                MediaStore.MediaColumns.DISPLAY_NAME,
               name
            )
            println("FILENAME: $name")
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            val outputPath = when {
                saveToCameraRoll -> null
                outputPath != null -> outputPath
                else -> {
                    val out = File.createTempFile("vrades-", ".jpg", context.cacheDir)
                    out.deleteOnExit();
                    out.canonicalPath
                }
            }

            var outputFile: File? = null

            val outputOptions = if (saveToCameraRoll) {
                ImageCapture.OutputFileOptions
                    .Builder(
                        context.contentResolver,
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
                ContextCompat.getMainExecutor(getActivity(context)!!),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(ex: ImageCaptureException) {
                        Log.e(TAG, "CameraView: Photo capture failed: ${ex.message}", ex)
                    }

                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        try {
                            val uri = output.savedUri
                            FaceDetectionFragment().setFacePictureOnStorage(uri!!)

                            Log.d(TAG, "CameraView: Photo capture succeeded: ${output.savedUri}")


                        } catch (ex: Exception) {
                            Log.e(
                                TAG,
                                "Error while saving or decoding saved photo: ${ex.message}",
                                ex
                            )

                        }


                    }
                })
        }
    }

    fun switchCamera() {
        //change the cameraSelector
        cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) {
            CameraSelector.DEFAULT_BACK_CAMERA
        } else {
            CameraSelector.DEFAULT_FRONT_CAMERA
        }
        // restart the camera
        startCamera()
    }
    fun setSaveToCameraRoll(enabled: Boolean = true) {
        saveToCameraRoll = enabled
    }



    companion object {
        const val TAG = "CameraXBasic"
        var isReadyCamera = false
        val name =  "IMG_" + System.currentTimeMillis()

    }



}