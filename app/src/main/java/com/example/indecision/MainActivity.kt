package com.example.indecision

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val formBtn: Button = findViewById(R.id.formBtn)
        val accountBtn: Button =findViewById(R.id.accountBtn)
        val decisionListBtn: Button = findViewById(R.id.decisionListBtn)

        auth = FirebaseAuth.getInstance()

        formBtn.setOnClickListener {
            Intent(this, QuestionFormActivity::class.java).also {
                startActivity(it)
            }
        }

        accountBtn.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        decisionListBtn.setOnClickListener {
            if(auth.currentUser == null) {
                Intent(this, LoginActivity::class.java).also {
                    startActivity(it)
                }
            } else {
                Intent(this, QuestionListActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

//        permissionsBtn.setOnClickListener {
//            requestPermissions()
//        }
    }

//    private fun hasWriteExternalStoragePermission() =
//        ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

//    private fun requestPermissions() {
//        var permissionsToRequest = mutableListOf<String>()
//        if(!hasWriteExternalStoragePermission()) {
//            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        }
//
//        if(permissionsToRequest.isNotEmpty()) {
//            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 0)
//        }
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(requestCode == 0 && grantResults.isNotEmpty()) {
//            for(i in grantResults.indices) {
//                if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d("PermissionsRequest", "${permissions[i]} granted")
//                }
//            }
//        }
//    }
}