package com.tonon.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import com.tonon.a7minuteworkout.databinding.ActivityExerciseBinding
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    lateinit var binding: ActivityExerciseBinding

    private var restTimer: CountDownTimer ?= null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel> ?= null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech ?= null
    private var player: MediaPlayer ?= null

    private var exerciseAdapter: ExerciseStatusAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        toolbar()

        exerciseList = Constants.defaultExerciseList()

        tts = TextToSpeech(this, this)

        setupRestView()

        setupExerciseStatusRecyclerView()
    }

    private fun toolbar(){
        val toolbar = binding.toolbarExerciseActivity

        toolbar.setNavigationIcon(getDrawable(R.drawable.back))
        toolbar.setTitle("7 Minutes Workout")
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationOnClickListener {
            customDialogForBackButton()
        }
    }


    private fun setupRestView(){

        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        }catch (e: Exception){
            e.printStackTrace()
        }

        val restView = binding.restView
        val exerciseView = binding.llExerciseView

        restView.visibility = View.VISIBLE
        exerciseView.visibility = View.GONE

        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        val upcomingExerciseName = binding.upcomingExerciseName

        upcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()

        setRestProgressBar()
    }

    private fun setRestProgressBar(){
        val progressBar = binding.progressBar
        progressBar.progress = restProgress
        restTimer = object: CountDownTimer(10000,1000){

            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = 10 - restProgress

                var timer = binding.timer
                timer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setupExerciseView()
            }
        }.start()
    }

    private fun setupExerciseView() {

        val restView = binding.restView
        val exerciseView = binding.llExerciseView

        restView.visibility = View.GONE
        exerciseView.visibility = View.VISIBLE


        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())


        val image = binding.ivImage
        val exerciseName = binding.tvExerciseName

       image.setImageResource(exerciseList!![currentExercisePosition].getImage())
       exerciseName.text = exerciseList!![currentExercisePosition].getName()


        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {

        val progressbarExercise = binding.progressBarExercise
        progressbarExercise.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressbarExercise.progress = 30 - exerciseProgress
                val exerciseTimer = binding.tvExerciseTimer
                exerciseTimer.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {

                if (currentExercisePosition < exerciseList?.size!! - 1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }else{
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }


    override fun onInit(status: Int) {
        if ( status == TextToSpeech.SUCCESS){
            val result = tts!!.isLanguageAvailable(Locale("pt", "BR"))
            if (result == TextToSpeech.LANG_NOT_SUPPORTED){
                tts!!.setLanguage(Locale("pt", "BR"))
            }
        }else{
            Log.e("TTS", "A inicialização falhou!" )
        }
    }

    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setupExerciseStatusRecyclerView(){
        exerciseStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        exerciseStatus.adapter = exerciseAdapter
    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)

        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)

        customDialog.yes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        customDialog.no.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    override fun onDestroy() {
        if (restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if (player != null){
            player!!.stop()
        }

        super.onDestroy()
    }
}