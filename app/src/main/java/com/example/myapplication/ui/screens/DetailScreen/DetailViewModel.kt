package com.example.myapplication.ui.screens.DetailScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.api.RetrofitClient
import com.example.myapplication.data.model.Post
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _post = MutableLiveData<Post?>()
    val post: LiveData<Post?> = _post

    fun fetchPostById(postId: Int) {
        viewModelScope.launch {
            RetrofitClient.apiService.getPostById(postId).enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.isSuccessful) {
                        _post.value = response.body()
                    } else {
                        _post.value = null
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    _post.value = null
                }
            })
        }
    }
}