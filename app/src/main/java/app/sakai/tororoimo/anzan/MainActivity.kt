package app.sakai.tororoimo.anzan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val randomNumber1 = 10 + Random.nextInt(11)
        val randomNumber2 = 10 + Random.nextInt(11)

        number1Text.text = randomNumber1.toString()
        number2Text.text = randomNumber2.toString()

        val operatorText = " × "
        var correctAnswer = 0

        signText.text = operatorText
        correctAnswer = randomNumber1 * randomNumber2



        checkButton.setOnClickListener {
            val yourAnswer = inputText.text.toString()
            if (yourAnswer.isNotEmpty()) {
                val answerPage = Intent(this, AnswerActivity::class.java)
                val questionText = randomNumber1.toString() + operatorText + randomNumber2.toString() + " ="
                answerPage.putExtra("question", questionText)
                answerPage.putExtra("answer", yourAnswer)
                answerPage.putExtra("correct", correctAnswer.toString())
                startActivity(answerPage)
                finish()
            }
        }
    }
}
