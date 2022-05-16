package com.example.vrades.ui.fragments

import android.app.Activity
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
import com.example.vrades.databinding.FragmentMyProfileBinding
import com.example.vrades.interfaces.IOnClickListener
import com.example.vrades.model.Response
import com.example.vrades.model.Test
import com.example.vrades.ui.adapters.AdapterTestHistory
import com.example.vrades.ui.binding.setImageUrl
import com.example.vrades.utils.Constants
import com.example.vrades.viewmodels.MyProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment : Fragment() {

    private var _binding: FragmentMyProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyProfileViewModel by activityViewModels()
    private lateinit var imagePath: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyProfileBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        getUser()
        binding.apply {
            val buttonAnalysis = btnDiary
            val buttonBack = btnBackProfile
            val buttonEdit = fbtnEditProfilePicture
            val navController = findNavController()

            buttonEdit.setOnClickListener{
                openGallery()
            }
            buttonAnalysis.setOnClickListener {
                navController.navigate(MyProfileFragmentDirections.actionNavProfileToNavDialog())
            }
            buttonBack.setOnClickListener {
                navController.navigate(MyProfileFragmentDirections.actionNavProfileToNavHome())
            }

        }
    }

    override fun onResume() {
        super.onResume()
        getUser()
    }

    private fun getUser() {
        viewModel.getUser().observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    print("Here")
                    binding.apply {
                        val textViewName = tvName
                        val user = it.data
                        val imageViewProfile = civProfilePicture
                        setImageUrl(imageViewProfile, user.image)
                        textViewName.text = user.username
                        configureRecyclerView(user.tests!!)
                    }
                }
                is Response.Error -> {
                    println(Constants.ERROR_REF)
                }
                else -> {
                    println(Constants.ERROR_REF)
                }
            }
        }
    }

    private fun configureRecyclerView(tests: List<Test>) {
        binding.apply {
            val recyclerViewTestHistory = rvTestResults

            val onClickListener = object : IOnClickListener {
                override fun onItemClick(position: Int) {
                    if (!tests[position].isCompleted) {
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
        }
    }

    private val pickImageFromGalleryForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data!!.data
                setProfilePictureOnStorage(uri!!)
            }
        }

    private fun openGallery() {
        val photoIntent = Intent(Intent.ACTION_PICK)
        photoIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        pickImageFromGalleryForResult.launch(photoIntent)
    }

    private fun setProfilePictureOnStorage(picture: Uri) {
        viewModel.setProfilePictureInStorage(picture).observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    updateProfilePictureInRealtime(it.data)
                }
                is Response.Error -> {
                    println(Constants.ERROR_REF)
                }
                else -> {
                    println(Constants.ERROR_REF)
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
                    getUser()
                }
                is Response.Error -> {
                    println(Constants.ERROR_REF)
                }
                else -> {
                    println(Constants.ERROR_REF)
                }
            }
        }
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