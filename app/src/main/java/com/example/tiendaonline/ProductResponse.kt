package com.example.tiendaonline

data class ProductResponse(
    val docs: List<Product>, // Lista de productos
    val totalDocs: Int, // Total de documentos
    val limit: Int, // Límite de documentos por página
    val totalPages: Int, // Total de páginas
    val page: Int, // Página actual
    val pagingCounter: Int, // Contador de paginación
    val hasPrevPage: Boolean, // Indica si hay una página anterior
    val hasNextPage: Boolean, // Indica si hay una página siguiente
    val prevPage: Int?, // Página anterior (puede ser null)
    val nextPage: Int? // Página siguiente (puede ser null)
)
