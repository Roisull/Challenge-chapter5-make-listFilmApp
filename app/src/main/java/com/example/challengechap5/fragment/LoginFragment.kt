package com.example.challengechap5.fragment

import android.content.ContentValues
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.challengechap5.R
import com.example.challengechap5.databinding.FragmentLoginBinding
import com.example.challengechap5.model.ResponseDataUserItem
import com.example.challengechap5.network.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private var user: ResponseDataUserItem? = null
    lateinit var dataUserShared: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun auth(username: String,password: String){
        RetrofitClient.instance.getAllUser()
            .enqueue(object : retrofit2.Callback<List<ResponseDataUserItem>> {
                override fun onResponse(
                    call: Call<List<ResponseDataUserItem>>,
                    response: Response<List<ResponseDataUserItem>>
                ) {
                    if (response.isSuccessful){
                        var responseBody = response.body()
                        if (responseBody != null) {
                            Log.d(ContentValues.TAG, "onResponse: ${responseBody.toString()}")
                            for (i in 0 until responseBody.size) {
                                if(responseBody[i].username.equals(username) && responseBody[i].password.equals(password)) {
                                    var addData = dataUserShared.edit()
                                    addData.putString("createAt",responseBody[i].createdAt)
                                    addData.putString("email",responseBody[i].email)
                                    addData.putString("id",responseBody[i].id)
                                    addData.putString("password",responseBody[i].password)
                                    addData.putString("username",responseBody[i].username)
                                    addData.apply()
                                    gotoHome()
                                      Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
                                    binding.tvWarningCannotLogin.visibility = View.INVISIBLE
                                } else {
                                    binding.tvWarningCannotLogin.visibility = View.VISIBLE
                                }
                            }
                        }
                    }else{
                        Toast.makeText(context, "Failed to Load Data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<ResponseDataUserItem>>, t: Throwable) {
                    Toast.makeText(context,"Something Wrong", Toast.LENGTH_SHORT).show()
                }

            })
    }
    fun gotoHome(){
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
    }

}