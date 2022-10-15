package com.example.challengechap5.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.challengechap5.R
import com.example.challengechap5.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var judul = arguments?.getString("title")
        var thumbnail = arguments?.getString("thumbnailUrl")

        binding.tvTitleDetailContent.text = judul
        Glide.with(this).load(thumbnail).into(binding.ivImageDetail)

        binding.ivEditContent.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("update",id!!.toInt())
            Navigation.findNavController(it).navigate(R.id.action_detailFragment_to_updateFragment,bundle)
        }
        binding.ivDeleteContent.setOnClickListener {
            val bundle = Bundle()
            val builder = AlertDialog.Builder(context)
            // set title
            builder.setTitle("Delete Content")
            // set message
            builder.setMessage("Are You Sure?")

            builder.setPositiveButton("Yes"){
                dialogInterface, which -> bundle.putInt("delete",id!!.toInt())
                Toast.makeText(context,"delete", Toast.LENGTH_SHORT).show()
            }
            builder.setNeutralButton("Cancel"){
                dialogInterface, which -> Toast.makeText(context,"cancel", Toast.LENGTH_SHORT).show()
            }

            // create alert dialog
            val alertDialog: AlertDialog =  builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }
}