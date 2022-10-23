package com.sercanorhangazi.githubsearch.base

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

open class BaseFragment(): Fragment() {
    fun snackBar(
        message: String,
        duration: Int = Snackbar.LENGTH_LONG,
        view: View = requireView()
    ) {
        Snackbar.make(view, message, duration).show()
    }
}
