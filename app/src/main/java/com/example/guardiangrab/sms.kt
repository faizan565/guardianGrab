package com.example.guardiangrab

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class sms : AppCompatActivity() {
    var textViewResult: TextView? = null
    var jsonPlaceHolderApi: JsonPlaceHolderAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)


        textViewResult = findViewById(R.id.textViewResult)

        val builder = Retrofit.Builder()
        builder.baseUrl("https://guardiangrab.lifeklix.com/")
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder
            .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI::class.java)

        getSmsDetailsFromApi()





        /* val fab: FloatingActionButton = findViewById(R.id.fab)

         fab.setOnClickListener { view ->
             Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 .setAction("Action", null).show()
         }*/
    }


    fun getSmsDetailsFromApi() {

        var sms = jsonPlaceHolderApi!!.getSmsDetails()

        sms!!.enqueue(object : Callback<List<SmsLogModel?>?> {
            override fun onResponse(call: Call<List<SmsLogModel?>?>, response: Response<List<SmsLogModel?>?>) {
                if (!response.isSuccessful) {
                    //println("SMS DATA " + sms.toString())
                    textViewResult!!.append("Code = " + response.code())
                }
                val smsData = response.body()!!
                for (post in smsData) {
                    var sp: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)

                    var username: String? = sp.getString("username", "phone")
                    if (username.equals(post!!.getUsername().toString())) {


                        var content = ""
                        //content += "\nId :" + post!!.getId()
                        content += "\nPhone Number : " + post!!.getPhoneNumber()
                        content += "\nMessage : " + post.getBody()
                        content += "\nSMS Date : " + post.getSmsDate()
                        content += "\nContact Name : " + post.getContactName()
                        content += "\n\n\n"
                        textViewResult!!.append(content)
                    }
                }
            }


            override fun onFailure(call: Call<List<SmsLogModel?>?>, t: Throwable) {
                textViewResult?.append("---------FAILURE----------------")
                textViewResult!!.append("Failure = " + t.message)
            }
        })

    }




}