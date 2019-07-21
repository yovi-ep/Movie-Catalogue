package com.yeputra.moviecatalogue.services

import android.content.Context
import com.firebase.jobdispatcher.*

class RemainderService(val context: Context) {
    private var TAG_DAILY = "com.yeputra.moviecatalogue.services.daily.remainder"
    private var TAG_RELEASE = "com.yeputra.moviecatalogue.services.release.remainder"

    private var mDispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))

    fun startDailyRemainder() {
        val myJob = mDispatcher.newJobBuilder()
                .setService(JobDailyRemainder::class.java)
                .setTag(TAG_DAILY)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(3600, 3610))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(
                        Constraint.DEVICE_IDLE
                )
                .build()

        mDispatcher.mustSchedule(myJob)
    }

    fun stopDailyRemainder() {
        mDispatcher.cancel(TAG_DAILY)
    }

    fun startReleaseRemainder() {
        val myJob = mDispatcher.newJobBuilder()
                .setService(JobReleaseRemainder::class.java)
                .setTag(TAG_RELEASE)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(3600, 3610))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(
                        Constraint.ON_UNMETERED_NETWORK,
                        Constraint.ON_ANY_NETWORK,
                        Constraint.DEVICE_IDLE
                )
                .build()

        mDispatcher.mustSchedule(myJob)
    }

    fun stopReleaseRemainder() {
        mDispatcher.cancel(TAG_RELEASE)
    }
}