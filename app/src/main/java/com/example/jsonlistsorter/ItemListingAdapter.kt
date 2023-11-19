package com.example.jsonlistsorter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.serialization.*

@Serializable
data class ItemListing(val id: Int, val listId: Int, val name: String? = "")

class ItemListAdapter(context: Context, itemListings: List<ItemListing>) : ArrayAdapter<ItemListing>(context, 0, itemListings) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get the data item for this position
        var viewToBuild = convertView

        // Check if an existing view is being reused, otherwise inflate the view
        if (viewToBuild == null) {
            viewToBuild = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        // Get the data item for this position
        val itemListing: ItemListing? = getItem(position)

        // Lookup view for data population
        val listIdText = viewToBuild!!.findViewById<View>(R.id.listIdView) as TextView
        val nameText = viewToBuild.findViewById<View>(R.id.nameView) as TextView
        val itemIdText = viewToBuild.findViewById<View>(R.id.itemIdView) as TextView

        // Populate the data into the template view using the data object
        if (itemListing != null) {
            listIdText.text =  context.getString(R.string.list_id_label,itemListing.listId)
            nameText.text = context.getString(R.string.name_label,itemListing.name)
            itemIdText.text = context.getString(R.string.item_id_label,itemListing.id)
        }
        // Return the completed view to render on screen
        return viewToBuild
    }
}