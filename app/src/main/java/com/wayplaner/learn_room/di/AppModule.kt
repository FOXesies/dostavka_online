package com.wayplaner.learn_room.di

import com.wayplaner.learn_room.data.repository.AuthCustomerRepositoryImpl
import com.wayplaner.learn_room.data.repository.HomeApiRepositoryImpl
import com.wayplaner.learn_room.data.repository.ImageApiImpl
import com.wayplaner.learn_room.data.repository.OrganizationApiImpl
import com.wayplaner.learn_room.data.repository.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import com.wayplaner.learn_room.domain.repository.AuthCustomerRepository
import com.wayplaner.learn_room.domain.repository.HomeApi
import com.wayplaner.learn_room.domain.repository.ImageApi
import com.wayplaner.learn_room.domain.repository.OrganizationApi
import com.wayplaner.learn_room.domain.repository.ProductRepository
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
    fun provideOrganizationApiService(retrofit: Retrofit): OrganizationApi = retrofit.create(OrganizationApi::class.java)

    @Singleton
    @Provides
    fun productRepository(productRepository: ProductRepository) = ProductRepositoryImpl(productRepository)

    @Singleton
    @Provides
    fun provideProductApiService(retrofit: Retrofit): ProductRepository = retrofit.create(ProductRepository::class.java)

    @Singleton
    @Provides
    fun imageRepository(imageApi: ImageApi) = ImageApiImpl(imageApi)

    @Singleton
    @Provides
    fun provideImageApiService(retrofit: Retrofit): ImageApi = retrofit.create(ImageApi::class.java)

    @Provides
    @Singleton
    fun authRepository(): AuthCustomerRepository = AuthCustomerRepositoryImpl()

}