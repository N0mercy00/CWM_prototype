package com.dongdong999.cwm_layout

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import java.io.InputStream
import com.dongdong999.cwm_layout.Data as Data


class BlankFragment : Fragment(), OnMapReadyCallback {

    //구글맵 프래그먼트
    private lateinit var googlemap: MapView
    private lateinit var btnCurrent: Button
    private var cameraPosition: CameraPosition? = null

    var currentLocation : LatLng? = null
    var mLocationManager: LocationManager? = null
    var mLocationListener: android.location.LocationListener? = null

    lateinit var mContext: Context

    private lateinit var gMap : GoogleMap

    var Excelitems: MutableList<Data> = mutableListOf()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_blank, container, false)
        btnCurrent = rootView.findViewById(R.id.btn_currentLocation)
        googlemap = rootView.findViewById(R.id.fragment1_googleMap)
        googlemap.onCreate(savedInstanceState)
        googlemap.getMapAsync(this)

        readExel()

        return rootView
    }

    //지도 초기화
    override fun onMapReady(googleMap: GoogleMap) {
        //마커
        val marker = LatLng(37.568291, 126.997780)
        googleMap.addMarker(MarkerOptions().position(marker).title("여기"))
        gMap=googleMap
        Log.d("TAG", "누르기전 googleMap 값은 ${googleMap.toString()} 들어간 지맵값은 ${gMap.toString()}")

        if(currentLocation!=null){
            cameraPosition = CameraPosition.builder().target(currentLocation)
                .zoom(15.0F).build()
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
            googleMap.moveCamera(cameraUpdate)
        }else{
            cameraPosition = CameraPosition.builder().target(LatLng(37.59215872,126.6759438))
                .zoom(15.0F).build()
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
            googleMap.moveCamera(cameraUpdate)

        }

        //여기다가 엑셀 연동해서 마커찍자

        readExel()
        Log.d("TAG","${Excelitems[0].name}")

        for(i in 0 until Excelitems.size){
            var tempposition=LatLng(Excelitems[i].lon.toDouble(),Excelitems[i].lat.toDouble())
            googleMap.addMarker(MarkerOptions().position(tempposition).title(Excelitems[i].name))
        }







    }


    //이게 지도 만들어 지고 나서 하는 부분
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        mLocationManager = mContext.getSystemService(LOCATION_SERVICE) as LocationManager
        mLocationListener = object : LocationListener, android.location.LocationListener {



            override fun onLocationChanged(p0: Location) {
                var lat = 0.0
                var lng = 0.0
                if (p0 != null) {
                    lat = p0.latitude
                    lng = p0.longitude
                    Log.d("TAG", "Lat: ${lat}, lon: ${lng}")
                }
                currentLocation = LatLng(lat, lng)

                //현재위치 마커찍기
                //gMap!!.addMarker(MarkerOptions().position(currentLocation!!).title("현재위치"))
                Log.d("TAG", "Lat: ${lat}, lon: ${lng}")
                gMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 20f))
            }

        }
        btnCurrent.setOnClickListener {
            Log.d("TAG", "버튼눌림 ${gMap.toString()}")
            if (ContextCompat.checkSelfPermission(
                    mContext,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    mContext,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mLocationManager!!.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    3000,
                    10f,
                    mLocationListener!!

                )
                Log.d("TAG", "위치 새로고침")

                /*gMap.addMarker(MarkerOptions().position(location2!!).title("현재위치"))
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location2, 15f))
                Log.d("TAG", "위치 새로고침2")*/
            }

        }
    }

    override fun onStart() {
        super.onStart()
        googlemap.onStart()
    }

    override fun onStop() {
        super.onStop()
        googlemap.onStop()
    }

    override fun onResume() {
        super.onResume()
        googlemap.onResume()
    }

    override fun onPause() {
        super.onPause()
        googlemap.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        googlemap.onLowMemory()
    }

    override fun onDestroy() {
        googlemap.onDestroy()
        super.onDestroy()
    }

    fun readExel() {
        try {
            val myInput: InputStream
            // assetManager 초기 설정
            val assetManager = resources.assets
            //  엑셀 시트 열기
            myInput = assetManager.open("seogu.xls")
            // POI File System 객체 만들기
            val myFileSystem = POIFSFileSystem(myInput)
            //워크 북
            val myWorkBook = HSSFWorkbook(myFileSystem)
            // 워크북에서 시트 가져오기
            val sheet = myWorkBook.getSheetAt(0)

            //행을 반복할 변수 만들어주기
            val rowIter = sheet.rowIterator()
            //행 넘버 변수 만들기
            var rowno = 0
            //MutableList 생성


            //행 반복문
            while (rowIter.hasNext()) {
                val myRow = rowIter.next() as HSSFRow
                if (rowno != 0) {
                    //열을 반복할 변수 만들어주기
                    val cellIter = myRow.cellIterator()
                    //열 넘버 변수 만들기
                    var colno = 0
                    var name = ""
                    var nmber = ""
                    var lon =""
                    var lat =""
                    var phone =""
                    var address =""
                    //열 반복문
                    while (cellIter.hasNext()) {
                        val myCell = cellIter.next() as HSSFCell
                        if (colno === 0) {
                            name = myCell.toString()
                        }else if (colno==5){
                            address=myCell.toString()
                        } else if (colno === 7) {
                            lon = myCell.toString()
                        }else if(colno==8){
                            lat=myCell.toString()
                        }else if(colno==9){
                            phone=myCell.toString()
                        }
                        colno++
                    }
                    //4,8번째 열을 Mutablelist에 추가
                    Excelitems.add(Data(name,phone,lon,lat,address))
                }
                rowno++
            }
            Log.e("TAG", " items: " + Excelitems)
        } catch (e: Exception) {
            Toast.makeText(activity, "에러 발생", Toast.LENGTH_LONG).show()
        }

    }


}

