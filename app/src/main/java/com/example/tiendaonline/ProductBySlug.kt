package com.example.tiendaonline

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductBySlug(
    val _id: String,
    val caracteristicas: List<Caracteristica>,
    val coleccion: Coleccion,
    val colores: List<Color>,
    val creador: Creador,
    val createdAt: String,
    val descripcion: String,
    val descuento: String,
    val galeria: List<Imagen>,
    val genero: String,
    val informacion: String,
    val marca: String,
    val nombre: String,
    val portadas: List<Imagen>,
    val precio: Int,
    val tallas: List<Talla>,
    val unidades: Int,
    val updatedAt: String,
    val url: String,
    val ventas: Int
) : Parcelable

@Parcelize
data class Coleccion(
    val _id: String,
    val nombre: String,
    val url: String
) : Parcelable

@Parcelize
data class Creador(
    val _id: String,
    val nombre: String,
    val empresa: Empresa,
    val imagen: Imagen,
    val email: String
) : Parcelable

@Parcelize
data class Empresa(
    val nombre: String,
    val imagen: Imagen,
    val descripcion: String,
    val direccion: String,
    val pais: String,
    val ciudad: String,
    val codigoPostal: String,
    val productosCreados: Int
) : Parcelable
