package com.kevinli5506.appta.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.PostResponse
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.fragment_withdraw_detail.*
import kotlinx.android.synthetic.main.fragment_withdraw_detail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class WithdrawDetailFragment : Fragment(),View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_withdraw_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.withdraw_btn_100000.setOnClickListener(this)
        view.withdraw_btn_50000.setOnClickListener(this)
        view.withdraw_btn_submit.setOnClickListener(this)
        view.withdraw_edt_amount.setText("0")
        view.withdraw_edt_amount.setOnFocusChangeListener{_,_->
            if(view.withdraw_edt_amount.text.toString().equals("")){
                view.withdraw_edt_amount.setText("0")
            }

        }
    }
    override fun onClick(v: View?) {
        when(v){
            withdraw_btn_100000->{
                val current = withdraw_edt_amount.text.toString().toInt()
                withdraw_edt_amount.setText((current+100000).toString())
            }
            withdraw_btn_50000->{
                val current = withdraw_edt_amount.text.toString().toInt()
                withdraw_edt_amount.setText((current+50000).toString())
            }
            withdraw_btn_submit->{
                val builder = AlertDialog.Builder(context!!)
                builder.setTitle("Klaim")
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
                    val bankName = withdraw_edt_bank_name.text.toString()
                    val account = withdraw_edt_account_number.text.toString()
                    val amount = withdraw_edt_amount.text.toString().toInt()
                    val apiClient = ApiClient.getApiService(context!!)
                    apiClient.postWithdraw(fullName,account,bankName,amount).enqueue(object : Callback<CommonResponseModel<PostResponse>>{
                        override fun onFailure(
                            call: Call<CommonResponseModel<PostResponse>>?,
                            t: Throwable?
                        ) {
                            Log.d("tes2", t?.message)
                        }

                        override fun onResponse(
                            call: Call<CommonResponseModel<PostResponse>>?,
                            response: Response<CommonResponseModel<PostResponse>>?
                        ) {
                            if (response?.code() == 200) {
                                val editProfResponse = response.body()
                                Log.d("tes2","Code : ${editProfResponse.data.message}, message : ${editProfResponse.data.message}")

                            } else {
                                Log.d("tes2", "Code = ${response?.code().toString()}")
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
