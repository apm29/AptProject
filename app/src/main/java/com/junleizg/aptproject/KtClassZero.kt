package com.junleizg.aptproject

import android.widget.TextView
import com.junleizg.annotations.CheckMethod
import com.junleizg.annotations.EnableField

class KtClassZero {
    @EnableField(value = "sadKt", group = 1)
    var mView1: TextView?=null

    @EnableField(value = "happyKt", group = 1)
    var mView2: TextView?=null

    @EnableField(value = "sorrowKt", group = 2)
    var mView3: TextView?=null


    @CheckMethod(name = "checker3")
    fun checker3(textView: TextView?, fieldName: String): Boolean {
        return false
    }

    @CheckMethod(name = "checker4")
    fun checker4(textView: TextView, fieldName: String): Boolean {
        return false
    }

}