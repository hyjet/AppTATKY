package com.kevinli5506.appta

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amulyakhare.textdrawable.TextDrawable
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.profile_tv_email

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() ,View.OnClickListener{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = UserData.name.trim()
        val email = UserData.email
        val phone = UserData.phone
        val spaceIndex = name.indexOf(" ")
        val abbrive = "${name[0]}${name[spaceIndex+1]}"

        val drawableName : TextDrawable = TextDrawable.builder()
            .beginConfig()
            .width(64)
            .height(64)
            .endConfig()
            .buildRound(abbrive,Color.RED)
        view.profile_cimgv_photo.setImageDrawable(drawableName)
        profile_tv_email.text = email
        profile_tv_name.text = name
        profile_tv_phone.text = phone
        profile_imgbtn_edit_profile.setOnClickListener(this)
        profile_imgbtn_history.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            profile_imgbtn_edit_profile->{
                val intent = Intent(context,EditProfilePage::class.java)
                startActivity(intent)
            }
            profile_imgbtn_history->{

            }
        }
    }

}
