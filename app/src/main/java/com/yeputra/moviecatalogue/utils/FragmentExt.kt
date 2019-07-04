package com.yeputra.moviecatalogue.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


fun FragmentActivity.fragmentReplace(container: Int, fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(container, fragment, fragment::class.java.simpleName)
        .addToBackStack(null)
        .commit()
}

fun FragmentActivity.fragmentAdd(container: Int, fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .add(container, fragment, fragment::class.java.simpleName)
        .hide(fragment)
        .commit()
}

fun FragmentActivity.fragmentShow(fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .show(fragment)
        .commit()
}

fun FragmentActivity.fragmentHide(fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .hide(fragment)
        .commit()
}