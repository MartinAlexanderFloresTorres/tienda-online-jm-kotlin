package com.example.tiendaonline

import ImageAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiendaonline.databinding.FragmentProductBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    // Obtén el ViewModel usando una fábrica que pase ApiService
    private val viewModel: ProductViewModel by viewModels {
        ProductViewModelFactory(RetrofitClient.apiService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val slug = it.getString("productSlug")
            viewModel.fetchProductBySlug(slug?: "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observa los cambios en el producto y actualiza la UI
        viewModel.product.observe(viewLifecycleOwner, Observer { product ->
            if (product != null) {
                binding.productTitle.text = product.nombre
                binding.productDescription.text = product.descripcion
                binding.productPrice.text = "S/. " + product.precio

                // Configurar RecyclerView para las imágenes
                val imageUrls = product.galeria.map { it.secure_url }
                val imageAdapter = ImageAdapter(imageUrls)
                binding.productGalery.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.productGalery.adapter = imageAdapter

                // Configurar botón de WhatsApp
                binding.buttonWhatsapp.setOnClickListener {
                    val url = "https://wa.me/929254912?text=Hello"
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(url)
                    }
                    startActivity(intent)
                }
            } else {
                // Manejo de errores o mensaje cuando no se encuentra el producto
                binding.productTitle.text = "Producto no encontrado"
            }
        })

        // Configurar botón de cerrar
        binding.buttonClose.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(productSlug: String) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("productSlug", productSlug)
                }
            }
    }
}
