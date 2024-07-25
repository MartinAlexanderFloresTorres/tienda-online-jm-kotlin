package com.example.tiendaonline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiendaonline.databinding.FragmentCategoriesBinding
import kotlinx.coroutines.launch


class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryAdapter: CategoryAdapter
    private var categoryList = mutableListOf<Category>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView
        categoryAdapter = CategoryAdapter(categoryList)
        binding.recyclerViewCategories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoryAdapter
        }

        // Cargar las categorías
        loadCategories()
    }

    private fun loadCategories() {
        showLoading(true)
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getColecciones()
                categoryList.clear()
                categoryList.addAll(response)
                categoryAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
                // Manejar el error de la llamada a la API
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
