package com.example.quickstartlessons.module.base.utils

import com.example.quickstartlessons.module.base.utils.QsConstants.ZERO

fun String?.toDoubleOrZero() = this?.toDoubleOrNull() ?: ZERO.toDouble()