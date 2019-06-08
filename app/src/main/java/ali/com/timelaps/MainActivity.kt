package ali.com.timelaps

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.support.annotation.ColorInt
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.support.v7.widget.Toolbar
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.google.firebase.analytics.FirebaseAnalytics


@Suppress("DEPRECATION")
open class MainActivity : AppCompatActivity() {

    private var score = 0
    internal val initialCountDown: Long = 60000
    internal val countDownInterval: Long = 1000
    private var gameStarted = false
    internal var timeLeftOnTimer: Long = 60000
    private lateinit var tapMeButton: Button
    private lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var switch: SwitchCompat
    private lateinit var textYourScore: TextView
    private var toolbar: Toolbar? = null
    private var mFireBaseAnalytics: FirebaseAnalytics? = null
    private lateinit var dialog: Dialog


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        treadIntroSetup()

        dialog = Dialog(this@MainActivity)

        mFireBaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        mFireBaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)

        toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "سطح معمولی"
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

            if (textYourScore.visibility == View.VISIBLE) {
                textYourScore.visibility = View.GONE
            }


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

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {

        //        val parent = findViewById<View>(android.R.id.content)
//
//        Snackbar.make(parent, getString(R.string.game_over_message, score.toString()), Snackbar.LENGTH_LONG).setAction(
//            "باشه !"
//        ) { }.setActionTextColor(resources.getColor(R.color.black)).withColor(resources.getColor(R.color.colorPrimary))
//            .show()


        customDialogMethod()

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

    private fun treadIntroSetup() {

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


    }

    companion object {
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }


    fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
        this.view.setBackgroundColor(colorInt)
        return this
    }

    inner class ViewDialog {

        fun showDialog(activity: Activity) {
            val dialog = Dialog(activity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.newcustom_layout_dialog)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val textViewDialog: TextView = dialog.findViewById(R.id.text_dialog_tozih_score)
            textViewDialog.text = getString(R.string.string_tozih_dialog, score.toString())

            val frameYes: FrameLayout = dialog.findViewById(R.id.frmOk)

//            val mDialogOk = activity.frmOk
            frameYes.setOnClickListener {
                dialog.cancel()
            }

            dialog.show()
        }
    }


    public override fun onResume() {
        super.onResume()
        resetGame()
    }

    override fun onPause() {
        dialog.dismiss()
        resetGame()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        resetGame()
        dialog.dismiss()
    }

    override fun onStop() {
        super.onStop()
        resetGame()
        dialog.dismiss()
    }

    private fun customDialogMethod() {

        dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.newcustom_layout_dialog)
        val params = WindowManager.LayoutParams()
        params.copyFrom(dialog.window!!.attributes)
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.gravity = Gravity.CENTER
        dialog.window!!.attributes = params
        dialog.window!!.setBackgroundDrawableResource(R.color.colorPrimary)
        val frameLayout = dialog.findViewById<FrameLayout>(R.id.frmOk)
        val textViewDialog = dialog.findViewById<TextView>(R.id.text_dialog_tozih_score)

        textViewDialog.text = getString(R.string.string_tozih_dialog, score.toString())


        frameLayout.setOnClickListener {
            dialog.dismiss()
        }
        if (!this@MainActivity.isFinishing) {
            dialog.show()

        }

    }
}
