package com.kevinli5506.appta

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calculate_page.*
import kotlinx.android.synthetic.main.activity_donation_page.*

class DonationPage : AppCompatActivity() ,View.OnClickListener{

    var donationItemList: ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation_page)

        donationItemList.addAll(DonationItemData.listDonationItem)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, donationItemList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        donation_spin_item_type.adapter = adapter
        donation_tv_choose.setOnClickListener(this)
        donation_btn_donation.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            donation_tv_choose->{
                donation_edt_item_name.setText("Hello")
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                        val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permission, PERMISSION_CODE)
                    }
                    else{
                        pickImage()
                    }
                }
                else{
                    pickImage()
                }
            }
            donation_btn_donation->{
                val itemName = donation_edt_item_name.text.toString()
                val itemType = donation_spin_item_type.selectedItemPosition
                val itemImage = donation_imgv_item_photo
                finish()
            }
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,IMAGE_PICK_CODE)
    }
    companion object{
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE->{
                if (grantResults.size>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    pickImage()
                }
                else
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK && requestCode== IMAGE_PICK_CODE){
            donation_imgv_item_photo.setImageURI(data?.data)
        }
    }
}
