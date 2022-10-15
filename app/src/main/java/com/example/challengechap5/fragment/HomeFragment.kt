package com.example.challengechap5.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengechap5.R
import com.example.challengechap5.adapter.AdapterContent
import com.example.challengechap5.databinding.FragmentHomeBinding
import com.example.challengechap5.model.ResponseDataContentItem
import com.example.challengechap5.network.RetrofitClient
import com.example.challengechap5.viewModel.ViewModelContent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var adapterContent: AdapterContent
    lateinit var modelContent: ViewModelContent
    lateinit var dataUserShared: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //show list film
        showList()
        dataUserShared = requireActivity().getSharedPreferences("dataUser",Context.MODE_PRIVATE)

        // VM
        modelContent = ViewModelProvider(this).get(ViewModelContent::class.java)
        modelContent.allLiveData().observe(viewLifecycleOwner, Observer {
            adapterContent.setData(it as ArrayList<ResponseDataContentItem>)
        })

        // Rv
        adapterContent = AdapterContent(ArrayList())
        binding.rvItem.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        binding.rvItem.adapter = adapterContent

        // Navigate to fragment Add
        binding.fbAddItem.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }

        binding.tvLogoutHome.setOnClickListener {
            clearData()
            Toast.makeText(context, "Logout Berhasil", Toast.LENGTH_SHORT).show()
            goLogin()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun showList(){
        RetrofitClient.instance.getAllContent()
            .enqueue(object: Callback<List<ResponseDataContentItem>>{
                override fun onResponse(
                    call: Call<List<ResponseDataContentItem>>,
                    response: Response<List<ResponseDataContentItem>>
                ) {
                    if (response.isSuccessful){
                        binding.rvItem.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                        binding.rvItem.adapter = AdapterContent(response.body()!!)
                        Toast.makeText(context,"Load Data Success", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context, "Load Data Failed", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<ResponseDataContentItem>>, t: Throwable) {
                    Toast.makeText(context,"Something Wrong", Toast.LENGTH_LONG).show()
                }

            })
    }

    fun clearData(){
        var pref = dataUserShared.edit()
        pref.clear()
        pref.apply()
    }

    fun goLogin(){
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment)
    }

}