package com.kevinli5506.appta.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.PutResponse
import com.kevinli5506.appta.Model.User
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.activity_edit_profile_page.*
import kotlinx.android.synthetic.main.activity_home_page.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfilePage : BaseActivity(), View.OnClickListener {
    lateinit var curName: String
    lateinit var curEmail: String
    lateinit var curPhone: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_page)
        edit_profile_btn_save.setOnClickListener(this)
        refresh()


    }

    private fun refresh() {
        val apiClient = ApiClient.getApiService(this) //Todo : Check context
        apiClient.getUser().enqueue(object : Callback<CommonResponseModel<List<User>>> {
            override fun onFailure(call: Call<CommonResponseModel<List<User>>>?, t: Throwable?) {
                Log.d("tes2", t?.message)
                val toast = Toast.makeText(this@EditProfilePage,t?.message, Toast.LENGTH_SHORT)
                toast.show()
            }

            override fun onResponse(
                call: Call<CommonResponseModel<List<User>>>?,
                response: Response<CommonResponseModel<List<User>>>?
            ) {
                if (response?.code() == 200) {
                    val userResponse = response.body()
                    if (userResponse.statusCode == 200) {
                        val list = userResponse.data
                        val user: User = list[0]
                        curName = user.name.trim()
                        curEmail = user.email
                        curPhone = user.phone
                        edit_profile_edt_name.setText(curName)
                        edit_profile_edt_email.setText(curEmail)
                        edit_profile_edt_phone_number.setText(curPhone)

                    }

                } else {
                    Log.d("test2", "Code = ${response?.code().toString()}")
                }

            }

        })
    }

    override fun onClick(v: View?) {
        when (v) {
            edit_profile_btn_save -> {
                val name = edit_profile_edt_name.text.toString()
                val email = edit_profile_edt_email.text.toString()
                val phone = edit_profile_edt_phone_number.text.toString()
                val editProfileRequest: HashMap<String, String> = HashMap()
                if (!name.trim().equals(curName)) {
                    editProfileRequest.put("name", name)
                }
                if (!email.trim().equals(curEmail)) {
                    editProfileRequest.put("email", email)
                }
                if (!phone.trim().equals(curPhone)) {
                    editProfileRequest.put("phone", phone)
                }

                val apiClient = ApiClient.getApiService(this) //Todo : Check context
                apiClient.putEditProfile(editProfileRequest)
                    .enqueue(object : Callback<CommonResponseModel<PutResponse>> {
                        override fun onFailure(
                            call: Call<CommonResponseModel<PutResponse>>?,
                            t: Throwable?
                        ) {
                            Log.d("tes2", t?.message)
                            val toast = Toast.makeText(this@EditProfilePage,t?.message,Toast.LENGTH_SHORT)
                            toast.show()
                        }

                        override fun onResponse(
                            call: Call<CommonResponseModel<PutResponse>>?,
                            response: Response<CommonResponseModel<PutResponse>>?
                        ) {
                            if(response?.code()==200){
                                val postResponse = response.body()
                                if (postResponse.statusCode==200){
                                    val message = postResponse.data.message
                                    Log.d("tes2",message)
                                    val snackbar = Snackbar.make(edit_profile_root_layout,"Edit SuccessFul",Snackbar.LENGTH_SHORT)
                                    snackbar.show()
                                    finish()
                                }

                            }
                            else {
                                try {
                                    val jObjError =
                                        JSONObject(response!!.errorBody().string())
                                    Toast.makeText(
                                        this@EditProfilePage,
                                        jObjError.getJSONObject("data").getString("error"),
                                        Toast.LENGTH_LONG
                                    ).show()
                                } catch (e: Exception) {
                                    Toast.makeText(this@EditProfilePage, e.message, Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                        }

                    })

            }
        }
    }
}
