package com.dongdong999.cwm_layout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongdong999.cwm_layout.databinding.FragmentBlank2Binding
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import java.io.InputStream


class BlankFragment2 : Fragment() {
    var Excelitems: MutableList<Data> = mutableListOf()
    lateinit var binding: FragmentBlank2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentBlank2Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data:MutableList<Data> = readExel()
        val adapter=CustomAdapter()
        adapter.listData=data
        binding.recyclerViewList.adapter=adapter
        binding.recyclerViewList.layoutManager=LinearLayoutManager(activity)
    }

    //엑셀읽기기
   fun readExel(): MutableList<Data> {
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
                    var rate=0
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
                    Excelitems.add(Data(name,phone,lon,lat,address,rate))
                }
                rowno++
            }
            Log.d("TAG", " items: " + Excelitems)
        } catch (e: Exception) {
            Toast.makeText(activity, "에러 발생", Toast.LENGTH_LONG).show()
        }

        return Excelitems

    }
}

