package com.wayplaner.learn_room.createorder.presentation.components

import android.content.Context
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.createorder.util.MapKitConstant
import com.wayplaner.learn_room.createorder.util.UiEventSuggest
import com.wayplaner.learn_room.organization.model.CityOrganization
import com.yandex.mapkit.directions.driving.DrivingRouter
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.GeoObjectSelectionMetadata
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.VisibleRegionUtils
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManager
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.search.SuggestItem
import com.yandex.mapkit.search.SuggestSession
import com.yandex.mapkit.search.SuggestSession.SuggestListener
import com.yandex.runtime.Error
import com.yandex.runtime.ui_view.ViewProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AddressSuggestModelView @Inject constructor(
): ViewModel(), SuggestListener, Session.SearchListener, GeoObjectTapListener, InputListener {
    private var suggestSession: SuggestSession? = null
    private var searchManager: SearchManager? = null
    private var directionsFactory: DrivingRouter? = null


    private var placemark: PlacemarkMapObject? = null
    private lateinit var map: MapView
    private lateinit var context: Context

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery

    private var routesCollection: MapObjectCollection? = null

    val mutableSuggestState = mutableStateOf(listOf<CityOrganization>())

    private var addressValid = false
    private var addressTab = false

    private val uiEventSuggest_ = MutableLiveData<UiEventSuggest>(UiEventSuggest.NoActive("Введите адрес"))
    val uiEventSuggest: LiveData<UiEventSuggest> = uiEventSuggest_

    private val curAddressTo_ = MutableLiveData(CityOrganization(null, null))
    companion object {

        private val addressTo_ = MutableLiveData(CityOrganization(null, null))
        val addressTo: LiveData<CityOrganization> = addressTo_
        private var cityPick: String? = null

        fun removeAddressTo(){
            addressTo_.postValue(CityOrganization(null, null))
        }
        fun setPickCity(city: String){
            cityPick = city
        }
    }

    fun setMap(map_: MapView){
        map = map_
        routesCollection = map.map.mapObjects.addCollection();

        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)
        suggestSession = searchManager!!.createSuggestSession();
        //map.map.addInputListener(this)
        map.map.addTapListener(this);
    }
    fun setContext(context_: Context){
        addressValid = false
        addressTab = false
        context = context_
    }

    fun clearSuggest(){
        mutableSuggestState.value = emptyList()
    }
    fun addressValid(): Boolean{
        return addressValid
    }
    fun saveAddress(){
        addressTo_.postValue(curAddressTo_.value)
    }
    fun isAddressTab(): Boolean{
        return addressTab
    }
    /*fun enableListAddress(): Boolean{
        addressValid = falsr
    }*/

    fun findAddress(address: String){
        if(address.isEmpty()){
            setValueUi(UiEventSuggest.NoActive("Адрес пустой"))
            return
        }

        val endAddress = /*if (cityPick != null)
                "$cityPick $address"
            else*/
                address
        _searchQuery.value = endAddress
        suggestSession!!.suggest(endAddress, MapKitConstant.BOUNDING_BOX, MapKitConstant.SEARCH_OPTIONS, this);
    }
    fun submitQuery(query: String?) {

        addressTab = false
        val searchSession = searchManager!!.submit(
            query!!,  //Geometry.fromPoint(new Point(62.123650766666664, 129.7368956)),
            VisibleRegionUtils.toPolygon(map.map.visibleRegion),
            SearchOptions(),
            this
        )
    }

    override fun onResponse(p0: MutableList<SuggestItem>) {
        if(p0.isEmpty()){
            setValueUi(UiEventSuggest.NoFound("Мы не нашли похожих результатов"))
            mutableSuggestState.value = emptyList()
            return
        }

        mutableSuggestState.value = p0.mapNotNull { if(!it.displayText.isNullOrEmpty() && it.center != null) { CityOrganization(it.displayText, Point(it.center!!.latitude, it.center!!.longitude))
        } else null }
        setValueUi(UiEventSuggest.SuccessSuggest(mutableSuggestState.value.map { it.address!! }))
    }
    fun getSuggestByPosition(position: Int): String {
        val pick = mutableSuggestState.value.elementAtOrNull(position)
        createPlacemark(Point(pick!!.points!!.latitude!!, pick.points!!.longitude!!))
        addressValid = true
        return pick?.address!!
    }
    fun setAddressTo(addressPosition: Int){
        curAddressTo_.value = mutableSuggestState.value[addressPosition]
    }

    private fun setValueUi(event: UiEventSuggest){
        uiEventSuggest_.value = event
    }

    override fun onError(p0: Error) {
        setValueUi(UiEventSuggest.NoActive(p0.toString()))
    }
    override fun onSearchResponse(p0: Response) {
        if (p0.collection.children.isNotEmpty()) {
            val firstResult = p0.collection.children[0];
            _searchQuery.value = firstResult.obj!!.descriptionText + firstResult.obj!!.name
        }
    }

    override fun onSearchError(p0: Error) {
        TODO("Not yet implemented")
    }

    fun createPlacemark(location: Point){

        if(placemark != null && !placemark!!.geometry.equals(location)){
            placemark!!.setGeometry(location);
        }
        else {
            val view = View(context)
            view.background = getDrawable(context, R.drawable.me_placemark)
            placemark = map.map.mapObjects.addPlacemark(location, ViewProvider(view))
            placemark!!.isDraggable = true
        }

        map.map.move(CameraPosition(location, 16.5f, 150.0f, 30.0f))
    }

    override fun onObjectTap(p0: GeoObjectTapEvent): Boolean {
        addressValid = true
        addressTab = true
        val selectionMetadata: GeoObjectSelectionMetadata = p0
            .geoObject
            .metadataContainer
            .getItem(GeoObjectSelectionMetadata::class.java)


        if (selectionMetadata != null) {
            map.map.selectGeoObject(selectionMetadata)
            p0.geoObject.geometry[0]
            createPlacemark(p0.geoObject.geometry[0].point!!)
            searchManager!!.submit(p0.geoObject.geometry[0].point!!, 20, SearchOptions(), this)
        }

        return selectionMetadata != null

    }

    override fun onMapTap(p0: Map, p1: Point) {
        /*searchManager!!.submit(p1, 20, SearchOptions(), this)*/

    }

    override fun onMapLongTap(p0: Map, p1: Point) {
        TODO("Not yet implemented")
    }

}