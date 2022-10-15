package com.example.challengechap5.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.challengechap5.R
import com.example.challengechap5.databinding.FragmentUpdateBinding
import com.example.challengechap5.viewModel.ViewModelContent

class UpdateFragment : Fragment() {

    lateinit var binding: FragmentUpdateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivUpdateContent.setOnClickListener {
            requireActivity().run {
                var fetID = arguments?.getInt("update",0)
                var title = binding.etUpdateContentTitle.text.toString()
                var desc = binding.etUpdateDeskripsiContent.text.toString()
                var thumb = binding.etUpdateContentThumbnail.text.toString()
                var url = binding.etUpdateUrlContent.text.toString()
                Log.d("infoid",fetID.toString())

                updateFilm(fetID.toString().toInt(),title, desc, thumb, url)

                Navigation.findNavController(it).navigate(R.id.action_updateFragment_to_homeFragment)
            }
        }
    }

    fun updateFilm(id : Int, title : String, description: String, thumbnailUrl: String, url: String){
        var viewModel = ViewModelProvider(this).get(ViewModelContent::class.java)
        viewModel.updateApiContent(id, title, description, thumbnailUrl, url)
        viewModel.updateLiveDataContent().observe(viewLifecycleOwner, Observer{
            if (it != null){
                Toast.makeText(context, "Update Data Success", Toast.LENGTH_SHORT).show()
            }
        })
    }
}