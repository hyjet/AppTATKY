package com.kevinli5506.appta.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.PostResponse
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
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
class WithdrawDetailFragment : Fragment(),View.OnClickListener {
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
        view.withdraw_edt_bank_name.adapter= arrayAdapter
        view.withdraw_btn_100000.setOnClickListener(this)
        view.withdraw_btn_50000.setOnClickListener(this)
        view.withdraw_btn_submit.setOnClickListener(this)
        view.withdraw_edt_amount.setText("0")
        view.withdraw_edt_amount.setOnFocusChangeListener{_,_->
            if(view.withdraw_edt_amount.text.toString().equals("")){
                view.withdraw_edt_amount.setText("0")
            }
            else{
                val current = withdraw_edt_amount.text.toString().replace(",","").toInt()
                withdraw_edt_amount.setText(df.format (current).toString())
            }
        }
    }
    override fun onClick(v: View?) {
        when(v){
            withdraw_btn_100000->{
                val current = withdraw_edt_amount.text.toString().replace(",","").toInt()
                withdraw_edt_amount.setText(df.format (current+100000).toString())
            }
            withdraw_btn_50000->{
                val current = withdraw_edt_amount.text.toString().replace(",","").toInt()
                withdraw_edt_amount.setText(df.format(current+50000).toString())
            }
            withdraw_btn_submit->{
                val builder = AlertDialog.Builder(context!!)
                builder.setTitle("Witdrawal")
                builder.setMessage("Apakah anda yakin akan melakukan transaksi ini?")
                builder.setPositiveButton("Yes"){_, _ ->
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
                    val amount = withdraw_edt_amount.text.toString().replace(",","").toInt()
                    val apiClient = ApiClient.getApiService(context!!)
                    apiClient.postWithdraw(fullName,account,bankName,amount).enqueue(object : Callback<CommonResponseModel<PostResponse>>{
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

                                val postResponse = response?.body()
                                if (postResponse?.statusCode==200){
                                    val message = postResponse.data.message
                                    Log.d("tes2",message)
                                    val toast = Toast.makeText(context,message, Toast.LENGTH_SHORT)
                                    toast.show()
                                    activity?.finish()
                                }
                                else{
                                    try {
                                        Log.d("tes2","error")
                                        val jObjError =
                                            JSONObject(response!!.errorBody()?.string())
                                        val arrayError =
                                            jObjError.getJSONObject("data").getJSONArray("error")

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
                builder.setNegativeButton("No"){_, _ ->  }
                builder.show()

            }
        }
    }

}
