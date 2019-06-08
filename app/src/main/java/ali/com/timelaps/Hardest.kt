@file:Suppress("DEPRECATION")

package ali.com.timelaps

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.support.annotation.ColorInt
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AbsoluteLayout
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import java.util.*

class Hardest : AppCompatActivity() {


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
    private val TAG = this@Hardest.javaClass.getSimpleName()

    private lateinit var dialog: Dialog


    companion object {
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }


    public override fun onResume() {
        super.onResume()
        dialog.dismiss()
        resetGame()
    }

    private var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hardest)


        dialog = Dialog(this@Hardest)


        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "خیلی خیلی خیلی سخت"

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

        tapMeButton.setOnClickListener {

            if (switch.isChecked) {
                mediaPlayer.start()
            }


            val displayMetrics = this.resources.displayMetrics
            val dispHH = (displayMetrics.heightPixels / displayMetrics.density).toInt()
            val dispWW = (displayMetrics.widthPixels / displayMetrics.density).toInt()
            val ran = Random()
            val xxxxx = 10 + ran.nextInt((dispHH - 58) - 10 + 1)
            val yyyyy = 10 + ran.nextInt((dispWW - 58) - 10 + 1)
            @Suppress("DEPRECATION") val position = tapMeButton.layoutParams as AbsoluteLayout.LayoutParams
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

    @Suppress("DEPRECATION")
    private fun endGame() {
//        val parent = findViewById<View>(android.R.id.content)
//
//        Snackbar.make(parent, getString(R.string.game_over_message, score.toString()), Snackbar.LENGTH_LONG).setAction(
//            "باشه !"
//        ) { }.setActionTextColor(resources.getColor(R.color.black)).withColor(resources.getColor(R.color.colorPrimary))
//            .show()


//        val alert = ViewDialog()
//        alert.showDialog(this@Hardest)

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


    private fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
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

        dialog = Dialog(this@Hardest)
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
        if (!this@Hardest.isFinishing) {
            dialog.show()

        }

    }
}
