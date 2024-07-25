package com.example.tiendaonline

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val _id: String = "",
    val creador: String = "",
    val nombre: String = "",
    val url: String = "",
    val marca: String = "",
    val unidades: Int = 0,
    val genero: String = "",
    val descuento: String = "",
    val precio: Double = 0.0,
    val ventas: Int = 0,
    val coleccion: String = "",
    val tallas: List<Talla> = listOf(),
    val colores: List<Color> = listOf(),
    val caracteristicas: List<Caracteristica> = listOf(),
    val descripcion: String = "",
    val informacion: String = "",
    val portadas: List<Imagen> = listOf(),
    val galeria: List<Imagen> = listOf(),
    val createdAt: String = "",
    val updatedAt: String = "",
    val __v: Int = 0
) : Parcelable


@Parcelize
data class Talla(
    val id: String,
    val talla: String
) : Parcelable

@Parcelize
data class Color(
    val id: String,
    val color: String
) : Parcelable

@Parcelize
data class Caracteristica(
    val id: String,
    val caracteristica: String
) : Parcelable

@Parcelize
data class Imagen(
    val asset_id: String,
    val public_id: String,
    val secure_url: String,
    val original_filename: String
) : Parcelable
