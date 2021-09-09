package io.jitpack.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class WebService(private val context: Context) {
    fun getData(igetData: IgetData) {
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,
                "https://gorest.co.in/public/v1/posts", null,
                { response ->
                    try {
                        val jsonArray = response.getJSONArray("data")
                        val jsonObject = jsonArray.getJSONObject(3)
                        val title = jsonObject.getString("title")
                        val body = jsonObject.getString("body")
                        igetData.onResponse(title, body)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }) { igetData.onResponse("error", "body error") }
        Volley.newRequestQueue(context).add(jsonObjectRequest)
    }

    interface IgetData {
        fun onResponse(title: String?, body: String?)
    }
}