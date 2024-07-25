package com.example.tiendaonline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel(private val apiService: ApiService) : ViewModel() {

    private val _product = MutableLiveData<ProductBySlug>()
    val product: LiveData<ProductBySlug> get() = _product

    fun fetchProductBySlug(slug: String) {
        viewModelScope.launch {
            try {
                val fetchedProduct = apiService.getProductById(slug)
                _product.postValue(fetchedProduct)
            } catch (e: Exception) {
                // Manejo de errores
                e.printStackTrace()
            }
        }
    }
}
