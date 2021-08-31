package com.dongdong999.cwm_layout

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongdong999.cwm_layout.databinding.FragmentBlank2Binding
import com.dongdong999.cwm_layout.databinding.ItemRecyclerBinding
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



        //안됨 자꾸 null참조
       /* var rating = view.findViewById<TextView>(R.id.recyclerItem_rating)
        rating?.setOnClickListener {
            Log.d("TAG","일단눌림림")
       }*/

        adapter.setItemClickListener(object:CustomAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                //여기에 클릭시 이벤트

                Log.d("TAG"," 난 눌리고있음-> 이름 : ${data[position].name} 별점 : ${data[position].rating}")
                Toast.makeText(activity,"its work",Toast.LENGTH_SHORT).show()

                val builder = AlertDialog.Builder(activity)
                val dialogView = layoutInflater.inflate(R.layout.rating_layout,null)
                //val dialogText = dialogView.findViewById<EditText>(R.id.dialogEt)
                val dialogRatingBar = dialogView.findViewById<RatingBar>(R.id.dialogRb)
                builder.setView(dialogView).setPositiveButton("확인"){
                    dialogInterface,i->
                    data[position].rating=dialogRatingBar.rating.toInt()
                    adapter.notifyDataSetChanged()
                }
                    .setNegativeButton("취소"){dialogInterface,i->}
                    .show()
               // adapter.notifyDataSetChanged()
            }
        })







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
                    Excelitems.add(Data(name,phone,lon,lat,address,rate,0))
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

