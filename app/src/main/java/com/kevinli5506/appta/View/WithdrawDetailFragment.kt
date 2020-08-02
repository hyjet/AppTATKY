package com.kevinli5506.appta.View

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.amulyakhare.textdrawable.TextDrawable
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.PostResponse
import com.kevinli5506.appta.Model.User
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_withdraw_detail.*
import kotlinx.android.synthetic.main.fragment_withdraw_detail.view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

/**
 * A simple [Fragment] subclass.
 */
class WithdrawDetailFragment : Fragment(), View.OnClickListener {
    val df = DecimalFormat("#,###")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_withdraw_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayAdapter = ArrayAdapter(
            context!!,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.bank_name)
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.withdraw_edt_bank_name.adapter = arrayAdapter
        view.withdraw_btn_100000.setOnClickListener(this)
        view.withdraw_btn_50000.setOnClickListener(this)
        view.withdraw_btn_submit.setOnClickListener(this)
        view.withdraw_btn_200000.setOnClickListener(this)
        view.withdraw_edt_amount.setText("0")
        view.withdraw_edt_amount.setOnFocusChangeListener { _, _ ->
            if (view.withdraw_edt_amount.text.toString().equals("")) {
                view.withdraw_edt_amount.setText("0")
            } else {
                val current = withdraw_edt_amount.text.toString().replace(",", "").toInt()
                withdraw_edt_amount.setText(df.format(current).toString())
            }
        }
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
                        val name = user.name
                        withdraw_edt_name.setText(name)
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
            withdraw_btn_200000 -> {
                withdraw_edt_amount.setText(df.format( 200000).toString())
            }
            withdraw_btn_100000 -> {
                withdraw_edt_amount.setText(df.format( 100000).toString())
            }
            withdraw_btn_50000 -> {
                withdraw_edt_amount.setText(df.format(50000).toString())
            }
            withdraw_btn_submit -> {
                val amount = withdraw_edt_amount.text.toString().replace(",", "").toInt()
                if (amount < 20000) {
                    Toast.makeText(
                        context,
                        "Minimal penarikan dana adalah Rp. 20000",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val builder = AlertDialog.Builder(context!!)
                    builder.setTitle("Witdrawal")
                    builder.setMessage("Apakah anda yakin akan melakukan transaksi ini?")
                    builder.setPositiveButton("Ya") { _,   _ ->
                        /*val fragment =
                            WithdrawOnProcessFragment()
                        activity!!.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.withdraw_container, fragment, fragment.javaClass.simpleName)
                            .commit()
                        //Todo : change submitted to true*/
                        val fullName = withdraw_edt_name.text.toString()
                        val bankName = withdraw_edt_bank_name.selectedItem.toString()
                        val account = withdraw_edt_account_number.text.toString()

                        val apiClient = ApiClient.getApiService(context!!)
                        apiClient.postWithdraw(fullName, account, bankName, amount)
                            .enqueue(object : Callback<CommonResponseModel<PostResponse>> {
                                override fun onFailure(
                                    call: Call<CommonResponseModel<PostResponse>>?,
                                    t: Throwable?
                                ) {
                                    Log.d("tes2", t?.message)
                                    val toast =
                                        Toast.makeText(context, t?.message, Toast.LENGTH_SHORT)
                                    toast.show()
                                }

                                override fun onResponse(
                                    call: Call<CommonResponseModel<PostResponse>>?,
                                    response: Response<CommonResponseModel<PostResponse>>?
                                ) {

                                    val postResponse = response?.body()
                                    if (postResponse?.statusCode == 200) {
                                        val message = postResponse.data.message
                                        Log.d("tes2", message)
                                        val toast =
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT)
                                        toast.show()
                                        activity?.finish()
                                    } else {
                                        try {
                                            Log.d("tes2", "error")
                                            val jObjError =
                                                JSONObject(response!!.errorBody()?.string())
                                            val arrayError =
                                                jObjError.getJSONObject("data")
                                                    .getJSONArray("error")

                                            Toast.makeText(
                                                context,
                                                arrayError.getString(0),
                                                Toast.LENGTH_LONG
                                            ).show()
                                        } catch (e: Exception) {
                                            Toast.makeText(
                                                context,
                                                e.message,
                                                Toast.LENGTH_LONG
                                            )
                                                .show()
                                        }
                                    }


                                }

                            })
                    }
                    builder.setNegativeButton("Tidak") { _, _ -> }
                    builder.show()

                }
            }

        }
    }

}
