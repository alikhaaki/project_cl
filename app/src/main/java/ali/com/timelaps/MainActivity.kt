package ali.com.timelaps

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.andrognito.flashbar.Flashbar

class MainActivity : AppCompatActivity() {

    private lateinit var tapMeButton: Button
    private lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    private val toast: Toast? = null

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
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val t = Thread(Runnable {
            //  Initialize SharedPreferences
            val getPrefs = PreferenceManager
                .getDefaultSharedPreferences(baseContext)

            //  Create a new boolean and preference and set it to true
            val isFirstStart = getPrefs.getBoolean("firstStart", true)

            //  If the activity has never started before...
            if (isFirstStart) {

                //  Launch app intro
                val i = Intent(this@MainActivity, IntroActivity::class.java)

                runOnUiThread { startActivity(i) }

                //  Make a new preferences editor
                val e = getPrefs.edit()

                //  Edit preference to make it false because we don't want this to run again
                e.putBoolean("firstStart", false)

                //  Apply changes
                e.apply()
            }
        })

        // Start the thread
        t.start()


        tapMeButton = findViewById(R.id.tap_me_button)
        gameScoreTextView = findViewById(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        textYourScore = findViewById(R.id.text_score_tozih)
        switch = findViewById(R.id.switchh)


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
        toast?.cancel()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        toast?.cancel()
    }

    override fun onStop() {
        super.onStop()
        toast?.cancel()
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
                toast?.cancel()
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
        Flashbar.Builder(this).gravity(Flashbar.Gravity.BOTTOM).duration(600)
            .message(getString(R.string.game_over_message,score.toString())).build()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        if (item != null) when {
            item.itemId == R.id.go_to_hard_1_menu -> startActivity(Intent(this, Hard1::class.java))
            item.itemId == R.id.go_to_hardest_gaem -> startActivity(Intent(this, Hardest::class.java))
            item.itemId == R.id.go_to_hard_2_menu -> startActivity(Intent(this, Harder::class.java))
        }
        return true
    }


}
