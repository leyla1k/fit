package com.example.todolist.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable

import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.example.todolist.R
import com.example.todolist.a.LatestNewsUiState
import com.example.todolist.databinding.FragmentLocationsBinding
import com.example.todolist.a.isPermissionGranted
import com.example.todolist.a.SharedPreferencesProvider
import com.example.todolist.a.onRequestPermissionsResultCheck
import com.example.todolist.a.requestPermission
import com.example.todolist.a.showErrorDialog
import com.example.todolist.databinding.FragmentPrivacyBinding
import com.google.android.datatransport.TransportFactory

import kotlinx.coroutines.launch
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.runtime.image.ImageProvider
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView


class PrivacyFragment : Fragment() {

    private var _binding: FragmentPrivacyBinding? = null
    private val binding get() = _binding!!
lateinit var mapView:MapView
    private val TARGET_POINT_1 = Point(17.22566, -97.987)
    private val TARGET_POINT_2 = Point(18.0, -87.0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrivacyBinding.inflate(inflater, container, false)
        MapKitFactory.initialize(context)
        return binding.root
    }






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.yandexMapView
        // Перемещение камеры к первой целевой точке
        mapView.map.move(
            CameraPosition(TARGET_POINT_1, 14.0f, 0.0f, 0.0f)
        )
        // Проверка разрешений на доступ к местоположению
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            return
        }

        // Проверка разрешений на доступ к местоположению
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        } else {
            // Запрос обновлений местоположения
            requestLocationUpdates()
        }

        // Отметка целевых точек на карте
        addMarker(TARGET_POINT_1, R.drawable.ic_pin)
        addMarker(TARGET_POINT_2, R.drawable.ic_pin)
    }
    private fun requestLocationUpdates() {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
    }
    // Listener для обработки обновлений местоположения
    private val locationListener = LocationListener { location ->
        val userLocation = Point(location.latitude, location.longitude)
        addMarker(userLocation, R.drawable.ic_pin_user)
    }
    // Метод для преобразования Drawable в Bitmap
    private fun getBitmapFromDrawable(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    // Метод для добавления маркера на карту
    private fun addMarker(point: Point, drawableRes: Int) {
        val drawable = ResourcesCompat.getDrawable(resources, drawableRes, null)
        if (drawable != null) {
            val bitmap = getBitmapFromDrawable(drawable)
            val imageProvider = ImageProvider.fromBitmap(bitmap)
            mapView.map.mapObjects.addPlacemark(point, imageProvider)
        } else {
            Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //Log.d("TAGt", "requestCode = $requestCode")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        activity?.onRequestPermissionsResultCheck(requestCode, grantResults)
    }




    companion object {
        private val POINT = Point(55.90536986, 48.95701074)
        private val POSITION = CameraPosition(POINT, 17.0f, 0.0f, 0.0f)
    }
}