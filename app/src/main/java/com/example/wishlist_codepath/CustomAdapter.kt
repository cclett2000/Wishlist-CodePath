package com.example.wishlist_codepath

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle.parent
import java.text.NumberFormat

class CustomAdapter(private val itemList: MutableList<Model>): RecyclerView.Adapter<CustomAdapter.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                // holds list items
                val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent, false)
                return ViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val model = itemList[position]
                Log.d("postion", position.toString())

                holder.itemView.setOnClickListener {
                        showOverflowMenu(holder.itemView.context, holder.itemView, holder.absoluteAdapterPosition)
                }

                val formatter: NumberFormat = NumberFormat.getNumberInstance()
                formatter.minimumFractionDigits = 2
                formatter.maximumFractionDigits = 2
                val formattedPrice: String = formatter.format(model.price)

                // sets text to the textView from itemHolder class?
                holder.itemName.text = model.itemName
                holder.storeName.text = model.storeName
                holder.price.text = "$$formattedPrice"
        }

        override fun getItemCount(): Int {
                return itemList.size
        }

        private fun showOverflowMenu(context: Context, view: View, position: Int) {
                val menu = PopupMenu(context, view)

                menu.menu.apply {
                        add("Delete Item").setOnMenuItemClickListener {
                                Log.d("PostionRemoved", position.toString())
                                itemList.removeAt(position)
                                notifyItemRemoved(position)
                                true
                        }
                }

                menu.show()
        }

        // support class
        class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
                val itemName: TextView = itemView.findViewById(R.id.recycler_item_name_view)
                val storeName: TextView = itemView.findViewById(R.id.recycler_store_name_view)
                val price: TextView = itemView.findViewById(R.id.recycler_price_view)
        }
}