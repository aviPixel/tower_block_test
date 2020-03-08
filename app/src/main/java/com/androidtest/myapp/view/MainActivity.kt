package com.androidtest.myapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.androidtest.management.setStatusBarWhite
import com.androidtest.myapp.interfaces.ITowerBlackTest
import com.androidtest.myapp.viewmodel.TowerBlackTestViewModelFactory
import com.androidtest.myapp.viewmodel.TowerBlockTestViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidtest.management.MainConfig
import com.androidtest.management.hide
import com.androidtest.management.visible
import com.androidtest.myapp.R
import kotlinx.android.synthetic.main.dialog_count_down.*
import kotlinx.android.synthetic.main.dialog_count_down.tvDetail
import kotlinx.android.synthetic.main.dialog_total_time.*

class MainActivity : AppCompatActivity(), ITowerBlackTest.View {

    private val viewModel by lazy {
        ViewModelProviders.of(this, TowerBlackTestViewModelFactory(this))
            .get((TowerBlockTestViewModel::class.java))
    }

    private var recyclerAdapter: ListBlockAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setStatusBarWhite()

        initView()

        initObserv()

        setData()
    }

    override fun initView() {
        initRecyclerView()

        viewModel.setOnTouchListener(
            findViewById<FrameLayout>(R.id.btnBlue),
            findViewById<FrameLayout>(R.id.btnPink)
        )

        viewModel.setOnConfig(true)
    }

    override fun initObserv() {
        viewModel.getCountDownObserve().observe(this, androidx.lifecycle.Observer { data ->

            if (viewModel.getOnConfig()) {
                viewModel.setOnConfig(false)
                return@Observer
            }

            tvDetail?.text = data
            if (data == "00") {
                viewModel.setStartAllTime()
                removeBlock()
            }

        })

        viewModel.isShowCountDownObserve().observe(this, androidx.lifecycle.Observer { data ->
            if (data) {
                setVisibleTextCountDown()
            } else {
                setHideTextCountDown()
            }
        })

        viewModel.getCorrectDataPurple().observe(this, androidx.lifecycle.Observer { data ->
            if (getCurrentColor() != MainConfig.COLOR.PURPLE) {
                viewModel.cancelCountDownTime()
                return@Observer
            }

            if (data) {
                viewModel.cancelCountDownTime()
                viewModel.countDownTimePurple()
            } else {
                viewModel.cancelCountDownTimePurple()
            }
        })
    }

    override fun removeBlock() {
        recyclerAdapter?.removeBlock()
    }

    override fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
        rvListColor?.layoutManager = layoutManager
        rvListColor.setOnTouchListener { _, _ -> true }

        recyclerAdapter = ListBlockAdapter(this, object : ListBlockAdapter.OnItemListener {
            override fun onLastBlock(){
                viewModel.setLastBlock()
            }
        })
//        recyclerAdapter?.setHasStableIds(true)
        rvListColor?.adapter = recyclerAdapter
    }

    override fun setData() {
        recyclerAdapter?.setDataItem(viewModel.getListBlockColor())
    }

    override fun getCurrentColor(): String {
        return recyclerAdapter?.isCurrentColor() ?: ""
    }

    override fun setStopTime() {
        Log.e("stoptime", "stoptime")
        viewModel.setIsFinish()
        disableButton()
        hideFrameCursor()
        dialogTotalTime?.visible()
        tvTotalTime.text = viewModel.getAllTime()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.getIsFinish()) {
            disableButton()
            hideFrameCursor()
            dialogTotalTime?.visible()
        }
        if (viewModel.getLastBlock()) {
            setLastBlock()
        }
    }

    override fun setTextCountDownTimer(time: String) {
        tvDetail?.text = time
    }

    override fun setVisibleTextCountDown() {
        dialogCountDown?.visible()
    }

    override fun setHideTextCountDown() {
        dialogCountDown?.hide()
    }

    override fun hideFrameCursor() {
        frameCursor?.hide()
    }

    override fun disableButton() {
        btnPink.isEnabled = false
        btnBlue.isEnabled = false
    }

    override fun setLastBlock() {
        dummyItem?.hide()
        dummyItemPurple?.visible()
    }

    override fun onSaveInstanceState(outState: Bundle) {
//        outState?.run {
//            putBoolean("view_purple", true)
//        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
//        val isView = savedInstanceState?.getBoolean("view_purple")
    }

}