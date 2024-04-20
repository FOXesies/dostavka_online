
package com.wayplaner.learn_room

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.widget.addTextChangedListener
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
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
import com.yandex.mapkit.search.SuggestOptions
import com.yandex.mapkit.search.SuggestSession
import com.yandex.mapkit.search.SuggestType
import com.yandex.runtime.Error
import com.yandex.runtime.ui_view.ViewProvider

class MapSearchActivity : Activity(), Session.SearchListener,
    CameraListener, SuggestSession.SuggestListener, GeoObjectTapListener, InputListener {

    val DEFAULT_POINT = Point(59.945933, 30.320045)

    private val SEARCH_OPTIONS = SuggestOptions().setSuggestTypes(
        SuggestType.GEO.value or
                SuggestType.BIZ.value or
                SuggestType.TRANSIT.value
    )

    private var suggestSession: SuggestSession? = null
    private var placemark: PlacemarkMapObject? = null
    private var mapObjects: MapObjectCollection? = null
    private lateinit var mapView: MapView
    private lateinit var location: Point
    private lateinit var searchEdit: AutoCompleteTextView
    private var searchManager: SearchManager? = null
    private var searchSession: Session? = null
    private lateinit var suggestResult: MutableList<String>
    private var liquid_address = false;

    private val BOX_SIZE = 0.2

    private val BOUNDING_BOX = BoundingBox(
        Point(DEFAULT_POINT.latitude - BOX_SIZE, DEFAULT_POINT.longitude - BOX_SIZE),
        Point(DEFAULT_POINT.latitude + BOX_SIZE, DEFAULT_POINT.longitude + BOX_SIZE)
    )

    private fun submitQuery(query: String) {
        searchSession = searchManager!!.submit(
            query,
            VisibleRegionUtils.toPolygon(mapView!!.map.visibleRegion),
            SearchOptions(),
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_map_search)

        searchEdit = findViewById(R.id.searchView_for_map)

        searchEdit.addTextChangedListener {
            requestSuggest(getQuery())
        }

        val my_geo_b = findViewById<Button>(R.id.my_geo_maps)
        my_geo_b.setOnClickListener {
        }
        super.onCreate(savedInstanceState)

        mapView = findViewById(R.id.mapView_my)
        mapView.map.addCameraListener(this);
        mapView.map.addTapListener(this);
        mapView.map.addInputListener(this);

        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED);
        suggestSession = searchManager!!.createSuggestSession();
        suggestResult = ArrayList();

        intent.getStringExtra("address").let {
            if (!it.isNullOrEmpty()) {
                searchEdit.setText(it)
            }
        }

    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    fun initAutoComplete(auto_edittext: AutoCompleteTextView, list: List<String>) {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.isHorizontalScrollBarEnabled = true
                textView.movementMethod = ScrollingMovementMethod()
                textView.setOnClickListener {
                    auto_edittext.setText(textView.text)
                    auto_edittext.setSelection(auto_edittext.text.length)
                    submitQuery(auto_edittext.text.toString())
                }
                return view
            }
        }
        auto_edittext.setAdapter(adapter)

        auto_edittext.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                auto_edittext.showDropDown()
            }
        }
    }

    override fun onSearchResponse(response: Response) {
        val firstResult = response.collection.children.firstOrNull()?.obj ?: return
        location = firstResult.geometry[0].point ?: return

        mapObjects = mapView.map.mapObjects
        mapView.map.move(CameraPosition(location, 16.5f, 150.0f, 30.0f))

        liquid_address = true
        createPlacemark(location)

    }

    override fun onSearchError(error: Error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onResponse(@NonNull items: List<SuggestItem>) {
        suggestResult.clear()
        if(getQuery().isEmpty())
            return
        for (i in 0 until Math.min(5, items.size)) {
            items[i].displayText?.let { suggestResult.add(it) }
        }
        suggestResult?.let { initAutoComplete(searchEdit, it) }
    }

    override fun onError(p0: Error) {
        Toast.makeText(this, p0.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun requestSuggest(query: String) {
        suggestSession!!.suggest(query, BOUNDING_BOX, SEARCH_OPTIONS, this);
    }

    override fun onCameraPositionChanged(
        map: Map,
        cameraPosition: CameraPosition,
        cameraUpdateReason: CameraUpdateReason,
        finished: Boolean
    ) {}

    fun save_exit(view: View) {
        if (liquid_address) {
            finish()
        } else {
            var dialog_confirm_address = AlertDialog.Builder(this)
            dialog_confirm_address.create()
            dialog_confirm_address.setTitle("Адрес не сохранится")
            dialog_confirm_address.setMessage("Адрес не сохранится, так как введён неверно. \nЧтобы он сохранился найдите место на карте")
            dialog_confirm_address.setPositiveButton("Выйти") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
                finish()
            }
            dialog_confirm_address.setNegativeButton("Остаться") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            dialog_confirm_address.show()

        }
    }

    override fun onObjectTap(geoObjectTapEvent: GeoObjectTapEvent): Boolean {
        liquid_address = true
        val selectionMetadata: GeoObjectSelectionMetadata = geoObjectTapEvent
            .geoObject
            .metadataContainer
            .getItem(GeoObjectSelectionMetadata::class.java)


        if (selectionMetadata != null) {
            mapView.map.selectGeoObject(selectionMetadata)
            createPlacemark(geoObjectTapEvent.geoObject.geometry[0].point!!)
        }

        return selectionMetadata != null
    }

    fun createPlacemark(location: Point){

        if(placemark != null && !placemark!!.geometry.equals(location)){
            placemark!!.setGeometry(location);
        }
        else {
            val view = View(this)
            view.background = getDrawable(R.drawable.me_placemark)
            placemark = mapView.map.mapObjects.addPlacemark(location, ViewProvider(view))!!
            placemark!!.isDraggable = true
        }
    }

    fun setValueSearchText(address: String) { searchEdit.setText(address)}

    fun getQuery(): String {
        return searchEdit.text.toString()
    }

    override fun onMapTap(p0: Map, p1: Point) {
        mapView.map.deselectGeoObject();
    }

    override fun onMapLongTap(p0: Map, p1: Point) {
        TODO("Not yet implemented")
    }
}
