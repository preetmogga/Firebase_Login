package com.mogga.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mogga.login.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth= FirebaseAuth.getInstance()
        binding.btnSign.setOnClickListener {
            openSignUp()
        }
        binding.btnLog.setOnClickListener {
            if(check()){
                val email = binding.emailInput.text.toString()
                val passwd=binding.passInput.text.toString()
                auth.signInWithEmailAndPassword(email,passwd).addOnCompleteListener {
                    task ->
                    if (task.isSuccessful){
                        val intent = Intent(this,Home::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this,"Please enter valid email or passwd",Toast.LENGTH_LONG).show()

                    }

                }
            }else{
                Toast.makeText(this,"Please Enter The Email",Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun check(): Boolean {
        if (binding.emailInput.text.isNotEmpty() && (binding.passInput.text.isNotEmpty())){
            return true
        }

        return false

    }
  private fun   openSignUp() {
        val intent =Intent(this,SignUp::class.java)
        startActivity(intent)

    }

}