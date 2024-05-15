package com.example.quickstartlessons.module.base.model

import com.example.quickstartlessons.R

enum class LocaleEnum(val languageCode:String,val languageResId:Int ) {
    ENGLISH("en", R.string.english),
    ARMENIAN("hy",R.string.armenian),
    RUSSIAN("ru",R.string.russian)
}