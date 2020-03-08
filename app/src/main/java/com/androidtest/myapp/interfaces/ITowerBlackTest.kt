package com.androidtest.myapp.interfaces

interface ITowerBlackTest {

    interface View {

        fun initView()

        fun initObserv()

        fun initRecyclerView()

        fun setData()

        fun setStopTime()

        fun setTextCountDownTimer(time: String)

        fun setVisibleTextCountDown()

        fun setHideTextCountDown()

        fun hideFrameCursor()

        fun getCurrentColor(): String

        fun removeBlock()

        fun disableButton()

        fun setLastBlock()

    }

}