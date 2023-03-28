package com.example.myapplication



import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var countdownTextView: TextView
    private lateinit var startButton: Button

    private var countdownValue = 100
    private var isCountingDown = false
    private val countdownJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + countdownJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countdownTextView = findViewById(R.id.countdownTextView)
        startButton = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            if (!isCountingDown) {
                isCountingDown = true
                startCountdown()
            }
        }
    }

    private fun startCountdown() {
        countdownValue = 100
        startButton.isEnabled = false

        coroutineScope.launch {
            while (countdownValue > 0) {
                countdownValue -= 1
                countdownTextView.text = countdownValue.toString()
                delay(1000)
            }
            startButton.isEnabled = true
            isCountingDown = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countdownJob.cancel()
    }
}
