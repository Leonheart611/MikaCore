package com.mikaocto.mikacore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mikaocto.mikacore.databinding.InputItemHolderBinding
import com.mikaocto.mikacore.model.InputItem

class InputItemAdapter(val clicklistener: ItemClicklistener) :
    ListAdapter<InputItem, InputItemAdapter.InputItemHolder>(InputItemAdapterDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InputItemHolder {
        return InputItemHolder(
            InputItemHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: InputItemHolder, position: Int) {
        getItem(position).let { holder.bind(it) }
    }

    inner class InputItemHolder(
        private val binding: InputItemHolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: InputItem) {
            with(binding) {
                tvItemName.text = "Item Name: ${item.itemName}"
                tvOperatorName.text = "Operator Name: ${item.employeeName}"
                tvBarcodeValue.text = "Machine Code: ${item.barcodeValue}"
                tvInputDate.text = "Input Date: ${item.date} ${item.time}"
                tvQuantity.text = "Quantity: ${item.quantity}"
                ivDelete.setOnClickListener {
                    clicklistener.deleteItem(item)
                }
            }
        }
    }

    class InputItemAdapterDiff : DiffUtil.ItemCallback<InputItem>() {
        override fun areItemsTheSame(oldItem: InputItem, newItem: InputItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: InputItem,
            newItem: InputItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface ItemClicklistener {
        fun deleteItem(item: InputItem)
    }
}