package com.kevinli5506.appta.View

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.LoginRequest
import com.kevinli5506.appta.Model.LoginResponse
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import com.kevinli5506.appta.Rest.SessionManager
import kotlinx.android.synthetic.main.activity_login_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPage : AppCompatActivity(), View.OnClickListener {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        sessionManager = SessionManager(this)


        login_btn_login.setOnClickListener(this)


    }

    private fun hideKeyboard() {
        val view: View? = currentFocus
        if (view != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            login_btn_login -> {
                val email = login_edt_email.text.toString()
                val password = login_edt_password.text.toString()
                val postLogin = ApiClient.getApiService(this)
                postLogin.login(email, password).enqueue(object: Callback<CommonResponseModel<LoginResponse>>{
                    override fun onFailure(call: Call<CommonResponseModel<LoginResponse>>?, t: Throwable?) {
                        Log.d("tes2",t?.message)
                    }

                    override fun onResponse(
                        call: Call<CommonResponseModel<LoginResponse>>?,
                        response: Response<CommonResponseModel<LoginResponse>>?
                    ) {
                        if (response?.code()==200){
                            val loginResponse = response?.body()
                            if (loginResponse?.statusCode == 200) {
                                sessionManager.saveAuthToken(loginResponse.data.authToken)
                                val intent = Intent(this@LoginPage, HomePage::class.java)
                                startActivity(intent)
                            } else {
                                Log.d("tes2","login response : ${loginResponse?.statusCode.toString()}")
                            }
                        }
                        else{
                            Log.d("tes2","Code = ${response?.code().toString()}, ${email}")
                        }

                    }
                })
            }
        }

    }
}
