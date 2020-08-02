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
import com.kevinli5506.appta.FileHelper
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.PostResponse
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.activity_donation_page.*
import kotlinx.android.synthetic.main.activity_upload_identity_card_page.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadIdentityCardPage : AppCompatActivity(), View.OnClickListener {

    var path: String? = null
    val fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_identity_card_page)

        upload_identity_card_btn_choose.setOnClickListener(this)
        upload_identity_card_btn_send.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            upload_identity_card_btn_choose -> {
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
            upload_identity_card_btn_send -> {
                val pathNotNull = path
                if (!pathNotNull.isNullOrEmpty()) {
                    val file = fileHelper.createFile(pathNotNull)
                    val requestBody = fileHelper.createRequestBody(file)
                    val part = fileHelper.createPart(file, requestBody, "identity_card")
                    val apiClient = ApiClient.getApiService(this)
                    apiClient.postUpdateIdentityCard(part)
                        .enqueue(object : Callback<CommonResponseModel<PostResponse>>{
                            override fun onFailure(
                                call: Call<CommonResponseModel<PostResponse>>,
                                t: Throwable
                            ) {
                                Log.d("tes2", t.message)
                                val toast = Toast.makeText(
                                    this@UploadIdentityCardPage,
                                    t.message,
                                    Toast.LENGTH_SHORT
                                )
                                toast.show()
                            }

                            override fun onResponse(
                                call: Call<CommonResponseModel<PostResponse>>,
                                response: Response<CommonResponseModel<PostResponse>>
                            ) {
                                if (response.code() == 200) {
                                    val postResponse = response.body()
                                    if (postResponse?.statusCode == 200) {
                                        val message = postResponse.data.message
                                        Log.d("tes2", message)
                                        val toast = Toast.makeText(
                                            this@UploadIdentityCardPage,
                                            message,
                                            Toast.LENGTH_SHORT
                                        )
                                        toast.show()
                                        finish()
                                    }
                                } else {
                                    try {
                                        val jObjError =
                                            JSONObject(response.errorBody()?.string())
                                        Toast.makeText(
                                            this@UploadIdentityCardPage,
                                            jObjError.getJSONObject("data").getString("error"),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            this@UploadIdentityCardPage,
                                            e.message,
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                    }
                                }
                            }

                        })
                }
                else{
                    Toast.makeText(
                        this@UploadIdentityCardPage,
                        "Harap Pilih Gambar KTP Anda",
                        Toast.LENGTH_LONG
                    )
                        .show()
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

    companion object {
        private val IMAGE_PICK_CODE = 2000
        private val PERMISSION_CODE = 2001
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val imageUri = data?.data
            upload_identity_card_imgv_identity_card.setImageURI(imageUri)
            if (imageUri != null) {
                path = fileHelper.getPathFromURI(this, imageUri)
                upload_identity_card_edt_path.setText(path)
            }
        }
    }
}