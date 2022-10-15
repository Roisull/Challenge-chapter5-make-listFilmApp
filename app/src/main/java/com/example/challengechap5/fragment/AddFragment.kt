package com.example.challengechap5.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.challengechap5.R
import com.example.challengechap5.databinding.FragmentAddBinding
import com.example.challengechap5.viewModel.ViewModelContent

class AddFragment : Fragment() {
    lateinit var binding: FragmentAddBinding
    lateinit var modelContent: ViewModelContent
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivUploadContentClick.setOnClickListener {
            var description = binding.etInputDeskripsiFragmentAdd.text.toString()
            var thumbnail = binding.etInputThumbnailFragmentAdd.text.toString()
            var title = binding.etInputTitleFragmentAdd.text.toString()
            var url = binding.etInputVideoUrlFragmentAdd.text.toString()
            addItemData(description,thumbnail,title,url)
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }
    }

    fun addItemData(description: String, thumbnail: String, title: String, url: String){
        modelContent = ViewModelProvider(this).get(ViewModelContent::class.java)
        modelContent.postData(description,thumbnail,title,url)
        modelContent.addLiveDataContent().observe(viewLifecycleOwner, Observer {
            if (it != null){
                Toast.makeText(context, "Add Data Success", Toast.LENGTH_LONG).show()
            }
        })

    }
}