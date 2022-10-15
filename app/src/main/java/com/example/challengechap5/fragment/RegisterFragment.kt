package com.example.challengechap5.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.challengechap5.R
import com.example.challengechap5.databinding.FragmentRegisterBinding
import com.example.challengechap5.viewModel.ViewModelUser
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgBtnRegister.setOnClickListener {
            postUser()
        }
        binding.tvHaveAnAccount.setOnClickListener {
            gotoLogin()
        }
    }

    fun postUser(){
        var createAt = ""
        var name = binding.etInputUsernameRegister.text.toString().trim()
        var email = binding.etInputEmailRegister.text.toString().trim()
        var password = binding.etInputPasswordRegister.text.toString().trim()
        var rePassword = binding.etInputRePasswordRegister.text.toString().trim()

        if (name.length == 0 && email.length == 0 && password.length == 0 && rePassword.length == 0){
            val warningRegis = "Semua Field harus Ter-isi"
            binding.tvWarningRegister.text = warningRegis
        }else{
            if (password.equals(rePassword)){
                val viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
                viewModel.postApiUser(createAt,email,0,password,name)
                viewModel.postLiveDataUser().observe(requireActivity(), Observer{
                    if (it != null){
                        gotoLogin()
                        Toast.makeText(context,"Register Success", Toast.LENGTH_SHORT).show()
                    }
                })
            }else{
                val txtConfirmWrong = "Password & RePassword tidak sama"
                binding.tvWarningRegister.text = txtConfirmWrong
            }
        }
    }

    fun gotoLogin(){
        Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment)
    }
}