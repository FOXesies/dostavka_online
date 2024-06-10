package com.wayplaner.learn_room.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.wayplaner.learn_room.admin.basic_info.data.repository.BasicInfoImpl
import com.wayplaner.learn_room.admin.basic_info.domain.model.BasicInfoResponse
import com.wayplaner.learn_room.admin.basic_info.domain.repository.BasicInfoRepository
import com.wayplaner.learn_room.admin.infoorder.data.repository.InfoOrderApiImpl
import com.wayplaner.learn_room.admin.infoorder.domain.repository.AdminInfoOrderApi
import com.wayplaner.learn_room.admin.menu.data.model.ResponseProduct
import com.wayplaner.learn_room.admin.menu.data.repository.MenuProductImpl
import com.wayplaner.learn_room.admin.menu.domain.repository.MenuProductRepository
import com.wayplaner.learn_room.admin.orders.data.repository.AdminOrderImpl
import com.wayplaner.learn_room.admin.orders.domain.repository.AdminOrderApi
import com.wayplaner.learn_room.auth.data.repository.AuthCustomerRepositoryImpl
import com.wayplaner.learn_room.auth.domain.repository.AuthCustomerRepository
import com.wayplaner.learn_room.basket.data.repository.BasketApiImpl
import com.wayplaner.learn_room.basket.domain.repository.BasketApi
import com.wayplaner.learn_room.createorder.data.repository.OrderApiImpl
import com.wayplaner.learn_room.createorder.domain.repository.OrderApi
import com.wayplaner.learn_room.di.deserializer.BasicInfoDeserializer
import com.wayplaner.learn_room.di.deserializer.OrderDEserialezer
import com.wayplaner.learn_room.di.deserializer.OrganizationIdDTODeserializer
import com.wayplaner.learn_room.di.deserializer.ProductDeserializer
import com.wayplaner.learn_room.di.deserializer.ProductResponseDeserializer
import com.wayplaner.learn_room.favotitea.data.reposirory.FavoriteApiImpl
import com.wayplaner.learn_room.favotitea.domain.repository.FavoriteApi
import com.wayplaner.learn_room.feedbasks.data.repository.FeedbacksApiImpl
import com.wayplaner.learn_room.feedbasks.domain.repository.FeedbackApi
import com.wayplaner.learn_room.home.data.repository.HomeApiRepositoryImpl
import com.wayplaner.learn_room.home.domain.model.Image
import com.wayplaner.learn_room.home.domain.repository.HomeApi
import com.wayplaner.learn_room.order_info.data.repository.InfoOrderIMpl
import com.wayplaner.learn_room.order_info.domain.repository.InfoOrderApi
import com.wayplaner.learn_room.orderlist.data.repository.ListOrderImpl
import com.wayplaner.learn_room.orderlist.domain.repository.ListOrderApi
import com.wayplaner.learn_room.organization.data.repository.OrganizationApiImpl
import com.wayplaner.learn_room.organization.domain.repository.OrganizationApi
import com.wayplaner.learn_room.organization.model.OrganizationIdDTO
import com.wayplaner.learn_room.product.data.repository.ProductRepositoryImpl
import com.wayplaner.learn_room.product.domain.model.Product
import com.wayplaner.learn_room.product.domain.repository.ProductRepository
import com.wayplaner.learn_room.settings.data.repository.SettingsUserApiIMpl
import com.wayplaner.learn_room.settings.domain.repository.SettingsUserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.Base64
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val BASE_URL = "http://192.168.0.103:8080/api/v1/"

    private val gsonOrd: Gson = GsonBuilder()
        .registerTypeAdapter(Image::class.java, ImageDeserializer())
        .create()

    private val gsonOrder: Gson = GsonBuilder()
        .registerTypeAdapter(Image::class.java, ImageDeserializer())
        .registerTypeAdapter(OrganizationIdDTO::class.java, OrderDEserialezer())
        .registerTypeAdapter(Product::class.java, ProductDeserializer())
        .create()

    private val gsonProductsAdmin: Gson = GsonBuilder()
        .registerTypeAdapter(Image::class.java, ImageDeserializer())
        .registerTypeAdapter(ResponseProduct::class.java, ProductResponseDeserializer())
        .create()

    private val gsonOrdId: Gson = GsonBuilder()
        .registerTypeAdapter(Image::class.java, ImageDeserializer())
        .registerTypeAdapter(OrganizationIdDTO::class.java, OrganizationIdDTODeserializer())
        .registerTypeAdapter(Product::class.java, ProductDeserializer())
        .create()

    private val gsonOrdBasic: Gson = GsonBuilder()
        .registerTypeAdapter(Image::class.java, ImageDeserializer())
        .registerTypeAdapter(BasicInfoResponse::class.java, BasicInfoDeserializer())
        .create()

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
    fun likeRepository(mainService: FavoriteApi) = FavoriteApiImpl(mainService, gsonOrd, gsonProductsAdmin)


    @Singleton
    @Provides
    fun providelikeApiService(retrofit: Retrofit): FavoriteApi = retrofit.create(FavoriteApi::class.java)

    @Singleton
    @Provides
    fun homeRepository(mainService: HomeApi) = HomeApiRepositoryImpl(mainService, gsonOrd)

    @Singleton
    @Provides
    fun organizationRepository(organizationService: OrganizationApi) = OrganizationApiImpl(organizationService, gsonOrdId)

    @Singleton
    @Provides
    fun provideBasketApiService(retrofit: Retrofit): BasketApi = retrofit.create(
        BasketApi::class.java)

    @Singleton
    @Provides
    fun basketRepository(basketApi: BasketApi) = BasketApiImpl(basketApi, gsonOrdId)

    @Provides
    @Singleton
    fun provideOrderListApiService(retrofit: Retrofit) = retrofit.create(ListOrderApi::class.java)

    @Singleton
    @Provides
    fun provideOrderList(listOrderApi: ListOrderApi) = ListOrderImpl(listOrderApi)

    @Provides
    @Singleton
    fun provideSettingApiService(retrofit: Retrofit) = retrofit.create(SettingsUserApi::class.java)

    @Singleton
    @Provides
    fun provideSetting(listOrderApi: SettingsUserApi) = SettingsUserApiIMpl(listOrderApi)

    @Provides
    @Singleton
    fun provideFeedApiService(retrofit: Retrofit) = retrofit.create(FeedbackApi::class.java)

    @Singleton
    @Provides
    fun provideFeed(feedbackApi: FeedbackApi) = FeedbacksApiImpl(feedbackApi)

    @Singleton
    @Provides
    fun provideOrganizationApiService(retrofit: Retrofit): OrganizationApi = retrofit.create(
        OrganizationApi::class.java)

    @Singleton
    @Provides
    fun productRepository(productRepository: ProductRepository) = ProductRepositoryImpl(productRepository, gsonOrd)

    @Singleton
    @Provides
    fun provideProductApiService(retrofit: Retrofit): ProductRepository = retrofit.create(
        ProductRepository::class.java)

    @Singleton
    @Provides
    fun ordersInfoApi(orderApi: InfoOrderApi) = InfoOrderIMpl(orderApi, gsonOrder)

    @Singleton
    @Provides
    fun ordersInfoApiService(retrofit: Retrofit): InfoOrderApi = retrofit.create(
        InfoOrderApi::class.java)

    @Singleton
    @Provides
    fun orderRepository(orderApi: OrderApi) = OrderApiImpl(orderApi)

    @Singleton
    @Provides
    fun provideOrderApiService(retrofit: Retrofit): OrderApi = retrofit.create(
        OrderApi::class.java)

    @Singleton
    @Provides
    fun admin_basicInfoRepository(basicInfoRepository: BasicInfoRepository) = BasicInfoImpl(basicInfoRepository, gsonOrdBasic)

    @Singleton
    @Provides
    fun admin_basicInfoService(retrofit: Retrofit): BasicInfoRepository = retrofit.create(
        BasicInfoRepository::class.java)

    @Singleton
    @Provides
    fun admin_menuProductRepository(menuProductRepository: MenuProductRepository) = MenuProductImpl(menuProductRepository, gsonProductsAdmin, gsonOrd)

    @Singleton
    @Provides
    fun admin_menuProductService(retrofit: Retrofit): MenuProductRepository = retrofit.create(
        MenuProductRepository::class.java)

    @Singleton
    @Provides
    fun admin_OrderRepository(orderRepository: AdminOrderApi) = AdminOrderImpl(orderRepository)

    @Singleton
    @Provides
    fun admin_OrderService(retrofit: Retrofit): AdminOrderApi = retrofit.create(
        AdminOrderApi::class.java)

    @Singleton
    @Provides
    fun admin_OrderInfoRepository(adminInfoOrderApi: AdminInfoOrderApi) = InfoOrderApiImpl(adminInfoOrderApi, gsonOrder)

    @Singleton
    @Provides
    fun admin_OrderInfoService(retrofit: Retrofit): AdminInfoOrderApi = retrofit.create(
        AdminInfoOrderApi::class.java)

    @Singleton
    @Provides
    fun authRepository(authCustomerRepository: AuthCustomerRepository) = AuthCustomerRepositoryImpl(authCustomerRepository)

    @Singleton
    @Provides
    fun authService(retrofit: Retrofit): AuthCustomerRepository = retrofit.create(
        AuthCustomerRepository::class.java)
}

class ImageDeserializer : JsonDeserializer<Image> {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Image {
        val jsonObject = json.asJsonObject
        val id = jsonObject.get("id").asLong
        val value = jsonObject.get("value").asString
        val main = jsonObject.get("main").asBoolean
        val byteArray = Base64.getDecoder().decode(value)
        return Image(id, byteArray, main)
    }
}