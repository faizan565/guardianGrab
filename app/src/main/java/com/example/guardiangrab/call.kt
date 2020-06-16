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

class call : AppCompatActivity() {
    var textViewResult: TextView? = null
    var jsonPlaceHolderApi: JsonPlaceHolderAPI? = null
    public var list=ArrayList<CallLogModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)
        val builder = Retrofit.Builder()
        builder.baseUrl("https://guardiangrab.lifeklix.com/")
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder
            .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI::class.java)
        textViewResult = findViewById(R.id.textViewResult)
        textViewResult?.append("")
        getPost()


      //  getSmsDetailsFromApi()
       // Thread.sleep(5000)



//        print(list.size)
//        for(l in list) {
//            if (l.getUsername().equals(username)) {
//                var content = ""
//                //    content += "\nId :" + post!!.getId()
//                content += "\nPhone Number : " + l!!.getPhoneNumber()
//                content += "\nCall Type : " + l!!.getCallType()
//                content += "\nCall Duration : " + l!!.getCallDuration()
//                content += "\nCall Date : " + l.getCallDate()
//                content += "\nContact Name : " + l.getContactName()
//                content += "\n\n\n"
//            }
//
//        }

//        getSmsDetailsFromApi()





        /* val fab: FloatingActionButton = findViewById(R.id.fab)

         fab.setOnClickListener { view ->
             Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 .setAction("Action", null).show()
         }*/
    }

    fun getPost() {
        var sp:SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)

        var username: String? = sp.getString("username", "phone")



        val call = jsonPlaceHolderApi!!.getPost()
        call!!.enqueue(object : Callback<List<CallLogModel?>?> {


            override fun onResponse(call: Call<List<CallLogModel?>?>, response: Response<List<CallLogModel?>?>)
            {
                if (!response.isSuccessful) {

                }
                val callData = response.body()!!

                for (post in callData) {
                    if(username.equals(post!!.getUsername())){
                        var content=""
                        content += "\nPhone Number : " + post!!.getPhoneNumber()
                        content += "\nCall Type : " + post!!.getCallType()
                        content += "\nCall Duration : " + post!!.getCallDuration()
                        content += "\nCall Date : " + post.getCallDate()
                        content += "\nContact Name : " + post.getContactName()
                        content += "\n\n\n"
                        textViewResult!!.append(content)


                    }



                }
            }


            override fun onFailure(call: Call<List<CallLogModel?>?>, t: Throwable) {
            }
        })
    }


}