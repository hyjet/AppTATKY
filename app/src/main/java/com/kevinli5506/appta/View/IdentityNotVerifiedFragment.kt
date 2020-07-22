package com.kevinli5506.appta.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.PostResponse
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import com.kevinli5506.appta.Rest.SessionManager
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.fragment_identity_not_verified.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class IdentityNotVerifiedFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_identity_not_verified, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        not_verified_btn_sign_out.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            not_verified_btn_sign_out -> {
                val builder = AlertDialog.Builder(context!!)
                builder.setTitle("Exit")
                builder.setMessage("Apakah anda yakin akan sign out dari akun ini?")
                builder.setPositiveButton("Yes") { _, _ ->
                    val apiClient = ApiClient.getApiService(context!!)
                    apiClient.postSignOut()
                        .enqueue(object : Callback<CommonResponseModel<PostResponse>> {
                            override fun onFailure(
                                call: Call<CommonResponseModel<PostResponse>>?,
                                t: Throwable?
                            ) {
                                Log.d("tes2", t?.message)
                                val toast = Toast.makeText(context, t?.message, Toast.LENGTH_SHORT)
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
                                        val sessionManager = SessionManager(context!!)
                                        sessionManager.deleteAuthToken()
                                        OneSignal.setSubscription(false)
                                        val intent = Intent(context, LaunchPage::class.java)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(intent)
                                    } else {
                                        val errorMessage = postResponse?.data?.error?.get(0)
                                        Log.d("tes2", errorMessage)
                                    }
                                } else {
                                    try {
                                        val jObjError =
                                            JSONObject(response!!.errorBody()?.string())
                                        Toast.makeText(
                                            context,
                                            jObjError.getJSONObject("data").getString("error"),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } catch (e: Exception) {
                                        Toast.makeText(context, e.message, Toast.LENGTH_LONG)
                                            .show()
                                    }
                                }
                            }

                        })
                }
                builder.setNegativeButton("No") { _, _ ->
                }
                builder.show()
            }
        }
    }
}