package com.gogote.brainburst

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gogote.brainburst.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load data from Firebase
        loadDataFromFirebase()
    }

    private fun loadDataFromFirebase() {
        // Reference to your Firestore collection
        val db = Firebase.firestore

        // Attempt to load some data from Firestore
        db.collection("Quiz")
            .get()
            .addOnSuccessListener { documents ->
                // Check if the documents snapshot is not empty
                if (!documents.isEmpty) {
                    // Data is successfully loaded from Firebase
                    proceedToNextActivity()
                } else {
                    // Handle the case where no documents are found
                    // For now, we'll just proceed to the next activity
                    proceedToNextActivity()
                }
            }
            .addOnFailureListener {
                // Handle the error case (e.g., show a toast, log the error)
                Toast.makeText(this, "Failed to load data from Firebase", Toast.LENGTH_SHORT).show()
                proceedToNextActivity()
            }
    }
    private fun proceedToNextActivity() {
        if (Firebase.auth.currentUser != null) {
            // User is signed in, go to Quiz activity
            startActivity(Intent(this, Quiz::class.java))
        } else {
            // User is not signed in, go to Login activity
            val intent = Intent(this, Login::class.java)
            intent.putExtra("MODE", "SIGNUP")
            startActivity(intent)
        }
        // Finish the splash screen activity
        finish()
    }
}
