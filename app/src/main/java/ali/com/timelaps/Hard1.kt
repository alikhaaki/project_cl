package ali.com.timelaps

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.support.annotation.ColorInt
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class Hard1 : AppCompatActivity() {

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

    companion object {
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }


    public override fun onResume() {
        super.onResume()
        resetGame()

    }

    private var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hard1)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "سطح متوسط"
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


            textYourScore.visibility = View.INVISIBLE

            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.abc_fade_in)
            view.startAnimation(bounceAnimation)
            if (switch.isChecked) {
                mediaPlayer.start()
            }

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

    override fun onDestroy() {
        super.onDestroy()

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
//        Toast.makeText(this, getString(R.string.game_over_message, score.toString()), Toast.LENGTH_SHORT).show()

        val parent = findViewById<View>(android.R.id.content)

        Snackbar.make(parent, getString(R.string.game_over_message, score.toString()), Snackbar.LENGTH_LONG).setAction(
            "باشه !"
        ) { }.setActionTextColor(resources.getColor(R.color.black)).withColor(resources.getColor(R.color.colorPrimary))
            .show()

        textYourScore.visibility = View.VISIBLE
        textYourScore.text = score.toString()
        resetGame()
    }

    private fun incrementScore() {
        if (!gameStarted) {
            startGame()
        }
        score = score + 1
        val newScore = getString(R.string.your_score, score.toString())
        gameScoreTextView.text = newScore

    }

    private fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
        this.view.setBackgroundColor(colorInt)
        return this
    }

}
