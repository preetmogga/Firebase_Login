package com.mogga.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mogga.login.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private  lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        binding.btnSignUp.setOnClickListener {
            if (check()){
                val email = binding.regEmailInput.text.toString()
                val passwd = binding.passwdInput.text.toString()
                val name = binding.nameInput.text.toString()
                val phone = binding.phoneInput.text.toString()
              val user = hashMapOf(
                  "Name" to name,
                 "email" to email,
              "Phone" to phone
              )
                val User = db.collection("User")
                val query = User.whereEqualTo("email",email).get().addOnSuccessListener {
                    it ->
                    if (it.isEmpty)
                    {
                        auth.createUserWithEmailAndPassword(email,passwd).addOnCompleteListener(this){
                            task ->
                            if (task.isSuccessful){
                                User.document(email).set(user)
                                val intent = Intent(this,Home::class.java)
                                intent.putExtra("email",email)
                                startActivity(intent)
                                finish()
                            }else{
                                Toast.makeText(this,"pls retry some time",Toast.LENGTH_LONG).show()

                            }
                        }
                    }else
                    {
                        Toast.makeText(this,"user already login",Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }

    private fun check(): Boolean {
        return true
    }

    }
