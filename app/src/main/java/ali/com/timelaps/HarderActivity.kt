@file:Suppress("DEPRECATION")

package ali.com.timelaps

import ali.com.timelaps.SampleHelperClass.ID_PUBLIC
import ali.com.timelaps.SampleHelperClass.TAG_MAIN_ACTIVITY
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.support.annotation.ColorInt
import android.support.design.widget.Snackbar
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.AbsoluteLayout
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import java.util.*

class HarderActivity : AppCompatActivity() {

    private lateinit var tapMeButton: Button
    private lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    private var score = 0
    private var gameStarted = false
    private lateinit var countDownTimer: CountDownTimer
    internal val initialCountDown: Long = 20000
    internal val countDownInterval: Long = 1000
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var switch: SwitchCompat
    private lateinit var textYourScore: TextView
    internal var timeLeftOnTimer: Long = 20000
    private lateinit var dialog: Dialog
    private lateinit var toolbar: Toolbar

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_hard2, menu)
        return true

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)

        when (item?.itemId) {

            R.id.menu_hard2_mainactivity -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            R.id.menu_hard2_hard1 -> {
                startActivity(Intent(this, HardActivity::class.java))
                finish()
            }
            R.id.menu_hard2_hardest -> {
                startActivity(Intent(this, HardestActivity::class.java))
                finish()
            }
        }

        return true
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val inttttExtra = intent
        val striiiiiiing = inttttExtra.getStringExtra(ID_PUBLIC)
        if (striiiiiiing == TAG_MAIN_ACTIVITY) {
            startActivity(Intent(this, MainActivity::class.java))

        }
    }

    private lateinit var absoluteLayout: AbsoluteLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_harder)

        dialog = Dialog(this@HarderActivity)


        absoluteLayout = findViewById(R.id.absolute_layout)

        toolbar = findViewById(R.id.toolbar)
        tapMeButton = findViewById(R.id.tap_me_button)
        gameScoreTextView = findViewById(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        textYourScore = findViewById(R.id.text_score_tozih)
        switch = findViewById(R.id.switchh)

        val lllllllll = absoluteLayout.measuredHeight
        val bbbbbbbb = absoluteLayout.measuredWidth
        val sssss = absoluteLayout.measuredHeightAndState

        Log.i("harderrrrrrrrr", "mHe  =  $lllllllll  mWi = $bbbbbbbb  mesHWW = $ $sssss ... ")


        val vto = absoluteLayout.getViewTreeObserver()
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    absoluteLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this)
                } else {
                    absoluteLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                }
                val width = absoluteLayout.getMeasuredWidth()
                val height = absoluteLayout.getMeasuredHeight()

                Log.i("harderrrrrrrrr", "mHe  =  $width  mWi = $height  ")

            }
        })





        setSupportActionBar(toolbar)
        supportActionBar?.title = "سخت"

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

            textYourScore.visibility = View.GONE

            if (switch.isChecked) {
                mediaPlayer.start()
            }

            val random = Random()
            val red = random.nextInt(256 - 70) + 70
            val blue = random.nextInt(256 - 70) + 70
            val yellow = random.nextInt(256 - 70) + 70
            val backColor = Color.argb(255, red, blue, yellow)


            val drawable = tapMeButton.background
            DrawableCompat.setTint(drawable, backColor)

            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_animation)
            view.startAnimation(bounceAnimation)


            val displayMetrics = this.resources.displayMetrics
            val displayHeight = (displayMetrics.heightPixels / displayMetrics.density).toInt()
            val displayWidth = (displayMetrics.widthPixels / displayMetrics.density).toInt()
            val ran = Random()
            val xPosition = 10 + ran.nextInt((displayHeight - 58) - 10 + 1)
            val yPosition = 10 + ran.nextInt((displayWidth - 58) - 10 + 1)
            @Suppress("DEPRECATION") val position = tapMeButton.layoutParams as AbsoluteLayout.LayoutParams
            position.x = xPosition
            position.y = yPosition
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
//        Snackbar.make(parent, getString(R.string.game_over_message, score.toString()), Snackbar.LENGTH_LONG).setAction(
//            "باشه  "
//        ) { }.setActionTextColor(resources.getColor(R.color.black)).withColor(resources.getColor(R.color.colorPrimary))
//            .show()


//        val alert = ViewDialog()
//        alert.showDialog(this@HarderActivity)
//

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


        dialog = Dialog(this@HarderActivity)
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
        if (!this@HarderActivity.isFinishing) {
            dialog.show()

        }


    }

    companion object {
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    public override fun onResume() {
        super.onResume()
        dialog.dismiss()
        resetGame()
    }

}