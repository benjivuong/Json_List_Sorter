package com.example.jsonlistsorter

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private var url = "https://fetch-hiring.s3.amazonaws.com/hiring.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val request = StringRequest(url,
            { string ->
                if (string != null) {
                    parseJsonData(string)
                }
            }) {
            Toast.makeText(applicationContext, "Some error occurred!!", Toast.LENGTH_SHORT)
                .show()
        }

        val rQueue: RequestQueue = Volley.newRequestQueue(this@MainActivity)
        rQueue.add(request)
    }

    private fun parseJsonData(jsonString: String) {
        try {
            val jsonArray = JSONArray(jsonString)
            val sortedList: ArrayList<ItemListing> = ArrayList()
            for (i in 0 until jsonArray.length()) {
                val obj = Json.decodeFromString<ItemListing>(jsonArray.getString(i))
                if (!obj.name.isNullOrEmpty()) {
                    sortedList.add(obj)
                }
            }
            val adapter = ItemListAdapter(this, sortedList.sortedWith(compareBy(ItemListing::listId, ItemListing::name)))
            adapter.notifyDataSetChanged()
            val listView= findViewById<ListView>(R.id.listView1)
            listView.adapter = adapter

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}