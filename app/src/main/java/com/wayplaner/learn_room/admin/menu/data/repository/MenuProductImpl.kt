package com.wayplaner.learn_room.admin.menu.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wayplaner.learn_room.admin.menu.domain.repository.MenuProductRepository
import com.wayplaner.learn_room.organization.domain.model.ResponseProductOrg
import com.wayplaner.learn_room.product.domain.model.Product
import com.wayplaner.learn_room.product.domain.model.ProductDToUpdate
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class MenuProductImpl(
    private val repository: MenuProductRepository,
    private val gson: Gson,
    private val gsonOrd: Gson
) {
    suspend fun getCategories(id: Long) = repository.getCategories(id)
    suspend fun getAllInfo(idOrg: Long): Response<Map<String, List<ResponseProductOrg>>> {
        val response = repository.getAllInfo(idOrg)
        val responseBody = response.body()?.string()
        val organizations = gson.fromJson<Map<String, List<ResponseProductOrg>>>(responseBody, object : TypeToken<Map<String, List<ResponseProductOrg>>>() {}.type)
        return Response.success(organizations)
    }
    suspend fun getProductinfo(idProduct: Long): Response<Product> {
        val response = repository.getInfo(idProduct)
        val responseBody = response.body()?.string()
        val product = gsonOrd.fromJson(responseBody, Product::class.java)
        return Response.success(product)
    }

    suspend fun uploadInfo(info: ProductDToUpdate) = repository.uploadInfo(info)
    suspend fun updateProduct(images: List<MultipartBody.Part>, product: RequestBody) = repository.uploadImage(images, product)

    /*suspend fun uploadImage(idOrg: Long, image: MultipartBody.Part) = repository.uploadImage(listOf(image, image), idOrg)*/

}