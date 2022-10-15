package com.example.challengechap5.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechap5.R
import com.example.challengechap5.databinding.ItemBinding
import com.example.challengechap5.model.ResponseDataContentItem

class AdapterContent(var itemContent: List<ResponseDataContentItem>): RecyclerView.Adapter<AdapterContent.ViewHolder>() {

    class ViewHolder(val binding: ItemBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @Suppress("RecyclerView")position: Int) {
        Glide.with(holder.itemView).load(itemContent[position].thumbnailUrl).into(holder.binding.ivKonten)
        holder.binding.tvJudulKonten.text = itemContent[position].title

        holder.binding.cvSeeDetail.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("albumId", itemContent[position].albumId.toString())
            bundle.putInt("id",itemContent[position].id)
            bundle.putString("thumbnailUrl",itemContent[position].thumbnailUrl)
            bundle.putString("title",itemContent[position].title)
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_detailFragment,bundle)
        }
    }

    override fun getItemCount(): Int {
        return itemContent.size
    }
    fun setData(data: ArrayList<ResponseDataContentItem>){
        this.itemContent = data
    }
}