package com.yeputra.moviecatalogue.job

import android.content.Context
import android.util.Log
import com.firebase.jobdispatcher.*


class JobFactory(val context: Context) {
    companion object {
        const val ID_DAILY_REMAINDER = 100
        const val ID_RELEASE_REMAINDER = 102
    }
    private val TAG = JobFactory::class.java.simpleName
    private var TAG_DAILY = "com.yeputra.moviecatalogue.services.daily.remainder"
    private var TAG_RELEASE = "com.yeputra.moviecatalogue.services.release.remainder"

    private var mDispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))

    fun startDailyRemainder() {
        Log.d(TAG, "startDailyRemainder")
        val myJob = mDispatcher.newJobBuilder()
                .setService(JobDailyRemainder::class.java)
                .setTag(TAG_DAILY)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(0, 30))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .build()

        mDispatcher.mustSchedule(myJob)
    }

    fun stopDailyRemainder() {
        Log.d(TAG, "stopDailyRemainder")
        mDispatcher.cancel(TAG_DAILY)
    }

    fun startReleaseRemainder() {
        Log.d(TAG, "startReleaseRemainder")
        val myJob = mDispatcher.newJobBuilder()
                .setService(JobReleaseRemainder::class.java)
                .setTag(TAG_RELEASE)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(0, 30))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(
                        Constraint.ON_UNMETERED_NETWORK,
                        Constraint.ON_ANY_NETWORK
                )
                .build()

        mDispatcher.mustSchedule(myJob)
    }

    fun stopReleaseRemainder() {
        Log.d(TAG, "stopReleaseRemainder")
        mDispatcher.cancel(TAG_RELEASE)
    }
}