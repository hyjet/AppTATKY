package com.kevinli5506.appta.View

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.FileHelper
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.EventDonation
import com.kevinli5506.appta.Model.PostResponse
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.activity_donation_page.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class DonationPage : BaseActivity(), View.OnClickListener {
    var path: String? = null
    val fileHelper = FileHelper()
    lateinit var event :EventDonation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation_page)
        event = intent.getParcelableExtra(EventDetailPage.EXTRA_EVENT_ID)
        donation_tv_item_type.text = event.productType
        donation_tv_choose.setOnClickListener(this)
        donation_btn_donation.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            donation_tv_choose -> {
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
            donation_btn_donation -> {
                val itemAmount = donation_edt_item_amount.text.toString().toInt()
                val pathNotNull = path
                if (!pathNotNull.isNullOrEmpty()) {
                    val file = fileHelper.createFile(pathNotNull)
                    val requestBody = fileHelper.createRequestBody(file)
                    val part = fileHelper.createPart(file, requestBody,"images")
                    val apiClient = ApiClient.getApiService(this)
                    apiClient.postDonation(event.id, itemAmount, part)
                        .enqueue(object : Callback<CommonResponseModel<PostResponse>> {
                            override fun onFailure(
                                call: Call<CommonResponseModel<PostResponse>>?,
                                t: Throwable?
                            ) {

                                Log.d("tes2", t?.message)
                                val toast = Toast.makeText(
                                    this@DonationPage,
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
                                        finish()
                                    }
                                } else {
                                    try {
                                        val jObjError =
                                            JSONObject(response!!.errorBody()?.string())
                                        Toast.makeText(
                                            this@DonationPage,
                                            jObjError.getJSONObject("data").getString("error"),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } catch (e: Exception) {
                                        Toast.makeText(this@DonationPage, e.message, Toast.LENGTH_LONG)
                                            .show()
                                    }
                                }
                            }

                        })
                } else {
                    Log.d("tes2", "No image selected")
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

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
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
            donation_imgv_item_photo.setImageURI(imageUri)
            if (imageUri != null) {
                path = fileHelper.getPathFromURI(this, imageUri)
            }
        }
    }
}
