package com.wayplaner.learn_room.createorder.presentation.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wayplaner.learn_room.databinding.FragmentMapBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.search.SearchFactory

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


        return binding.root
    }
    override fun onStop() {
        binding.mapOrg.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapOrg.onStart()
    }
}