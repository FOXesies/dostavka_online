package com.wayplaner.learn_room.createorder.presentation.components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wayplaner.learn_room.R
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.SearchFactory

class MapActivity : AppCompatActivity() {
    private lateinit var map: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        MapKitFactory.setApiKey("1e8845c3-b492-4939-9356-2fe8447e8dcb");

        SearchFactory.initialize(this)
        MapKitFactory.initialize(this)

        map = findViewById(R.id.map_org)
    }
    override fun onStop() {
        map.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        map.onStart()
    }


}