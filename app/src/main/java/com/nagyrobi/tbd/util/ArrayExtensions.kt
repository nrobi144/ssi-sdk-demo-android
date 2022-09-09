package com.nagyrobi.tbd.util

import mobile.StringArray

fun StringArray.toStringList(): List<String> {
    val list = mutableListOf<String>()
    for (i in 0 until size()) {
        list.add(get(i))
    }
    return list
}