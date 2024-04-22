package com.wayplaner.learn_room.di

import com.wayplaner.learn_room.auth.data.repository.AuthCustomerRepositoryImpl
import com.wayplaner.learn_room.home.data.repository.HomeApiRepositoryImpl
import com.wayplaner.learn_room.organization.data.repository.OrganizationApiImpl
import com.wayplaner.learn_room.product.data.repository.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import com.wayplaner.learn_room.auth.domain.repository.AuthCustomerRepository
import com.wayplaner.learn_room.basket.data.repository.BasketApiImpl
import com.wayplaner.learn_room.basket.domain.repository.BasketApi
import com.wayplaner.learn_room.createorder.data.repository.OrderApiImpl
import com.wayplaner.learn_room.createorder.domain.repository.OrderApi
import com.wayplaner.learn_room.home.domain.repository.HomeApi
import com.wayplaner.learn_room.organization.domain.repository.OrganizationApi
import com.wayplaner.learn_room.product.domain.repository.ProductRepository
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val BASE_URL = "http://192.168.0.103:8080/api/v1/"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): HomeApi = retrofit.create(HomeApi::class.java)

    @Singleton
    @Provides
    fun homeRepository(mainService: HomeApi) = HomeApiRepositoryImpl(mainService)

    @Singleton
    @Provides
    fun organizationRepository(organizationService: OrganizationApi) = OrganizationApiImpl(organizationService)

    @Singleton
    @Provides
    fun provideBasketApiService(retrofit: Retrofit): BasketApi = retrofit.create(
        BasketApi::class.java)

    @Singleton
    @Provides
    fun basketRepository(basketApi: BasketApi) = BasketApiImpl(basketApi)

    @Singleton
    @Provides
    fun provideOrganizationApiService(retrofit: Retrofit): OrganizationApi = retrofit.create(
        OrganizationApi::class.java)

    @Singleton
    @Provides
    fun productRepository(productRepository: ProductRepository) = ProductRepositoryImpl(productRepository)

    @Singleton
    @Provides
    fun provideProductApiService(retrofit: Retrofit): ProductRepository = retrofit.create(
        ProductRepository::class.java)

    @Singleton
    @Provides
    fun orderRepository(orderApi: OrderApi) = OrderApiImpl(orderApi)

    @Singleton
    @Provides
    fun provideOrderApiService(retrofit: Retrofit): OrderApi = retrofit.create(
        OrderApi::class.java)

    @Provides
    @Singleton
    fun authRepository(): AuthCustomerRepository = AuthCustomerRepositoryImpl()

}