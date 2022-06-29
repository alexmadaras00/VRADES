package com.example.vrades.presentation.ui.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vrades.databinding.DialogLoadingBinding
import com.example.vrades.databinding.FragmentMyProfileBinding
import com.example.vrades.presentation.interfaces.IOnClickListener
import com.example.vrades.domain.model.Response
import com.example.vrades.domain.model.Test
import com.example.vrades.presentation.ui.adapters.AdapterTestHistory
import com.example.vrades.presentation.ui.binding.setImageUrl
import com.example.vrades.presentation.utils.Constants.ERROR_REF
import com.example.vrades.presentation.viewmodels.MyProfileViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MyProfileFragment : Fragment() {

    private var _binding: FragmentMyProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyProfileViewModel by activityViewModels()
    private var dialog: Dialog? = null
    private var dialogBinding: DialogLoadingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyProfileBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openDialog()
        getUser()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        binding.apply {
            val buttonBack = btnBackProfile
            val buttonEdit = fbtnEditProfilePicture
            val navController = findNavController()
            buttonEdit.setOnClickListener{
                openGallery()
            }
            buttonBack.setOnClickListener {
                navController.navigate(MyProfileFragmentDirections.actionNavProfileToNavHome())
            }
        }
    }

    private fun getUser() {

        viewModel.getUser().observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    binding.apply {
                        val textViewName = tvName
                        val user = it.data
                        val buttonAnalysis = btnDiary
                        val buttonFirstTest = btnGoDetectProfile
                        val imageViewProfile = civProfilePicture
                        setImageUrl(imageViewProfile, user.image)
                        textViewName.text = user.username
                        configureRecyclerView(user.tests!!)
                        if (user.advices!!.isNotEmpty()) {
                            buttonAnalysis.visibility = View.VISIBLE
                            buttonFirstTest.visibility = View.GONE
                        } else {
                            buttonFirstTest.visibility = View.VISIBLE
                            buttonAnalysis.visibility = View.GONE
                        }
                        buttonAnalysis.setOnClickListener {
                            findNavController().navigate(MyProfileFragmentDirections.actionNavProfileToNavDialog())
                        }
                        buttonFirstTest.setOnClickListener {
                            findNavController().navigate(MyProfileFragmentDirections.actionNavProfileToNavFace())
                        }
                    }
                }
                is Response.Error -> {
                    println(it.message)
                }
                else -> {
                    println(ERROR_REF)
                }
            }
        }
    }

    private fun configureRecyclerView(tests: List<Test>) {
        binding.apply {
            val recyclerViewTestHistory = rvTestResults
            val onClickListener = object : IOnClickListener {
                override fun onItemClick(position: Int) {
                    if (tests[position].isCompleted == false) {
                        when (tests[position].state) {
                            0 -> {
                                findNavController().navigate(MyProfileFragmentDirections.actionNavProfileToNavHome())
                            }
                            1 -> {
                                findNavController().navigate(MyProfileFragmentDirections.actionNavProfileToNavAudio())
                            }
                            2 -> {
                                findNavController().navigate(MyProfileFragmentDirections.actionNavProfileToNavWriting())
                            }
                            else -> {

                            }
                        }
                    } else findNavController().navigate(MyProfileFragmentDirections.actionNavProfileToNavDetails())
                }

            }
            val adapterTestHistory = AdapterTestHistory(onClickListener)
            adapterTestHistory.setDataSource(tests as ArrayList<Test>)
            recyclerViewTestHistory.adapter = adapterTestHistory
            recyclerViewTestHistory.layoutManager = LinearLayoutManager(context)
            recyclerViewTestHistory.setHasFixedSize(true)
            recyclerViewTestHistory.isScrollContainer = true
            recyclerViewTestHistory.hasNestedScrollingParent()
            dismissDialog() // closing the dialog
        }
    }

    private val pickImageFromGalleryForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data!!.data
                println(uri)
                setProfilePictureOnStorage(uri!!)
                binding.civProfilePicture.setImageURI(uri)
            }
        }

    private fun openGallery() {
        val photoIntent = Intent(Intent.ACTION_PICK)
        photoIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        pickImageFromGalleryForResult.launch(photoIntent)

    }

    private fun setProfilePictureOnStorage(picture: Uri) {
        openDialog()
        viewModel.setProfilePictureInStorage(picture)
            .observe(viewLifecycleOwner) {
                when (it) {
                    is Response.Success -> {
                        val uri = it.data
                        println(uri)
                        updateProfilePictureInRealtime(uri)
                    }
                    is Response.Error -> {
                        println(it.message)
                    }
                    else -> {
                        println(ERROR_REF)
                    }
                }
            }
    }

    private fun updateProfilePictureInRealtime(picture: String) {
        viewModel.updateProfilePictureInRealtime(picture).observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Profile picture successfully updated!",
                        Toast.LENGTH_SHORT
                    ).show()

                    dismissDialog()
                }
                is Response.Error -> {
                    println(ERROR_REF)
                }
                else -> {
                    println(ERROR_REF)
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


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        fun newInstance() = MyProfileFragment()
        const val IMAGE_REQUEST_CODE = 100
    }


}