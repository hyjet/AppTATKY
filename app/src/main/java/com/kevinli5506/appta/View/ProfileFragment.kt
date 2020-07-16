package com.kevinli5506.appta.View

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.amulyakhare.textdrawable.TextDrawable
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.PostResponse
import com.kevinli5506.appta.Model.User
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import com.kevinli5506.appta.Rest.SessionManager
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profile_imgbtn_edit_profile.setOnClickListener(this)
        profile_imgbtn_history.setOnClickListener(this)
        profile_btn_sign_out.setOnClickListener(this)
        refresh()
    }

    override fun onStart() {
        super.onStart()
        refresh()
    }

    private fun refresh() {
        val apiClient = ApiClient.getApiService(context!!)
        apiClient.getUser().enqueue(object : Callback<CommonResponseModel<List<User>>> {
            override fun onFailure(call: Call<CommonResponseModel<List<User>>>?, t: Throwable?) {
                Log.d("tes2", t?.message)
                val toast = Toast.makeText(context,t?.message, Toast.LENGTH_SHORT)
                toast.show()
            }

            override fun onResponse(
                call: Call<CommonResponseModel<List<User>>>?,
                response: Response<CommonResponseModel<List<User>>>?
            ) {
                if (response?.code() == 200) {
                    val userResponse = response.body()
                    if (userResponse?.statusCode == 200) {
                        val list = userResponse.data
                        val user: User = list[0]
                        val name = user.name.trim()
                        val email = user.email
                        val phone = user.phone
                        val spaceIndex = name.indexOf(" ")
                        var abbrive = "${name[0]}"
                        if (spaceIndex >= 0) {
                            abbrive = abbrive+ name[spaceIndex+1]
                        }
                        val drawableName: TextDrawable = TextDrawable.builder()
                            .beginConfig()
                            .width(64)
                            .height(64)
                            .endConfig()
                            .buildRound(abbrive, Color.RED)
                        profile_cimgv_photo.setImageDrawable(drawableName)
                        profile_tv_email.text = email
                        profile_tv_name.text = name
                        profile_tv_phone.text = phone
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

    override fun onClick(v: View?) {
        when (v) {
            profile_imgbtn_edit_profile -> {
                val intent = Intent(
                    context,
                    EditProfilePage::class.java
                )
                startActivity(intent)
            }
            profile_imgbtn_history -> {
                val intent = Intent(
                    context,
                    HistoryPage::class.java
                )
                startActivity(intent)
            }
            profile_btn_sign_out -> {
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
                                val toast = Toast.makeText(context,t?.message, Toast.LENGTH_SHORT)
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
