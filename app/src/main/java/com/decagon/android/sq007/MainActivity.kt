package com.decagon.android.sq007

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.decagon.android.sq007.utils.Common
import com.decagon.android.sq007.userModel.UserModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.installations.FirebaseInstallations
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import io.paperdb.Paper


class MainActivity : AppCompatActivity() {

//    private lateinit var fusedLocationClient: FusedLocationProviderClient
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

//        val firebaseDatabaseUserInfo = FirebaseDatabase.getInstance().getReference(Common.USER_INFORMATION)

        startTracking.setOnClickListener {
            var intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

//        Paper.init(this)
//
//        AuthUI.IdpConfig.EmailBuilder().build()
//        AuthUI.IdpConfig.GoogleBuilder().build()
//
//        Dexter.withContext(this)
//            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//            .withListener(object : PermissionListener{
//                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
//                    showSignInOption()
//                }
//
//                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
//                    Toast.makeText(this@MainActivity, "You must accept this permission", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
//                    TODO("Not yet implemented")
//                }
//
//            }).check()

    }

//    private fun showSignInOption() {
//        startActivityForResult(AuthUI.getInstance()
//            .createSignInIntentBuilder()
//            .setAvailableProviders(providers)
//            .build(), REQUEST_CODE)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE){
//            val firebaseUser = FirebaseAuth.getInstance().currentUser
//            firebaseDatabaseUserInfo.orderByKey()
//                .equalTo(firebaseUser?.uid)
//                .addListenerForSingleValueEvent(object : ValueEventListener{
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        if (snapshot == null ){
//                            //User not exist
//                            if (snapshot.child(firebaseUser!!.uid).exists()){
//                                Common.LOG_USER = UserModel(firebaseUser!!.uid, firebaseUser.email!!)
//                                //Add user to data base
//                                firebaseDatabaseUserInfo.child(Common.LOG_USER.uid!!).setValue(Common.LOG_USER)
//                            }
//                        }
//                        else{
//                            //User exist on the list
//                            Common.LOG_USER = snapshot.child(firebaseUser!!.uid)
//                                .getValue(UserModel::class.java)!!
//                        }
//
//                        Paper.book().write(Common.USERID_SAVED_KEY, Common.LOG_USER.uid)
//                        updateToken(firebaseUser)
//                        setupUI()
//
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        TODO("Not yet implemented")
//                    }
//
//
//                })
//        }
//    }

//    private fun updateToken(firebaseUser: FirebaseUser) {
//        val tokens = FirebaseDatabase.getInstance().getReference(Common.TOKENS)
//        //get token
//
//        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener { result -> tokens.child(firebaseUser.uid).setValue(
//            result.result?.token
//        ) }
//
//
//    }
}