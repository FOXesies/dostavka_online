
package com.wayplaner.learn_room

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.wayplaner.learn_room.createorder.presentation.components.AddressSuggestModelView
import com.wayplaner.learn_room.createorder.util.UiEventSuggest
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.search.SuggestItem
import com.yandex.mapkit.search.SuggestSession
import com.yandex.runtime.Error
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapSearchActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var location: Point
    private lateinit var searchEdit: SearchView
    private var placemark: PlacemarkMapObject? = null
    private lateinit var listAddress: ListView

    private var mapObjects: MapObjectCollection? = null
    private lateinit var modelViewSuggest: AddressSuggestModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map_search)
        mapView = findViewById(R.id.mapView_my)

        modelViewSuggest = ViewModelProvider(this)[AddressSuggestModelView::class.java]
        modelViewSuggest.setMap(mapView)
        modelViewSuggest.setContext(this)

        init()
    }

    private fun showInfo(message: String){
        replaceViewItem()
    }
    private fun replaceViewItem(){
        listAddress.visibility = View.GONE
    }
    private fun updateAddress(address: List<String>){
        listAddress.adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, address)
    }
    private fun initObserve(){
        modelViewSuggest.uiEventSuggest.observe(this){
            when(it){
                is UiEventSuggest.NoActive -> showInfo(it.message)
                is UiEventSuggest.NoFound -> showInfo(it.message)
                is UiEventSuggest.SuccessSuggest -> updateAddress(it.listAddress)
            }
        }
    }

    private fun init(){
        listAddress = findViewById(R.id.list_address)
        searchEdit = findViewById(R.id.searchView_for_map)
        addLogicEditText(searchEdit)
        initObserve()

        listAddress.setOnItemClickListener { parent, view, position, id ->

            modelViewSuggest.setAddressTo(position)

            val pickedAddress = modelViewSuggest.getSuggestByPosition(position)
            searchEdit.setQuery(pickedAddress, true)
            modelViewSuggest.submitQuery(pickedAddress)
            //searchEdit.setSelection(pickedAddress.length)

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

    private fun addLogicEditText(et: SearchView){

        modelViewSuggest.searchQuery.observe(this) { newValue ->
            et.setQuery(newValue, false)
        }

        et.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                replaceViewItem()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.
                if(!modelViewSuggest.isAddressTab()) {
                    listAddress.visibility = View.VISIBLE
                    modelViewSuggest.findAddress(newText.toString())
                }

                return false
            }
        })
    }

    fun save_exit(view: View) {
        if (modelViewSuggest.addressValid()) {
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
}
