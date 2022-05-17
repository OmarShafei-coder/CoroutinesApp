package com.omarshafei.coroutinesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    private final val TAG = "sahfaishfioadfhashfoas"
    lateinit var text: TextView
    val parentJob = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text = findViewById(R.id.text)

////        async await
//        GlobalScope.launch {
//            val time = measureTimeMillis {
//                val x = async { printMyTextAfterDelay("Omar") }
//                val y = async { printMyTextAfterDelay2("Omar") }
//
//                if(x.await() == y.await()) {
//                    Log.d(TAG, "onCreate: Equals")
//                } else {
//                    Log.d(TAG, "onCreate: Not Equals")
//                }
//            }
//            Log.d(TAG, "time: $time")
//        }
//
/////////////////////////////////////////////////////////////////////////////////////////////
////        Structured Concurrency and Jobs
//        val coroutineScope = CoroutineScope(Dispatchers.IO + parentJob)
//        val job = coroutineScope.launch {
//            val child1 = launch { printMyTextAfterDelay("coroutine 1") }
//            val child2 = launch { printMyTextAfterDelay2("coroutine 2") }
//
//            // wait for all these jobs to be finished and then continue
//            joinAll(child1, child2)
//            // wait for this job to be finished and then continue
//            child1.join()
//
//            launch { delay(2000) }
//        }
//
////        //Channels
//        val channel = Channel<String>(2)
//        val charList = arrayOf("A", "B", "C", "D")
//
//        runBlocking {
//            launch {
//                for (char in charList) {
//                    // this is suspend function
//                    channel.send(char)
//                    // this is not suspend function
//                    channel.trySend(char)
//                }
//            }
//
//            launch {
//                for (char in channel) {
//                    Log.d(TAG, "char: $char")
//                }
//            }
//        }
///////////////////////////////////////////////////////////////////////////////////////////
//        Flows
//        GlobalScope.launch {
//            // producer 1 step
//            val flow1 = flow<Int> {
//                for(i in 1..3) {
//                    emit(i)
//                    delay(1000)
//                }
//            }
//            // producer 2 step
//            val flow2 = flow<String> {
//                val list = listOf<String>("A", "B", "C", "D")
//                for (item in list) {
//                    emit(item)
//                    delay(2000)
//                }
//            }
//
//            // waiting for result of flow1 and flow2
//            // intermediate step
//            flow1.zip(flow2) {a, b -> "$a,$b"
//            // collector step
//            }.collect {
//                Log.d(TAG, "onCreate: $it")
//            }
//        }

//        StateFlow and viewModel
        val timerViewModel = ViewModelProvider(this)[TimerViewModel::class.java]
        timerViewModel.startTimer()

        //we used launchWhenStarted instead of launch to work only when the app is in onStart()
        lifecycleScope.launchWhenStarted {
            timerViewModel.timerStateFlow.collect {
                text.text = it.toString()
                Log.d(TAG, "onCreate: $it")
            }
        }
    }

    private suspend fun printMyTextAfterDelay(myText: String): String {
        delay(2000)
        Log.d(TAG, "printMyTextAfterDelay: $myText")
        return myText
    }

    private suspend fun printMyTextAfterDelay2(myText: String): String {
        delay(3000)
        Log.d(TAG, "printMyTextAfterDelay2: $myText")
        return myText
    }
}