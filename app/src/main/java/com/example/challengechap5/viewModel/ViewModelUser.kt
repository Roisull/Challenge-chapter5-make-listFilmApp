package com.example.challengechap5.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengechap5.model.ResponseDataUserItem
import com.example.challengechap5.network.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ViewModelUser: ViewModel() {
    lateinit var addLiveDataUser: MutableLiveData<ResponseDataUserItem?>

    init {
        addLiveDataUser = MutableLiveData()
    }

    fun postLiveDataUser(): MutableLiveData<ResponseDataUserItem?>{
        return addLiveDataUser
    }

    fun postApiUser(createAt: String, email: String, id: Int, password: String, username: String){
        RetrofitClient.instance.postUser(ResponseDataUserItem(createAt,email,"",password,username))
            .enqueue(object : retrofit2.Callback<ResponseDataUserItem> {
                override fun onResponse(
                    call: Call<ResponseDataUserItem>,
                    response: Response<ResponseDataUserItem>
                ) {
                    if (response.isSuccessful){
                        addLiveDataUser.postValue(response.body())
                    }else{
                        addLiveDataUser.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseDataUserItem>, t: Throwable) {
                    addLiveDataUser.postValue(null)
                }

            })
    }
}
