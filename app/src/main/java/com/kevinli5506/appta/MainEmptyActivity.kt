package com.kevinli5506.appta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kevinli5506.appta.Rest.SessionManager
import com.kevinli5506.appta.View.HomePage
import com.kevinli5506.appta.View.LaunchPage

class MainEmptyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent :Intent
        val sessionManager =SessionManager(this)
        if(sessionManager.fetchAuthToken()!=null){
            intent = Intent(this,HomePage::class.java)
        }
        else{
            intent = Intent(this,LaunchPage::class.java)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
