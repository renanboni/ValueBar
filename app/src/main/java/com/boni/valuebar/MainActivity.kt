package com.boni.valuebar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.boni.widget.ValueBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ValueBar.ValueBarListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        valuebar.valueBarListener = this
    }

    override fun onValueChanged(value: Int) {
        valuebarText.text = value.toString()
    }
}
