package com.example.myapplication.ui.screens.HomeScreen

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


class HomeViewModel : ViewModel() {

    private val _posts =
        MutableLiveData<List<Post>>() //Declara una variable privada llamada _posts de tipo MutableLiveData<List<Post>>. MutableLiveData es una clase que permite observar cambios en los datos
    val posts: LiveData<List<Post>> get() = _posts //Declara una variable pública llamada posts de tipo LiveData<List<Post>>. LiveData es una versión inmutable de MutableLiveData, lo que significa que la UI puede observar los cambios en posts, pero no puede modificarlos directamente.

    init { //el bloque init llama a la función fetchPosts(). Esto significa que, tan pronto como se crea una instancia del HomeViewModel, se inicia la solicitud a la API para obtener la lista de publicaciones (posts).
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch { //Lanza una corrutina en el ámbito del ViewModel. Esto es importante porque las corrutinas en el viewModelScope se cancelan automáticamente cuando el ViewModel se destruye, evitando fugas de memoria.
            val call = RetrofitClient.apiService.getPosts()
            call.enqueue(object :
                Callback<List<Post>> { //En Retrofit, enqueue() se utiliza para realizar solicitudes de red de forma asíncrona. Esto significa que la solicitud se ejecuta en segundo plano en un hilo diferente al hilo principal de la aplicación (el hilo de la interfaz de usuario).
                override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                    if (response.isSuccessful) {
                        _posts.value = response.body()
                    }
                }

                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    // Handle error
                }
            })
        }
    }
}