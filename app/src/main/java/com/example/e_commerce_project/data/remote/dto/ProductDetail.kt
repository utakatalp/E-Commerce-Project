package com.example.e_commerce_project.data.remote.dto


data class ProductDetail(
    val description: String?,
    val id: Int?,
    val imageOne: String?,
    val imageThree: String?,
    val imageTwo: String?,
    val price: Double?,
    val title: String?,
    val isFavorite: Boolean?,
    val rate: Double?,
    val salePrice: Double?,
    val saleState: Boolean?
) {
    fun getImageList(): List<String?> {
        return listOf(imageOne, imageTwo, imageThree)
    }
}
