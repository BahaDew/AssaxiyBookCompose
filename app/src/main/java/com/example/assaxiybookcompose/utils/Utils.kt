package com.example.assaxiybookcompose.utils

import timber.log.Timber

fun bahaLogger(message: String, tag: String = "BAHA") {
    Timber.tag(tag).d(message)
}