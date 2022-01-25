package com.toastcompat.sample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import io.tarif.toastcompat.ktx.ToastCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.text).setOnClickListener {
            ToastCompat.Builder(this)
                .text("Hello world!")
                .textColor(Color.WHITE)
                .textSize(14f)
                .font(R.font.marko_one)
                .backgroundType(ToastCompat.SOLID)
                .backgroundColor(Color.BLACK)
                .cornerRadius(10)
                .stroke(2, Color.GRAY)
                .padding(5f,15f,16f)
                .iconSize(60)
                .gravity(ToastCompat.GRAVITY_BOTTOM)
                .iconStart(R.drawable.ic_baseline_circle_notifications_24)
                .iconEnd(R.drawable.ic_baseline_circle_notifications_24)
                .show()

        }
    }
}