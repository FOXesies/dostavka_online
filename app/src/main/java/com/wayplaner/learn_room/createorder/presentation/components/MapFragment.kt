package com.wayplaner.learn_room.createorder.presentation.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wayplaner.learn_room.createorder.util.AddressPick
import com.wayplaner.learn_room.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.search.SearchFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment: Fragment() {
    private var binding_: FragmentMapBinding? = null
    //private lateinit var addressChangeViewModel: AddressSuggestModelView
    private val binding get() = binding_!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //super.onCreate(savedInstanceState

        //MapKitFactory.setApiKey("1e8845c3-b492-4939-9356-2fe8447e8dcb")

        binding_ = FragmentMapBinding.inflate(inflater, container, false)

        SearchFactory.initialize(binding_!!.root.context)
        MapKitFactory.initialize(binding_!!.root.context)

        AddressPick.address.observe(AddressPick.lifecycle!!) {
            if (it != null) {
                setupMap(it.lat!!, it.lon!!)
            }
        }

        return binding.root
    }
    override fun onStop() {
        binding.mapOrg.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    fun setupMap(latitude: Double, longitude: Double) {
        binding.mapOrg.map.move(
            CameraPosition(Point(latitude, longitude), 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 1F),
            null
        )

        val mapObjects = binding.mapOrg.map.mapObjects
        mapObjects.addPlacemark(Point(latitude, longitude))
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapOrg.onStart()
    }
}