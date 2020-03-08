package com.androidtest.myapp.viewmodel

import android.os.CountDownTimer
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.*
import com.androidtest.management.MainConfig
import com.androidtest.myapp.interfaces.ITowerBlackTest
import java.lang.StringBuilder
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class TowerBlockTestViewModel(private val act: ITowerBlackTest.View) : ViewModel() {

    private var lastTime: Long = 0
    private var onTouchDuration: Long = 2000
    private var countDownTimer: CountDownTimer? = null
    private var countDownTimerPurple: CountDownTimer? = null
    private lateinit var listBlockColor: ArrayList<String>
    private var isRemoveFirst: Boolean = false
    private var isConfig: Boolean = false
    var isTouchBlue = MutableLiveData<Boolean>().apply { value = false }
    var isTouchPink = MediatorLiveData<Boolean>().apply { value = false }
    var countDownObserve = MutableLiveData<String>()
    var isShowCountDown = MutableLiveData<Boolean>()
    private var isLastBlock: Boolean = false
    private var isStopTime: Boolean = false

    init {
        setListColor()
    }

    fun setOnTouchListener(btnBlue: View, btnPink: View) {
        onCountDownTimerBlue(btnBlue)
        onCountDownTimerPink(btnPink)
    }

    private fun onCountDownTimerPink(view: View) {
        view.setOnTouchListener(object : View.OnTouchListener {
            var currentMilliTime: Long = 0
            var timeInterval: Long = 1000

            override fun onTouch(v: View, m: MotionEvent): Boolean {
                if (m.action == MotionEvent.ACTION_DOWN) {
                    isTouchPink.value = true
//                    if (act.getCurrentColor() == "") {
//                        return false
//                    }

                    if (act.getCurrentColor() == MainConfig.COLOR.BLUE) {
                        return false
                    }

                    if (act.getCurrentColor() == MainConfig.COLOR.PURPLE) {
                        timeInterval = 100
                    }

                    if (act.getCurrentColor() != MainConfig.COLOR.PURPLE) {
                        isShowCountDown.postValue(true)
                    }

                    currentMilliTime = System.currentTimeMillis()

                    countDownTimer = object : CountDownTimer(onTouchDuration, timeInterval) {
                        override fun onTick(millisUntilFinished: Long) {
                            if (act.getCurrentColor() == MainConfig.COLOR.PURPLE) {
                                isTouchPink.value = true
                                return
                            }

                            val countDownTime = TimeUnit.MILLISECONDS.toSeconds(checkTimeMilli(millisUntilFinished)) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(checkTimeMilli(millisUntilFinished)))
                            countDownObserve.postValue(String.format("%02d", countDownTime))
                        }

                        override fun onFinish() {
                            isShowCountDown.postValue(false)
                            if (act.getCurrentColor() == MainConfig.COLOR.PURPLE) {
                                countDownTimer?.start()
                            } else {
                                isTouchPink.value = false
                                countDownObserve.postValue("00")
                                countDownTimer?.cancel()
                            }
                        }
                    }.start()

                } else if (m.action == MotionEvent.ACTION_UP) {
                    isTouchPink.value = false
                    countDownTimer?.cancel()
                    if (System.currentTimeMillis() - currentMilliTime > onTouchDuration) {
                        return false
                    } else {
                        isShowCountDown.postValue(false)
                        return false
                    }
                }
                else if (m.action == MotionEvent.ACTION_POINTER_UP) {
                    isTouchPink.value = false
                    isShowCountDown.postValue(false)
                    countDownTimer?.cancel()
                }
                else if (m.action == 261 || m.action == 262) {
                    isTouchPink.value = false
                    isShowCountDown.postValue(false)
                    countDownTimer?.cancel()
                }
                return true
            }
        })
    }

    private fun onCountDownTimerBlue(view: View) {
        view.setOnTouchListener(object : View.OnTouchListener {
            var currentMilliTime: Long = 0
            var timeInterval: Long = 1000

            override fun onTouch(v: View, m: MotionEvent): Boolean {
                if (m.action == MotionEvent.ACTION_DOWN) {
                    isTouchBlue.value = true

//                    if (act.getCurrentColor() == "") {
//                        return false
//                    }

                    if (act.getCurrentColor() == MainConfig.COLOR.PINK) {
                        return false
                    }

                    if (act.getCurrentColor() == MainConfig.COLOR.PURPLE) {
                        timeInterval = 100
                    }

                    if (act.getCurrentColor() != MainConfig.COLOR.PURPLE) {
                        isShowCountDown.postValue(true)
                    }

                    currentMilliTime = System.currentTimeMillis()

                    countDownTimer = object : CountDownTimer(onTouchDuration, timeInterval) {
                        override fun onTick(millisUntilFinished: Long) {
                            if (act.getCurrentColor() == MainConfig.COLOR.PURPLE) {
                                isTouchBlue.value = true
                                return
                            }

                            val countDownTime = TimeUnit.MILLISECONDS.toSeconds(checkTimeMilli(millisUntilFinished)) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(checkTimeMilli(millisUntilFinished)))
                            countDownObserve.postValue(String.format("%02d", countDownTime))
                        }

                        override fun onFinish() {
                            isShowCountDown.postValue(false)
                            if (act.getCurrentColor() == MainConfig.COLOR.PURPLE) {
                                countDownTimer?.start()
                            } else {
                                isTouchBlue.value = false
                                countDownObserve.postValue("00")
                                countDownTimer?.cancel()
                            }
                        }
                    }.start()

                } else if (m.action == MotionEvent.ACTION_UP) {
                    isTouchBlue.value = false
                    countDownTimer?.cancel()
                    if (System.currentTimeMillis() - currentMilliTime > onTouchDuration) {
                        //destroy block
                        return false
                    } else {
                        isShowCountDown.postValue(false)
                        return false
                    }
                }
                else if (m.action == MotionEvent.ACTION_POINTER_UP) {
                    isTouchBlue.value = false
                    isShowCountDown.postValue(false)
                    countDownTimer?.cancel()
                }
                else if (m.action == 261 || m.action == 262) {
                    isTouchBlue.value = false
                    isShowCountDown.postValue(false)
                    countDownTimer?.cancel()
                }
                return true
            }
        })
    }

    fun getCorrectDataPurple(): MediatorLiveData<Boolean> {
        return isCorrectData
    }

    private val isCorrectData = MediatorLiveData<Boolean>().apply {
        var blueFlag = false
        var pinkFlag = false
        value = false
        addSource(isTouchBlue) { x -> x?.let {
            blueFlag = x
            value = pinkFlag && blueFlag
//            if (pinkFlag && blueFlag) {
//                value = true
////                isShowCountDown.postValue(true)
//            } else {
////                isShowCountDown.postValue(false)
//            }
        } }
        addSource(isTouchPink) { x -> x?.let {
            pinkFlag = x
//            if (pinkFlag && blueFlag) {
//                value = true
////                isShowCountDown.postValue(true)
//            } else {
////                isShowCountDown.postValue(false)
//            }
            value = pinkFlag && blueFlag
        } }
    }

    fun countDownTimePurple() {
//        isShowCountDown.postValue(true)************
        isPurpleStop = false
        countDownTimerPurple = object : CountDownTimer(onTouchDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val countDownTime = TimeUnit.MILLISECONDS.toSeconds(checkTimeMilli(millisUntilFinished)) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(checkTimeMilli(millisUntilFinished)))

                countDownObserve.postValue(String.format("%02d", countDownTime))
            }

            override fun onFinish() {
                if (!isPurpleStop) {
                    isShowCountDown.postValue(false)
                    act.setStopTime()
                    countDownObserve.postValue("00")
                    countDownTimerPurple?.cancel()
                }
            }
        }.start()
    }

    private var isPurpleStop: Boolean = false
    fun cancelCountDownTimePurple() {
        isPurpleStop = true
        isShowCountDown.postValue(false)
        countDownTimerPurple?.onFinish()
        countDownTimerPurple?.cancel()
        //********* countDownTimerPurple?.cancel()
    }

    fun cancelCountDownTime() {
        countDownTimer?.cancel()
    }

    fun getCountDownObserve(): LiveData<String> {
        return countDownObserve
    }

    fun isShowCountDownObserve(): LiveData<Boolean> {
        return isShowCountDown
    }

    fun setStartAllTime() {
        if (!isRemoveFirst) {
            isRemoveFirst = true
            lastTime = System.currentTimeMillis()
        }
    }

    fun setOnConfig(config: Boolean) {
        isConfig = config
    }

    fun getOnConfig(): Boolean {
        return isConfig
    }

    fun getAllTime(): String {
        val resTime = (System.currentTimeMillis() - lastTime)
        val resMin = TimeUnit.MILLISECONDS.toMinutes(resTime).toInt()
        val resSecond = TimeUnit.MILLISECONDS.toSeconds(resTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(resTime))

        if (resMin > 0) {
            return StringBuilder(resMin.toString()).append(" min").append(" ").append(resSecond).append(" s").toString()
        }
        return StringBuilder(resSecond.toString()).append(" s").toString()
    }

    private fun checkTimeMilli(milli: Long): Long {
        if (milli % 1000 == 0L) {
            return milli
        }
        return (kotlin.math.abs(1000 - (milli % 1000)) + milli)
    }

    fun getListBlockColor(): ArrayList<String> {
        return listBlockColor
    }

    private fun setListColor() {
        listBlockColor = ArrayList()
        val listColor = ArrayList<String>()
        listColor.add(MainConfig.COLOR.BLUE)
        listColor.add(MainConfig.COLOR.PINK)

        val rand = Random()
        for (i in 0..18) {
            listBlockColor.add(listColor[rand.nextInt(listColor.size)])
        }
        listBlockColor.add(MainConfig.COLOR.PURPLE)
    }

    fun setIsFinish() {
        isStopTime = true
    }

    fun getIsFinish(): Boolean {
        return isStopTime
    }

    fun setLastBlock() {
        isLastBlock = true
        act.setLastBlock()
    }

    fun getLastBlock(): Boolean {
        return isLastBlock
    }

}