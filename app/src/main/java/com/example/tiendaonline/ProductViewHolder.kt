package com.example.tiendaonline

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tiendaonline.databinding.ProductItemBinding

class ProductViewHolder(
    private val binding: ProductItemBinding,
    private val onClick: (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        binding.productTitle.text = product.nombre
        binding.productPrice.text = "S/. ${product.precio}"

        val imageUrl = product.portadas.firstOrNull()?.secure_url
        if (imageUrl != null) {
            Glide.with(binding.productImage.context)
                .load(imageUrl)
                .placeholder(R.drawable.side_nav_bar)
                .error(R.drawable.side_nav_bar)
                .into(binding.productImage)
        } else {
            binding.productImage.setImageResource(R.drawable.side_nav_bar)
        }

        // Configura el botón para navegar al detalle del producto
        binding.productButton.setOnClickListener {
            onClick(product)
        }

    }
}
