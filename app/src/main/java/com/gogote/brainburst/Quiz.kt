package com.gogote.brainburst

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gogote.brainburst.databinding.ActivityQuizBinding
import com.google.android.material.color.utilities.Score
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Quiz : AppCompatActivity() {
    private lateinit var  binding: ActivityQuizBinding
     private lateinit  var list: ArrayList<QuestionModel>
     private  var count : Int = 0
    private var score: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

         list = ArrayList<QuestionModel>()

        Firebase.firestore.collection("Quiz")
            .get().addOnSuccessListener {
               doct ->
                list.clear()
                for(i in doct.documents){

                    val questionModel = i.toObject(QuestionModel::class.java)
                    list.add(questionModel!!)

                }
                binding.question.setText(list.get(0).question)
                binding.option1.setText(list.get(0).option1)
                binding.option2.setText(list.get(0).option2)
                binding.option3.setText(list.get(0).option3)
                binding.option4.setText(list.get(0).option4)
            }


//        list.add(QuestionModel("What is the capital city of Australia?", "Sydney", "Melbourne", "Canberra", "Perth", "Canberra"))
//        list.add(QuestionModel("Which component is essential for storing data temporarily while a computer is running? ","Hard Drive", "Ram", "GPU", "Power Supply", "Ram"))
//        list.add(QuestionModel("What is the capital city of Australia?", "Sydney", "Melbourne", "Canberra", "Perth", 3))
//        list.add(QuestionModel("What is the capital city of Australia?", "Sydney", "Melbourne", "Canberra", "Perth", 3))
//        list.add(QuestionModel("What is the capital city of Australia?", "Sydney", "Melbourne", "Canberra", "Perth", 3))


        binding.option1.setOnClickListener {
            nextData(binding.option1.text.toString())
        }


        binding.option2.setOnClickListener {
            nextData(binding.option2.text.toString())
        }
        binding.option3.setOnClickListener {
            nextData(binding.option3.text.toString())
            }
        binding.option4.setOnClickListener {
            nextData(binding.option4.text.toString())
        }

    }

    private fun nextData(i: String) {

        if(count < list.size){

            if(list.get(count).answer.toString().equals(i)){
                score++
            }

        }






        count++
        if(count >=list.size){
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("score", score.toString())

//            intent.putExtra("total", list.size)

            startActivity(intent)
            finish()
        } else{
        binding.question.setText(list.get(count).question)
        binding.option1.setText(list.get(count).option1)
        binding.option2.setText(list.get(count).option2)
        binding.option3.setText(list.get(count).option3)
        binding.option4.setText(list.get(count).option4)
        }


    }
}