package com.decagon.android.sq007

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.database.DatabaseReference


class MainActivity : AppCompatActivity() {
    lateinit var startTracking:Button

    companion object{
        private const val REQUEST_CODE =121
    }

    private lateinit var  providers:List<AuthUI.IdpConfig>
    private lateinit var firebaseDatabaseUserInfo:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startTracking = findViewById(R.id.start_tracking)
        startTracking.setOnClickListener {
            var intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }

}