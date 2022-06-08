package com.example.wb_week_six

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class VM : ViewModel() {

    companion object {
        const val TIMER_INTERVAL = 1000
        const val CALCULATOR_INTERVAL = 10
    }

    private var handler: Handler? = null
    val pi: MutableLiveData<String> = MutableLiveData("")
    val time: MutableLiveData<Long> = MutableLiveData(0L)


    fun onStart() {
        handler = Handler(Looper.getMainLooper())
        val thread = Thread(calculator)
        val thread2 = Thread(timer)
        calculator.run()
        timer.run()
        thread.start()
        thread2.start()
    }

    fun onPause() {
        stopTimer()
    }

    fun onReset() {
        stopTimer()
        time.value = 0L
        pi.value = ""
    }

    private fun stopTimer() {
        handler?.removeCallbacks(timer)
        handler?.removeCallbacks(calculator)
    }

    private var timer: Runnable = object : Runnable {
        override fun run() {
            try {
                time.postValue(time.value?.plus(1000))
            } finally {
                handler!!.postDelayed(this, TIMER_INTERVAL.toLong())
            }
        }
    }

    private var calculator: Runnable = object : Runnable {
        override fun run() {
            try {
                pi.postValue(pi.value?.plus((0..9).random()).toString())
            } finally {
                handler!!.postDelayed(this, CALCULATOR_INTERVAL.toLong())
            }
        }
    }
}