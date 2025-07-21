package com.satya.timetask

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.satya.timetask.ui.theme.TimeTaskTheme
import java.util.* //Timer and TimerTask

class MainActivity : ComponentActivity() {

    //Handler for posting UI updates from TimerTask (runs on background thread)
    private val handler = Handler(Looper.getMainLooper())

    // Timer object to schedule the TimerTask
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TimerCounter()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    @Composable
    fun TimerCounter() { //Corrected name
        var counter by remember { mutableStateOf(0) }

        // Runs once when the composable is first composed
        LaunchedEffect(Unit) {
            val timerTask: TimerTask = object : TimerTask() {
                override fun run() {
                    // background thread
                    handler.post {
                        if(counter<60){
                        counter++}
                        else{
                            timer.cancel()
                        }
                    }
                }
            }
            timer.schedule(timerTask, 15000, 2000)
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Timer: $counter",
                fontSize = 32.sp
            )
        }
    }
}
