package com.dongdong999.cwm_layout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dongdong999.cwm_layout.databinding.FragmentBlank2Binding
import com.dongdong999.cwm_layout.databinding.ItemRecyclerBinding

class CustomAdapter:RecyclerView.Adapter<Holder>() {
    var listData= mutableListOf<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
       val data= listData.get(position)
        holder.setData(data)
    }

    override fun getItemCount(): Int {
       return listData.size
    }
}
class Holder(val binding: ItemRecyclerBinding ):RecyclerView.ViewHolder(binding.root){
    fun setData(data:Data){
        binding.recyclerItemName.text="${data.name}"
        binding.recyclerItemPhone.text="${data.number}"
        binding.recyclerItemAddress.text="${data.address}"
        binding.recyclerItemRating.text="${data.rating}"
    }
}
