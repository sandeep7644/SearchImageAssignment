package com.example.assignment.utils

import android.accounts.NetworkErrorException
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.example.assignment.R
import kotlinx.android.synthetic.main.toolbar_home.*
import retrofit2.HttpException


fun Throwable.getExceptionString() = when (this) {
    is NetworkErrorException -> "Network Error Occurred"
    is HttpException -> {
        if (this.code() == 403)
            "Unauthorized Access!! Please Set Client ID in Local.properties"
        else {
            "An Error Occurred"

        }
    }
    else -> "An Error Occurred"


}


fun AppCompatActivity.setToolbar(toolbar: Toolbar, title: String) {
    toolbar.setTitle(title)
    setSupportActionBar(toolbar)
    toolbar.setNavigationOnClickListener {
        onBackPressed()
    }
}