package com.yeputra.moviecatalogue.job

import android.content.Context
import com.firebase.jobdispatcher.*


class JobFactory(val context: Context) {
    private var TAG_DAILY = "com.yeputra.moviecatalogue.services.daily.remainder"
    private var TAG_RELEASE = "com.yeputra.moviecatalogue.services.release.remainder"
    private var TAG_WIDGET = "com.yeputra.moviecatalogue.services.updatecontentwidget"

    private var mDispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))

    fun startDailyRemainder() {
        val myJob = mDispatcher.newJobBuilder()
                .setService(JobDailyRemainder::class.java)
                .setTag(TAG_DAILY)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(1, 1))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
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
                        Constraint.ON_ANY_NETWORK
                )
                .build()

        mDispatcher.mustSchedule(myJob)
    }

    fun stopReleaseRemainder() {
        mDispatcher.cancel(TAG_RELEASE)
    }

    fun startUpdateWidgetContent() {
        /*val mServiceComponent = ComponentName(context, JobUpdateWidget::class.java)
        val builder = JobInfo.Builder(TAG_WIDGET, mServiceComponent)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setMinimumLatency(86000)
        } else {
            builder.setPeriodic(2)
        }
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler?
        jobScheduler?.schedule(builder.build())*/

        val myJob = mDispatcher.newJobBuilder()
                .setService(JobUpdateWidget::class.java)
                .setTag(TAG_WIDGET)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(0, 10))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(
                        Constraint.ON_UNMETERED_NETWORK,
                        Constraint.ON_ANY_NETWORK
                )
                .build()

        mDispatcher.mustSchedule(myJob)
    }

    fun stopUpdateWidgetContent() {
        mDispatcher.cancel(TAG_WIDGET)
        /*val tm = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler?
        tm?.cancel(TAG_WIDGET)*/
    }
}