package com.shine.indoormap.presenter

enum class Action(var type: Int) {
    DISMISSDIALOG(1),
    SHOWERRORMESSAGE(2),
    NETWORKANOMALY(3),
    LOADDATA(4)
}