package app.sakai.tororoimo.anzan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import java.util.prefs.PreferencesFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val realm: Realm = Realm.getDefaultInstance()

    var randomNumber1 = 0
    var randomNumber2 = 0
    val operatorText = " × "
    var correctAnswer = 0
    var isMistakeMode: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var questionNumber = 0
        var numberCorrectAnswer = 0
        var numberWrongAnswer = 0

        val dataStore: SharedPreferences = getSharedPreferences("DataStore", Context.MODE_PRIVATE)
        isMistakeMode = intent.getBooleanExtra("MODE", false)
        val x = realm.where(Question::class.java).findAll()
        x.forEach{
            Log.d("hoge", "${it.id} ${it.factor1} ${it.factor2}")
        }


        val question: RealmResults<Question>? = read()
        if (isMistakeMode) {
            createMistakeQuestion(question, dataStore)
        } else {
            createQuestion()
        }
        questionNumber++

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
                        save(randomNumber1, randomNumber2, dataStore)
                    }
                }

                if (isMistakeMode) {
                    createMistakeQuestion(question, dataStore)
                } else {
                    createQuestion()
                }
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
                    answerPage.putExtra("numberCorrectAnswer", numberCorrectAnswer)
                    answerPage.putExtra("numberWrongAnswer", numberWrongAnswer)
                    startActivity(answerPage)
                    finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }



    fun createQuestion() {
        randomNumber1 = 10 + Random.nextInt(11)
        randomNumber2 = 10 + Random.nextInt(11)

        number1Text.text = randomNumber1.toString()
        number2Text.text = randomNumber2.toString()

        signText.text = operatorText
        correctAnswer = randomNumber1 * randomNumber2
    }

    fun createMistakeQuestion (question: RealmResults<Question>?, dataStore: SharedPreferences)  {
        if (question != null) {
            val id = dataStore.getInt("REALM_ID", 0)
            val randomId = Random.nextInt(id)
            val x : Question? = realm.where(Question::class.java).equalTo("id", randomId).findFirst()
            Log.d("hoge2", x.toString())
            Log.d("id", id.toString())
            if (x != null) {
                randomNumber1 = x.factor1
                randomNumber2 = x.factor2
                number1Text.text = randomNumber1.toString()
                number2Text.text = randomNumber2.toString()

                signText.text = operatorText
                correctAnswer = randomNumber1 * randomNumber2
                Log.d("state", "1")
                Log.d("x.factor1", x.factor1.toString())
                Log.d("randomNumber1", randomNumber1.toString())
            } else {
                isMistakeMode = false
                Log.d("state", "2")
            }


        } else {
            isMistakeMode = false
            Log.d("state", "3")
        }
    }

    fun read(): RealmResults<Question>? {
        return realm.where(Question::class.java).findAll()
    }

    fun save(factor1: Int, factor2: Int, dataStore: SharedPreferences) {
        val id = dataStore.getInt("REALM_ID", 0)
        val newId = id + 1
        val editor = dataStore.edit()
        editor.putInt("REALM_ID", newId)
        editor.apply()

        realm.executeTransaction{
            val newQuestion: Question = it.createObject(Question::class.java, newId)
            newQuestion.factor1 = factor1
            newQuestion.factor2 = factor2
        }
    }
}
