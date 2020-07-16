package com.kevinli5506.appta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.PostResponse
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.activity_forget_password_page.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgetPasswordPage : BaseActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password_page)
        forget_password_btn_send.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v){
            forget_password_btn_send->{
                val email = forget_password_edt_email.text.toString()
                val apiClient = ApiClient.getApiService(this)
                apiClient.postForgetPassword(email)
                    .enqueue(object : Callback<CommonResponseModel<PostResponse>>{
                        override fun onFailure(
                            call: Call<CommonResponseModel<PostResponse>>?,
                            t: Throwable?
                        ) {
                            Log.d("tes2", t?.message)
                            val toast = Toast.makeText(
                                this@ForgetPasswordPage,
                                t?.message,
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        }

                        override fun onResponse(
                            call: Call<CommonResponseModel<PostResponse>>?,
                            response: Response<CommonResponseModel<PostResponse>>?
                        ) {
                            if (response?.code() == 200) {
                                val postResponse = response.body()
                                if (postResponse?.statusCode == 200) {
                                    val message = postResponse.data.message
                                    Log.d("tes2", message)
                                    val toast = Toast.makeText(
                                        this@ForgetPasswordPage,
                                        message,
                                        Toast.LENGTH_SHORT
                                    )
                                    toast.show()
                                    finish()
                                }
                            } else {
                                try {
                                    val jObjError =
                                        JSONObject(response!!.errorBody()?.string())
                                    Toast.makeText(
                                        this@ForgetPasswordPage,
                                        jObjError.getJSONObject("data").getString("error"),
                                        Toast.LENGTH_LONG
                                    ).show()
                                } catch (e: Exception) {
                                    Toast.makeText(this@ForgetPasswordPage, e.message, Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                        }

                    })
            }
        }
    }
}
