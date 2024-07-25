package com.example.tiendaonline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiendaonline.databinding.FragmentStoreBinding
import kotlinx.coroutines.launch

class StoreFragment : Fragment() {
    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    private lateinit var productAdapter: ProductAdapter
    private var productList = mutableListOf<Product>()
    private var currentPage = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView
        productAdapter = ProductAdapter(productList) { product ->
            val action = StoreFragmentDirections.actionStoreFragmentToProductDetailFragment(product.url)
            findNavController().navigate(action)
        }
        binding.recyclerViewProducts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }

        // Configurar los botones de paginación
        binding.btnPrevious.setOnClickListener {
            loadProducts(currentPage - 1)
        }

        binding.btnNext.setOnClickListener {
            loadProducts(currentPage + 1)
        }

        // Cargar la primera página de productos
        loadProducts(currentPage)
    }

    private fun loadProducts(page: Int) {
        showLoading(true)
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getProducts(page)
                productList.clear()
                productList.addAll(response.docs)
                productAdapter.notifyDataSetChanged()

                // Actualizar el estado de los botones
                currentPage = response.page
                updatePaginationButtons(response)
            } catch (e: Exception) {
                e.printStackTrace()
                // Manejar el error de la llamada a la API
            } finally {
                showLoading(false)
            }
        }
    }

    private fun updatePaginationButtons(response: ProductResponse) {
        binding.btnPrevious.isEnabled = response.hasPrevPage
        binding.btnNext.isEnabled = response.hasNextPage
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
