package com.mogga.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.mogga.login.databinding.ActivityHomeBinding


class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?:return
        val isLogin =  sharedPref.getString("Email","1")
        binding.btnLogout.setOnClickListener {
        sharedPref.edit().remove("Email").apply()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        if (isLogin=="1"){

            val email =    intent.getStringExtra("email")
            if (email!=null){
                setText(email)
                with(sharedPref.edit()){
                    putString("Email",email)
                    apply()
                }

            }else{
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()

            }
        }else{
            setText(isLogin)

        }


    }

    private fun setText(email: String?) {
        db= FirebaseFirestore.getInstance()
        if (email != null) {
            db.collection("User").document(email).get().addOnSuccessListener { task ->
                binding.userName.text=task.get("Name").toString()
                binding.userAddEmail.text=task.get("email").toString()
                binding.userPasswd.text=task.get("Phone").toString()


            }
        }

    }
}