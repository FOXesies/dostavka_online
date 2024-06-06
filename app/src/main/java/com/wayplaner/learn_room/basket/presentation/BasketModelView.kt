package com.wayplaner.learn_room.basket.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.basket.data.repository.BasketApiImpl
import com.wayplaner.learn_room.basket.domain.model.SendBasketProduct
import com.wayplaner.learn_room.basket.util.Basketproduct
import com.wayplaner.learn_room.order.data.model.BasketItem
import com.wayplaner.learn_room.order.data.model.IdsProductInBasket
import com.wayplaner.learn_room.product.data.repository.ProductRepositoryImpl
import com.wayplaner.learn_room.product.domain.model.Product
import com.wayplaner.learn_room.utils.CustomerAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BasketModelView @Inject constructor(
    private val basketApiImpl: BasketApiImpl,
    private val productApiImpl: ProductRepositoryImpl
): ViewModel() {

    private val basket_ = MutableLiveData<BasketItem>()
    val basketItem: LiveData<BasketItem?> = basket_

    private val products_ = MutableLiveData<MutableList<Product>>()
    val products : LiveData<MutableList<Product>> = products_

    private val productBasketCount = MutableLiveData<Int>()

    fun parseInfo(){
        viewModelScope.launch {
            products_.value = basket_.value!!.productsPick.map { getProduct(it.product!!) }.toMutableList()
        }
    }

    private suspend fun getProduct(id: Long): Product{
        return productApiImpl.getProductinfo(id).body()!!
    }


    fun setCountInProducts(count: Int) {
        productBasketCount.postValue(count)
    }
    fun isInBasket() = productBasketCount

    fun getInBasket(productId: Long) {
        viewModelScope.launch {
            val response = basketApiImpl.checkInBasket(CustomerAccount.info!!.profileUUID, productId)
            if(response.isSuccessful)
                productBasketCount.postValue(response.body()?.valueInt?: 0)
            else{
                // Обработка ошибки
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    Timber.e(errorBody.string())
                }
            }
        }
    }

    fun saveInfoInOrder(){
        Basketproduct.summ = basketItem.value?.summ
        Basketproduct.city = basketItem.value?.city
        Basketproduct.idOrg = basketItem.value?.idRestoraunt
    }

    fun loadBasket(){
        viewModelScope.launch {
            basket_.value = basketApiImpl.getBasket(CustomerAccount.info!!.profileUUID).body()
        }
    }

    fun replaceAll(orgId: Long, product: Product){
        viewModelScope.launch {
            basketApiImpl.replaceAll(SendBasketProduct(city = CustomerAccount.info!!.city, organziationId = orgId, productId = product.idProduct))

            val item = basket_.value!!.copy()
            item.summ += product.price!!

            basket_.value = item
        }
    }

    fun addProduct(orgId: Long, product: Product) {
        viewModelScope.launch {
            basketApiImpl.addProduct(SendBasketProduct(city = CustomerAccount.info!!.city, organziationId = orgId, productId = product.idProduct))

            val item = basket_.value!!.copy()
            item.summ += product.price!!
            item.productsPick.add(IdsProductInBasket(product.idProduct, 1))
            basket_.value = item
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            basketApiImpl.deleteProduct(SendBasketProduct(organziationId = basket_.value!!.idRestoraunt, productId = product.idProduct))
            productBasketCount.postValue(0)

            val item = basket_.value!!.copy()
            val removedItem = item.productsPick.find { it.product == product.idProduct }
            item.summ -= (product.price!! * removedItem!!.count)
            item.productsPick.remove(removedItem)
            basket_.value = item
        }
    }

    fun plusProduct(product: Product) {
        viewModelScope.launch {
            basketApiImpl.plusProduct(SendBasketProduct(organziationId = basket_.value!!.idRestoraunt, productId = product.idProduct))

            val item = basket_.value!!.copy()
            item.summ += product.price!!
            item.productsPick.find { it.product == product.idProduct }!!.count++
            basket_.value = item
        }
    }

    fun minusProduct(product: Product) {
        viewModelScope.launch {
            basketApiImpl.minusProduct(SendBasketProduct(organziationId = basket_.value!!.idRestoraunt, productId = product.idProduct))

            val item = basket_.value!!.copy()
            item.summ -= product.price!!
            item.productsPick.find { it.product == product.idProduct }!!.count--
            basket_.value = item
        }
    }

}