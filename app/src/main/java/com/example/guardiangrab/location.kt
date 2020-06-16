package com.example.guardiangrab

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class location : AppCompatActivity() {
    var textViewResult: TextView? = null
    var jsonPlaceHolderApi: JsonPlaceHolderAPI? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        textViewResult = findViewById(R.id.textViewResult)

        getSmsDetailsFromApi()




    }


    fun getSmsDetailsFromApi() {
        val builder = Retrofit.Builder()
        builder.baseUrl("https://guardiangrab.lifeklix.com/")
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder
            .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI::class.java)

        var sms = jsonPlaceHolderApi!!.getLocationFromApi()
        sms!!.enqueue(object : Callback<List<LocationModel?>?> {
            override fun onResponse(call: Call<List<LocationModel?>?>, response: Response<List<LocationModel?>?>) {
                if (!response.isSuccessful) {
                    //println("SMS DATA " + sms.toString())
                }
                val locationData = response.body()!!

                for (post in locationData) {
                        var content = ""
                        //content += "\nId :" + post!!.getId()
                        content += "\nLatitude : " + post!!.getLatitude()
                        content += "\nLongitude : " + post.getLongitude()
                        content += "\nArea : " + post.getArea()
                        content += "\nDate and time : " + post.getDate()
                        content += "\n\n\n"
                        textViewResult!!.append(content)
                    }
            }


            override fun onFailure(call: Call<List<LocationModel?>?>, t: Throwable) {
                textViewResult!!.append("Failure = " + t.message)
            }
        })

    }






}
