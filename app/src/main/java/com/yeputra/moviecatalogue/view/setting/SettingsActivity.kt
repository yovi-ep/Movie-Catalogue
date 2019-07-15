package com.yeputra.moviecatalogue.view.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.jobdispatcher.*
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.repository.preference.SettingPref
import com.yeputra.moviecatalogue.services.DailyRemainderService
import kotlinx.android.synthetic.main.settings_activity.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var setting: SettingPref
    private lateinit var mDispatcher: FirebaseJobDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        setupData()
        setupAppearance()
    }

    private fun setupData() {
        setting = SettingPref(this)
        mDispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))

        sw_daily_remainder.isChecked = setting.dailyRemainder
        sw_release_remainder.isChecked = setting.releaseRemainder
    }

    private fun setupAppearance() {
        layout_daily_remainder.setOnClickListener {
            sw_daily_remainder.isChecked = !sw_daily_remainder.isChecked
        }

        layout_release_remainder.setOnClickListener {
            sw_release_remainder.isChecked = !sw_release_remainder.isChecked
        }

        sw_daily_remainder.setOnCheckedChangeListener { _, isChecked ->
            SettingPref(this).dailyRemainder = isChecked

            if (isChecked)
                startDailyRemainder()
            else
                stopDailyRemainder()
        }
    }

    private fun startDailyRemainder() {
        val myJob = mDispatcher.newJobBuilder()
                .setService(DailyRemainderService::class.java)
                .setTag(packageName)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(0, 360))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(
                        // hanya berjalan saat ada koneksi yang unmetered (contoh Wifi)
                        /*Constraint.ON_UNMETERED_NETWORK,*/
                        // hanya berjalan ketika device di charge
                        /*Constraint.DEVICE_CHARGING*/

                        // berjalan saat ada koneksi internet
                        //Constraint.ON_ANY_NETWORK

                        // berjalan saat device dalam kondisi idle
                        Constraint.DEVICE_IDLE
                )
                /*.setExtras(myExtrasBundle)*/
                .build()

        mDispatcher.mustSchedule(myJob)
    }

    private fun stopDailyRemainder() {
        mDispatcher.cancel(packageName)
    }
}