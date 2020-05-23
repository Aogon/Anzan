package app.sakai.tororoimo.anzan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var randomNumber1 = 0
    var randomNumber2 = 0
    val operatorText = " × "
    var correctAnswer = 0


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var questionNumber = 0
        var numberCorrectAnswer = 0
        var numberWrongAnswer = 0


        number1Text.text = randomNumber1.toString()
        number2Text.text = randomNumber2.toString()

        signText.text = operatorText
        correctAnswer = randomNumber1 * randomNumber2
        questionNumber++


        randomNumber1 = 10 + Random.nextInt(11)
        randomNumber2 = 10 + Random.nextInt(11)

        number1Text.text = randomNumber1.toString()
        number2Text.text = randomNumber2.toString()


        signText.text = operatorText
        correctAnswer = randomNumber1 * randomNumber2



        checkButton.setOnClickListener {
            if (questionNumber < 5) {
                val yourAnswer = inputText.text.toString()
                if (yourAnswer.isNotEmpty()) {
                    if (yourAnswer == correctAnswer.toString()) {
                        Toast.makeText(this, "正解", Toast.LENGTH_SHORT).show()
                        numberCorrectAnswer++
                    } else {
                        Toast.makeText(this, "不正解", Toast.LENGTH_SHORT).show()
                        numberWrongAnswer++
                    }
                }

                randomNumber1 = 10 + Random.nextInt(11)
                randomNumber2 = 10 + Random.nextInt(11)

                number1Text.text = randomNumber1.toString()
                number2Text.text = randomNumber2.toString()

                signText.text = operatorText
                correctAnswer = randomNumber1 * randomNumber2
                questionNumber++

            } else if (questionNumber == 5) {

                val yourAnswer = inputText.text.toString()
                if (yourAnswer.isNotEmpty()) {
                    if (yourAnswer == correctAnswer.toString()) {
                        Toast.makeText(this, "正解", Toast.LENGTH_SHORT).show()
                        numberCorrectAnswer++
                    } else {
                        Toast.makeText(this, "不正解", Toast.LENGTH_SHORT).show()
                        numberWrongAnswer++
                    }
                    val answerPage = Intent(this, AnswerActivity::class.java)
                    val questionText =
                        randomNumber1.toString() + operatorText + randomNumber2.toString() + " ="
                    answerPage.putExtra("numberCorrectAnswer", numberCorrectAnswer)
                    answerPage.putExtra("numberWrongAnswer", numberWrongAnswer)
                    startActivity(answerPage)
                    finish()
                }
            }
        }
    }
}
