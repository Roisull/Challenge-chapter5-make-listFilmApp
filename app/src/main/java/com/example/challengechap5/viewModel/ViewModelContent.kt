package com.example.challengechap5.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengechap5.model.DataContent
import com.example.challengechap5.model.PutResponseContent
import com.example.challengechap5.model.ResponseDataContentItem
import com.example.challengechap5.network.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ViewModelContent: ViewModel() {
    lateinit var allData: MutableLiveData<List<ResponseDataContentItem>>
    lateinit var updateContent: MutableLiveData<List<PutResponseContent>?>
    lateinit var deleteContent: MutableLiveData<Int?>
    lateinit var postContent: MutableLiveData<ResponseDataContentItem>

    init {
        allData = MutableLiveData()
        updateContent = MutableLiveData()
        deleteContent = MutableLiveData()
        postContent = MutableLiveData()
    }

    fun allLiveData(): MutableLiveData<List<ResponseDataContentItem>>{
        return allData
    }
    fun updateLiveDataContent(): MutableLiveData<List<PutResponseContent>?>{
        return updateContent
    }
    fun deleteLiveDataContent(): MutableLiveData<Int?>{
        return deleteContent
    }
    fun addLiveDataContent(): MutableLiveData<ResponseDataContentItem>{
        return postContent
    }

    // GET All Data From API
    fun callAllData(){
        RetrofitClient.instance.getAllContent()
            .enqueue(object : retrofit2.Callback<List<ResponseDataContentItem>> {
                override fun onResponse(
                    call: Call<List<ResponseDataContentItem>>,
                    response: Response<List<ResponseDataContentItem>>
                ) {
                    if (response.isSuccessful){
                        allData.postValue(response.body())
                    }else{
                        error(response.message())
                    }
                }

                override fun onFailure(call: Call<List<ResponseDataContentItem>>, t: Throwable) {
                    allData.postValue(error(t.message.toString()))
                }

            })
    }

    // update Function to API
    fun updateApiContent(id: Int, title: String, description: String, thumbnailUrl: String, url: String){
        RetrofitClient.instance.updateContent(id, DataContent(description,thumbnailUrl,title,url))
            .enqueue(object : retrofit2.Callback<List<PutResponseContent>>{
                override fun onResponse(
                    call: Call<List<PutResponseContent>>,
                    response: Response<List<PutResponseContent>>
                ) {
                    if (response.isSuccessful){
                        updateContent.postValue(response.body())
                    }else{
                        updateContent.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<PutResponseContent>>, t: Throwable) {
                    updateContent.postValue(null)
                }

            })
    }
    fun deleteApiContent(id: Int){
        RetrofitClient.instance.deleteContent(id)
            .enqueue(object : retrofit2.Callback<Int>{
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if (response.isSuccessful){
                        deleteContent.postValue(response.body())
                    }else{
                        deleteContent.postValue(null)
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    deleteContent.postValue(null)
                }

            })
    }

    // POST Data
    fun postData(description: String,thumbnailUrl: String,title: String,url: String){
        RetrofitClient.instance.addContent(DataContent(description,thumbnailUrl,title,url))
            .enqueue(object : retrofit2.Callback<ResponseDataContentItem>{
                override fun onResponse(
                    call: Call<ResponseDataContentItem>,
                    response: Response<ResponseDataContentItem>
                ) {
                    if (response.isSuccessful){
                        postContent.postValue(response.body())
                    }else{
                        error(response.message())
                    }
                }

                override fun onFailure(call: Call<ResponseDataContentItem>, t: Throwable) {
                    postContent.postValue(error(t.message.toString()))
                }

            })

    }
}