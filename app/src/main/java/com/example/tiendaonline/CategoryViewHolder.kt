package com.example.tiendaonline

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tiendaonline.databinding.CategoryItemBinding

class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = CategoryItemBinding.bind(view)

    fun bind(category: Category) {
        binding.categoryTitle.text = category.nombre

        // Usar Glide para cargar la imagen de la categoría desde la URL
        val imageUrl = category.imagen?.secure_url
        if (imageUrl != null) {
            Glide.with(binding.categoryImage.context)
                .load(imageUrl)
                .placeholder(R.drawable.side_nav_bar) // Placeholder en caso de que la imagen tarde en cargar
                .error(R.drawable.side_nav_bar) // Imagen de error si ocurre un problema
                .into(binding.categoryImage) // ImageView donde se cargará la imagen
        } else {
            // Si no hay URL de imagen, puedes ocultar la ImageView o colocar una imagen predeterminada
            binding.categoryImage.setImageResource(R.drawable.side_nav_bar) // Imagen predeterminada
        }
    }
}
