package ali.com.timelaps

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AbsoluteLayout
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.math.log

class Harder : AppCompatActivity() {


    private lateinit var tapMeButton: Button
    private lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    private var score = 0
    private var gameStarted = false
    private lateinit var countDownTimer: CountDownTimer
    internal val initialCountDown: Long = 60000
    internal val countDownInterval: Long = 1000
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var switch: SwitchCompat
    private lateinit var textYourScore: TextView
    internal var timeLeftOnTimer: Long = 60000
    private val TAG = this@Harder.javaClass.simpleName


    companion object {
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    private fun randInt(min: Int, max: Int): Int {


        val rand: Random? = null


        return rand!!.nextInt(max - min + 1) + min
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_harder)


        tapMeButton = findViewById(R.id.tap_me_button)
        gameScoreTextView = findViewById(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        textYourScore = findViewById(R.id.text_score_tozih)
        switch = findViewById(R.id.switchh)

        switch.textOn = "On"
        switch.textOff = "Off"

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeftOnTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
            restoreGame()
        } else {
            resetGame()
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.mmm)
        tapMeButton.isSoundEffectsEnabled = false

        tapMeButton.setOnClickListener { view ->

            textYourScore.visibility=View.GONE

            if (switch.isChecked) {
                mediaPlayer.start()
            }

            val displayMetrics = this.resources.displayMetrics

            val dispHH = (displayMetrics.heightPixels / displayMetrics.density).toInt()
            val dispWW = (displayMetrics.widthPixels / displayMetrics.density).toInt()

            val ran = Random()
            val x = ran.nextInt(dispHH) + 1
            val y = ran.nextInt(dispWW) + 1

            val xxxxx = 10+ran.nextInt((dispHH-58)-10+1)
            val yyyyy = 10+ran.nextInt((dispWW-58)-10+1)

            val position = tapMeButton.layoutParams as AbsoluteLayout.LayoutParams


            Log.i("my_tag height ", "height is = $dispHH")
            Log.i("my_tag width  ", "width  is = $dispWW")

            Log.i("my_tag xxxx  ", "xxxx  is = $x")
            Log.i("my_tag yyy  ", "yyyy  is = $y")

            position.x = xxxxx
            position.y = yyyyy


            tapMeButton.layoutParams = position


            incrementScore()
        }

    }

    private fun restoreGame() {
        gameScoreTextView.text = getString(R.string.your_score, score.toString())
        val restoredTime = timeLeftOnTimer / 1000
        timeLeftTextView.text = getString(R.string.time_left, restoredTime.toString())

        countDownTimer = object : CountDownTimer(timeLeftOnTimer, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                var timeLeft = millisUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.time_left, timeLeft.toString())
            }

            override fun onFinish() {
                endGame()
            }
        }

        countDownTimer.start()
        gameStarted = true

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(SCORE_KEY, score)
        outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer)
        countDownTimer.cancel()
    }


    private fun resetGame() {
        score = 0
        gameScoreTextView.text = getString(R.string.your_score, score.toString())
        val initialTimeLeft = initialCountDown / 1000
        timeLeftTextView.text = getString(R.string.time_left, initialTimeLeft.toString())

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.time_left, timeLeft.toString())
            }

            override fun onFinish() {
                endGame()
            }
        }
        gameStarted = false
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.game_over_message, score.toString()), Toast.LENGTH_SHORT).show()
        textYourScore.visibility = View.VISIBLE
        textYourScore.text = getString(R.string.your_score_tozih, score.toString())
        resetGame()
    }

    private fun incrementScore() {
        if (!gameStarted) {
            startGame()
        }
        score += 1
        val newScore = getString(R.string.your_score, score.toString())
        gameScoreTextView.text = newScore

    }


//
//    DisplayMetrics displayMetrics1 = this.getResources().getDisplayMetrics();
//
//    int dispHH = (int) (displayMetrics1.heightPixels / displayMetrics1.density);
//    int dispWW = (int) (displayMetrics1.widthPixels / displayMetrics1.density);
//
//    Log.i(TAG, "dispHH = " + dispHH);
//    Log.i(TAG, "dispWW = " + dispWW);
//
//    Random random = new Random();
//
//    AbsoluteLayout.LayoutParams position = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
//
//
//    position.x = (random.nextInt(dispHH)) - 8;
//    position.y = (random.nextInt(dispWW)) - 8;
//
//
//    button.setLayoutParams(position);
}
