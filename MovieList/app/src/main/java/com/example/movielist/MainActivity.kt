package com.example.movielist


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var requestQueue: RequestQueue
    private var listarray= ArrayList<MovieModel>()
    private lateinit var adapter:MovieListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView=findViewById(R.id.movieList)
        recyclerView.layoutManager=LinearLayoutManager(this)
        requestQueue= VolleySingleton.getInstance(this)!!.getRequestQueue()
        fetchMovie()
        adapter = MovieListAdapter(this,listarray)
        recyclerView.adapter=adapter
    }



    private fun fetchMovie() {
        val url = "https://api.jsonbin.io/v3/b/66b77864ad19ca34f89438b8"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response: JSONObject ->
                try {
                    Log.i("response", response.toString())
                    // Extract the "record" array from the root JSON object
                    val recordArray = response.getJSONArray("record")

                    for (i in 0 until recordArray.length()) {

                        val jsonObject = recordArray.getJSONObject(i)
                        val title = jsonObject.getString("title")
                        val overview = jsonObject.getString("overview")
                        val poster = jsonObject.getString("poster")
                        val rating = jsonObject.getString("rating")

                        val model = MovieModel(title, poster, overview, rating)
                        listarray.add(model)
                        recyclerView.adapter?.notifyDataSetChanged()
                    }



                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.i("message", e.message.toString())
                }
            },
            { error ->
                error.printStackTrace()
                Log.i("error", error.message.toString())
            }
        )

        requestQueue.add(jsonObjectRequest)
    }




}