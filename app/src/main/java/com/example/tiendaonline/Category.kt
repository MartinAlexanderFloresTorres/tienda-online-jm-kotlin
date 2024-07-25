package com.example.tiendaonline

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val _id: String,
    val nombre: String,
    val url: String,
    val imagen: Imagen?,
    val banner: Banner?,
    val productos: List<Product>,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
) : Parcelable

@Parcelize
data class Banner(
    val data: Imagen?,
    val encabezado: String,
    val descripcion: String
) : Parcelable