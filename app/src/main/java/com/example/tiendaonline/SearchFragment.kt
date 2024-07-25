    package com.example.tiendaonline

    import android.os.Bundle
    import android.text.Editable
    import android.text.TextWatcher
    import android.view.KeyEvent
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.view.inputmethod.EditorInfo
    import android.widget.Toast
    import androidx.fragment.app.Fragment
    import androidx.lifecycle.lifecycleScope
    import androidx.navigation.fragment.findNavController
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.example.tiendaonline.databinding.FragmentSearchBinding
    import kotlinx.coroutines.launch

    class SearchFragment : Fragment() {
        private var _binding: FragmentSearchBinding? = null
        private val binding get() = _binding!!

        private lateinit var productAdapter: ProductAdapter
        private var productList = mutableListOf<Product>()
        private var currentPage = 1
        private var totalPages = 1
        private var searchQuery = ""

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflar el layout para este fragmento
            _binding = FragmentSearchBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            // Configurar el RecyclerView
            productAdapter = ProductAdapter(productList) { product ->
                val action = SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(product.url)
                findNavController().navigate(action)
            }
            binding.recyclerViewSearchResults.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = productAdapter
            }

            binding.editTextSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // No se necesita implementar en este caso
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    searchQuery = s.toString()
                    if (searchQuery.isEmpty()) {
                        productList.clear()
                        productAdapter.notifyDataSetChanged()
                        binding.btnPrevious.isEnabled = false
                        binding.btnNext.isEnabled = false
                        currentPage = 1
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // No se necesita implementar en este caso
                }
            })

            binding.editTextSearch.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (searchQuery.isNotEmpty()) {
                        currentPage = 1
                        searchProducts(searchQuery, currentPage)
                    } else {
                        showAlert("El término de búsqueda no puede estar vacío.")
                    }
                    true
                } else {
                    false
                }
            }

            binding.btnSearch.setOnClickListener {
                if (searchQuery.isNotEmpty()) {
                    currentPage = 1
                    searchProducts(searchQuery, currentPage)
                } else {
                    showAlert("El término de búsqueda no puede estar vacío.")
                }
            }

            binding.btnClear.setOnClickListener {
                binding.editTextSearch.text.clear()
            }

            // Configurar los botones de paginación
            binding.btnPrevious.setOnClickListener {
                if (currentPage > 1) {
                    searchProducts(searchQuery, currentPage - 1)
                }
            }

            binding.btnNext.setOnClickListener {
                if (currentPage < totalPages) {
                    searchProducts(searchQuery, currentPage + 1)
                }
            }
        }

        private fun searchProducts(query: String, page: Int) {
            if (query.isEmpty()) {
                showAlert("El término de búsqueda no puede estar vacío.")
                return
            }
            showLoading(true)
            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.apiService.searchProducts(query, page)
                    val products = response.docs
                    productList.clear()
                    productList.addAll(products)
                    productAdapter.notifyDataSetChanged()

                    // Actualizar el estado de la paginación
                    totalPages = response.totalPages
                    currentPage = response.page
                    updatePaginationButtons(response)
                } catch (e: Exception) {
                    e.printStackTrace()
                    showAlert("No se encontraron productos")

                    // Limpiar productos y actualizar el adaptador
                    productList.clear()
                    productAdapter.notifyDataSetChanged()
                    binding.btnPrevious.isEnabled = false
                    binding.btnNext.isEnabled = false
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

        private fun showAlert(message: String) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
