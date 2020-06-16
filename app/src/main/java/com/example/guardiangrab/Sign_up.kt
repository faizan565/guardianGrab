package com.example.guardiangrab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Sign_up : AppCompatActivity() {
    private var jsonPlaceHolderApi:JsonPlaceHolderAPI?=null
    public var list=ArrayList<SignupModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        getSignUpDataFromApi()
        var editTextName=findViewById<EditText>(R.id.name)
        var editTextPhone=findViewById<EditText>(R.id.phone)
        var editTextEmail=findViewById<EditText>(R.id.email)
        var editTextPassword=findViewById<EditText>(R.id.password)
        var editTextConfirmPassword=findViewById<EditText>(R.id.password1)
        val button=findViewById<Button>(R.id.submit)
        button.setOnClickListener {


            if (editTextName.text.toString().equals("") || editTextEmail.text.toString().equals("") || editTextEmail.text.equals("") || editTextPassword.text.equals("") || editTextConfirmPassword.text.equals(""))
            {
                Toast.makeText(applicationContext, "Please Enter all the fields", Toast.LENGTH_SHORT).show()
            }
            else if (editTextPassword.text.toString() != editTextConfirmPassword.text.toString()) {
                Toast.makeText(applicationContext, "Password must be same", Toast.LENGTH_SHORT).show()
            } else {
                var signUpModel=SignupModel(editTextName.text.toString(),editTextPhone.text.toString(),editTextEmail.text.toString(),editTextPassword.text.toString())
                //getSignUpDataFromApi()
                print(list.size)

                var flag:Boolean=false
                for(l in  list)
                {
                    if(l?.getPhone().equals(editTextPhone.text.toString()))
                    {
                        flag=true
                        break;
                    }
                }
                if(flag==false) {
                    var listForSignUp=ArrayList<SignupModel>()
                    listForSignUp.add(signUpModel)
                    signUpDetailsSend(listForSignUp)
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(applicationContext, "Sign Up successfully", Toast.LENGTH_LONG)
                        .show()
                }else{
                    Toast.makeText(applicationContext, "Already Found a user with this Mobile Number ,Please select differnt phone Number", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }


    fun signUpDetailsSend(list: ArrayList<SignupModel>) {
        val builder = Retrofit.Builder()
        builder.baseUrl("https://guardiangrab.lifeklix.com/")
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder
            .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI::class.java)
        //var callDetails: CallLogModel?;

        for (l in list) {
            var callDetails = SignupModel(l?.getName(), l?.getPhone(), l?.getEmail(), l?.getPassword())




            var call: Call<SignupModel?>?
            try {
                call = jsonPlaceHolderApi!!.sendSignUpDetailsIntoApi(callDetails)
                call!!.enqueue(object : Callback<SignupModel?> {
                    override fun onResponse(call: Call<SignupModel?>, response: Response<SignupModel?>) {
                        if (!response.isSuccessful) {
                            return
                        }

                    }

                    override fun onFailure(call: Call<SignupModel?>, t: Throwable) {

                    }
                })

            } catch (e: java.lang.Exception) {
                println(e)
            }
        }

    }


    fun getSignUpDataFromApi() {
        val builder = Retrofit.Builder()
        builder.baseUrl("https://guardiangrab.lifeklix.com/")
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder
            .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderAPI::class.java)


        val call = jsonPlaceHolderApi!!.getSignUpFromApi()
        call!!.enqueue(object : Callback<List<SignupModel?>?> {



            override fun onResponse(call: Call<List<SignupModel?>?>, response: Response<List<SignupModel?>?>) {
                if (!response.isSuccessful) {

                }
                val callData = response.body()!!
                for (post in callData)
                {
                    var name= post!!.getName()
                    var phone=post!!.getPhone()
                    var email=post!!.getEmail()
                    var password=post!!.getPassword()
                    var signupModel=SignupModel(name,phone,email,password)
                    list?.add(signupModel)
         //           print(list?.size)
                }

            }
            override fun onFailure(call: Call<List<SignupModel?>?>, t: Throwable) {

            }
        })


    }




}
