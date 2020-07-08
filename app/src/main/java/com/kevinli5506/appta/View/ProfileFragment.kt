package com.kevinli5506.appta.View

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amulyakhare.textdrawable.TextDrawable
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.User
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.fragment_profile.*
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

        refresh()
    }

    override fun onStart() {
        super.onStart()
        refresh()
    }

    private fun refresh() {
        val apiClient = ApiClient.getApiService(context!!) //Todo : Check context
        apiClient.getUser().enqueue(object : Callback<CommonResponseModel<List<User>>> {
            override fun onFailure(call: Call<CommonResponseModel<List<User>>>?, t: Throwable?) {
                Log.d("tes2", t?.message)
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
                        val name = user.name.trim()
                        val email = user.email
                        val phone = user.phone
                        val spaceIndex = name.indexOf(" ")
                        val abbrive = "${name[0]}${name[spaceIndex + 1]}"

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
                    Log.d("test2", "Code = ${response?.code().toString()}")
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
        }
    }

}
