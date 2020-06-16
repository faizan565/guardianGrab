package com.example.guardiangrab

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.guardiangrab.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class details : AppCompatActivity() {
    var textViewResult: TextView? = null
    var jsonPlaceHolderApi: JsonPlaceHolderAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        textViewResult = findViewById(R.id.textViewResult)

        val builder = Retrofit.Builder()
        builder.baseUrl("https://guardiangrab.lifeklix.com/")
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder
            .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI::class.java)



//        viewPager.get(0).setOnClickListener(View.OnClickListener {
//            getPost()
//        })
//
        getPost()

     //   getSmsDetailsFromApi()



        /* val fab: FloatingActionButton = findViewById(R.id.fab)

         fab.setOnClickListener { view ->
             Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 .setAction("Action", null).show()
         }*/
    }

    fun getSmsDetailsFromApi() {
        textViewResult?.append("---------getting sms log from api----------------")



        var sms = jsonPlaceHolderApi!!.getSmsDetails()

            sms!!.enqueue(object : Callback<List<SmsLogModel?>?> {
                override fun onResponse(call: Call<List<SmsLogModel?>?>, response: Response<List<SmsLogModel?>?>) {
                    if (!response.isSuccessful) {
                        //println("SMS DATA " + sms.toString())
                        textViewResult?.append("---------Success----------------")
                        textViewResult!!.append("Code = " + response.code())
                    }
                    val smsData = response.body()!!
                    for (post in smsData) {
                        var content = ""
                        //content += "\nId :" + post!!.getId()
                        content += "\nPhone Number: " + post!!.getPhoneNumber()
                        content += "\nMessage: " + post.getBody()
                        content += "\nSMS Date: " + post.getSmsDate()
                        content += "\nContact Name: " + post.getContactName()
                        content += "\n\n"
                        textViewResult!!.append(content)
                    }
                }


            override fun onFailure(call: Call<List<SmsLogModel?>?>, t: Throwable) {
                textViewResult?.append("---------FAILURE----------------")
                textViewResult!!.append("Failure = " + t.message)
            }
        })

    }


    fun getPost() {
        val call = jsonPlaceHolderApi!!.getPost()
        call!!.enqueue(object : Callback<List<CallLogModel?>?> {


            override fun onResponse(call: Call<List<CallLogModel?>?>, response: Response<List<CallLogModel?>?>)
            {
                if (!response.isSuccessful) {
                    textViewResult!!.text = "code " + response.code()

                }
                val callData = response.body()!!
                for (post in callData) {
                    var content = ""
                    //    content += "\nId :" + post!!.getId()
                    content += "\nPhone Number: " + post!!.getPhoneNumber()
                    content += "\nCall Type: " + post!!.getCallType()
                    content += "\nCall Duration: " + post!!.getCallDuration()
                    content += "\nCall Date: " + post.getCallDate()
                    content += "\nContact Name: " + post.getContactName()
                    content += "\n\n"
                    textViewResult!!.append(content)
                }
            }


            override fun onFailure(call: Call<List<CallLogModel?>?>, t: Throwable) {
                textViewResult!!.text = t.message
            }
        })
    }

}