package com.yeputra.moviecatalogue.view.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.base.BaseToolbarActivity
import com.yeputra.moviecatalogue.job.JobFactory
import com.yeputra.moviecatalogue.repository.preference.SettingPref
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.settings_activity.*
import java.util.*

class SettingsActivity : BaseToolbarActivity<MovieViewModel>() {

    private lateinit var setting: SettingPref
    private lateinit var remainderService: JobFactory

    override fun setToolbar(): Toolbar = toolbar

    override fun setButtonBack(): Boolean = true

    override fun initViewModel(): MovieViewModel = ViewModelProviders
            .of(this)
            .get(MovieViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        setupData()
        setupAppearance()
    }

    private fun setupData() {
        setting = SettingPref(this)
        remainderService = JobFactory(this)

        toolbar_title.text = getString(R.string.menu_setting)
        sw_daily_remainder.isChecked = setting.dailyRemainder
        sw_release_remainder.isChecked = setting.releaseRemainder
        tv_desc_language.text = Locale.getDefault().displayCountry
    }

    private fun setupAppearance() {
        layout_daily_remainder.setOnClickListener {
            sw_daily_remainder.isChecked = !sw_daily_remainder.isChecked
        }

        layout_release_remainder.setOnClickListener {
            sw_release_remainder.isChecked = !sw_release_remainder.isChecked
        }

        sw_release_remainder.setOnCheckedChangeListener { _, isChecked ->
            setting.releaseRemainder = isChecked

            if (isChecked)
                remainderService.startReleaseRemainder()
            else
                remainderService.stopReleaseRemainder()
        }

        sw_daily_remainder.setOnCheckedChangeListener { _, isChecked ->
            setting.dailyRemainder = isChecked

            if (isChecked)
                remainderService.startDailyRemainder()
            else
                remainderService.stopDailyRemainder()
        }

        layout_language.setOnClickListener {
            startActivityForResult(Intent(Settings.ACTION_LOCALE_SETTINGS), Constans.CHANGE_LOCAL)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constans.CHANGE_LOCAL) {
            setResult(requestCode)
            finish()
        }
    }
}