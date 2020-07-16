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
import kotlinx.android.synthetic.main.activity_sign_up_page.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpPage : BaseActivity(), View.OnClickListener {
    var path: String? = null
    val fileHelper = FileHelper()
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)
        sessionManager = SessionManager(this)
        signup_btn_signup.setOnClickListener(this)
        signup_tv_choose.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            signup_btn_signup -> {
                val name = signup_edt_name.text.toString()
                val email = signup_edt_email.text.toString()
                val password = signup_edt_password.text.toString()
                val phone = signup_edt_phone.text.toString()
                val namePart = RequestBody.create(MediaType.parse("text/plain"),name)
                val emailPart = RequestBody.create(MediaType.parse("text/plain"),email)
                val passwordPart = RequestBody.create(MediaType.parse("text/plain"),password)
                val phonePart = RequestBody.create(MediaType.parse("text/plain"),phone)
                val pathNotNull = path
                if (!pathNotNull.isNullOrEmpty()) {
                    val file = fileHelper.createFile(pathNotNull)
                    val requestBody = fileHelper.createRequestBody(file)
                    val part = fileHelper.createPart(file, requestBody,"identity_card")

                    val apiClient = ApiClient.getApiService(this)
                    apiClient.postSignup(namePart, emailPart, passwordPart, phonePart,part)
                        .enqueue(object : Callback<CommonResponseModel<LoginResponse>> {
                            override fun onFailure(
                                call: Call<CommonResponseModel<LoginResponse>>?,
                                t: Throwable?
                            ) {
                                Log.d("tes2", t?.message)
                                val toast =
                                    Toast.makeText(this@SignUpPage, t?.message, Toast.LENGTH_SHORT)
                                toast.show()
                            }

                            override fun onResponse(
                                call: Call<CommonResponseModel<LoginResponse>>?,
                                response: Response<CommonResponseModel<LoginResponse>>?
                            ) {
                                if (response?.code() == 200) {
                                    val loginResponse = response.body()
                                    if (loginResponse?.statusCode == 200) {
                                        sessionManager.saveAuthToken(loginResponse.data.authToken!!)
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
                                }
                            }
                        }
                        )
                }else{
                    Toast.makeText(
                        this@SignUpPage,
                        "Image path tidak ditemukan",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            signup_tv_choose -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(
                            permission,
                            PERMISSION_CODE
                        )
                    } else {
                        pickImage()
                    }
                } else {
                    pickImage()
                }
            }
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            IMAGE_PICK_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImage()
                } else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val imageUri = data?.data
            signup_imgv_identity_photo.setImageURI(imageUri)
            if (imageUri != null) {
                path = fileHelper.getPathFromURI(this, imageUri)
            }
        }
    }

    companion object {
        private val IMAGE_PICK_CODE = 2000
        private val PERMISSION_CODE = 2001
    }
}
