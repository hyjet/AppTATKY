package com.kevinli5506.appta.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.LoginResponse
import com.kevinli5506.appta.Model.PostResponse
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import com.kevinli5506.appta.Rest.SessionManager
import kotlinx.android.synthetic.main.activity_sign_up_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpPage : BaseActivity(), View.OnClickListener {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)
        sessionManager = SessionManager(this)
        signup_btn_signup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            signup_btn_signup -> {
                val name = signup_edt_name.text.toString()
                val email = signup_edt_email.text.toString()
                val password = signup_edt_password.text.toString()
                val phone = signup_edt_phone.text.toString()

                val apiClient = ApiClient.getApiService(this)
                apiClient.postSignup(name, email, password, phone)
                    .enqueue(object : Callback<CommonResponseModel<LoginResponse>> {
                        override fun onFailure(
                            call: Call<CommonResponseModel<LoginResponse>>?,
                            t: Throwable?
                        ) {
                            Log.d("tes2", t?.message)
                        }

                        override fun onResponse(
                            call: Call<CommonResponseModel<LoginResponse>>?,
                            response: Response<CommonResponseModel<LoginResponse>>?
                        ) {
                            if (response?.code() == 200) {
                                val loginResponse = response.body()
                                if (loginResponse.statusCode == 200) {
                                    sessionManager.saveAuthToken(loginResponse.data.authToken!!)
                                    val intent = Intent(this@SignUpPage, HomePage::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                } else {
                                    Log.d(
                                        "tes2",
                                        "code = : ${loginResponse.statusCode.toString()}, msg = ${loginResponse.data.errorMessage?.get(
                                            0
                                        )}"
                                    )
                                }
                            }

                        }

                    })
            }
        }
    }
}
