package com.kevinli5506.appta.View

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.FileHelper
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.LoginResponse
import com.kevinli5506.appta.Model.PostResponse
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import com.kevinli5506.appta.Rest.SessionManager
import com.onesignal.OSSubscriptionObserver
import com.onesignal.OSSubscriptionStateChanges
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.activity_sign_up_page.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpPage : BaseActivity(), View.OnClickListener, OSSubscriptionObserver {
    var path: String? = null
    val fileHelper = FileHelper()
    private var playerId: String = ""
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
                val namePart = RequestBody.create(MediaType.parse("text/plain"), name)
                val emailPart = RequestBody.create(MediaType.parse("text/plain"), email)
                val passwordPart = RequestBody.create(MediaType.parse("text/plain"), password)
                val phonePart = RequestBody.create(MediaType.parse("text/plain"), phone)

                signup_layout.isEnabled = false
                val apiClient = ApiClient.getApiService(this)
                apiClient.postSignup(namePart, emailPart, passwordPart, phonePart)
                    .enqueue(object : Callback<CommonResponseModel<LoginResponse>> {
                        override fun onFailure(
                            call: Call<CommonResponseModel<LoginResponse>>?,
                            t: Throwable?
                        ) {
                            Log.d("tes2", t?.message)
                            val toast =
                                Toast.makeText(this@SignUpPage, t?.message, Toast.LENGTH_SHORT)
                            toast.show()
                            signup_layout.isEnabled = true
                        }

                        override fun onResponse(
                            call: Call<CommonResponseModel<LoginResponse>>?,
                            response: Response<CommonResponseModel<LoginResponse>>?
                        ) {
                            if (response?.code() == 200) {
                                val loginResponse = response.body()
                                if (loginResponse?.statusCode == 200) {
                                    sessionManager.saveAuthToken(loginResponse.data.authToken!!)
                                    OneSignal.setSubscription(true)
                                    if (!playerId.equals("")) {
                                        apiClient.postPlayerId(playerId).enqueue(object :
                                            Callback<CommonResponseModel<PostResponse>> {
                                            override fun onFailure(
                                                call: Call<CommonResponseModel<PostResponse>>,
                                                t: Throwable
                                            ) {
                                                Log.d("tes2", t?.message)
                                                val toast =
                                                    Toast.makeText(
                                                        this@SignUpPage,
                                                        t?.message,
                                                        Toast.LENGTH_SHORT
                                                    )
                                                toast.show()
                                            }

                                            override fun onResponse(
                                                call: Call<CommonResponseModel<PostResponse>>,
                                                response: Response<CommonResponseModel<PostResponse>>
                                            ) {
                                                Log.d("tes2", response.message())
                                            }
                                        })
                                    }
                                    val intent = Intent(this@SignUpPage, HomePage::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                }
                            } else {
                                try {
                                    val jObjError =
                                        JSONObject(response!!.errorBody()?.string())
                                    val arrayError =
                                        jObjError.getJSONObject("data").getJSONArray("error")

                                    Toast.makeText(
                                        this@SignUpPage,
                                        arrayError.getString(0),
                                        Toast.LENGTH_LONG
                                    ).show()
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        this@SignUpPage,
                                        e.message,
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                }
                                signup_layout.isEnabled = true
                            }
                        }
                    }
                    )
            }

        }
    }


    companion object {
        private val IMAGE_PICK_CODE = 2000
        private val PERMISSION_CODE = 2001
    }

    override fun onOSSubscriptionChanged(stateChanges: OSSubscriptionStateChanges) {
        if (!stateChanges.from.subscribed && stateChanges.to.subscribed) {
            playerId = stateChanges.to.userId
        }
    }
}
