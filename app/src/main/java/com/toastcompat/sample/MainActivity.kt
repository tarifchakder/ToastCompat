package com.toastcompat.sample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.toastcompat.sample.databinding.ActivityMainBinding
import io.tarif.toastcompat.ktx.ToastCompat

class MainActivity : AppCompatActivity() {

    private lateinit var bind : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)


        bind.solidBackground.setOnClickListener {
            /*val builder = ToastCompat.Builder(this)
                .backgroundType(ToastCompat.SOLID)
                .text("Solid background with corner in code")
                .textColor(Color.WHITE)
                .cornerRadius(5)
                .textSize(14f)
                .font(R.font.palanquin_dark)
                .length(100)
                .backgroundColor(Color.parseColor("#229954"))
                .build()*/

            val builder = ToastCompat.Builder(this)
                .backgroundType(ToastCompat.SOLID)
                .text("This is ToastCompat !!")
                .textColor(Color.WHITE)
                .cornerRadius(5)
                .textSize(14f)
                .font(R.font.palanquin_dark)
                .length(100)
                .backgroundColor(Color.parseColor("#8E44AD"))
                .build()

            builder.show()
        }

        bind.solidBackground.setOnLongClickListener {
            ToastCompat.makeText(this,"Solid background corner in xml",Toast.LENGTH_LONG,R.style.toastSolid).show()
            true
        }

       /* bind.gradientBackground.setOnClickListener {
            val builder = ToastCompat.Builder(this)
                .backgroundType(ToastCompat.GRADIENT)
                .gradientColor(Color.RED,Color.BLUE)
                .text("Gradient background code")
                .textColor(Color.WHITE)
                .backgroundColor(Color.BLACK)
                .build()

            builder.show()
        }

        bind.gradientBackground.setOnLongClickListener {
            ToastCompat.makeText(this,"Gradient background in xml",Toast.LENGTH_LONG,R.style.toastGradient).show()
            true
        }

        bind.drawableBackground.setOnClickListener {
            val builder = ToastCompat.Builder(this)
                .text("Drawable background in code")
                .setDrawableTint(Color.BLACK)
                .textColor(Color.WHITE)
                .build()

            builder.show()
        }

        bind.drawableBackground.setOnLongClickListener {
            ToastCompat.makeText(this,"Drawable background in xml",Toast.LENGTH_LONG,R.style.toastDrawable).show()
            true
        }

        bind.colorizedText.setOnClickListener {
            val builder = ToastCompat.Builder(this)
                .text("Customize Text in code")
                .backgroundColor(Color.GRAY)
                .textColor(Color.BLACK)
                .textBold()
                .font(R.font.marko_one)
                .build()

            builder.show()

        }

        bind.colorizedText.setOnLongClickListener {
            ToastCompat.makeText(this,"Colorized text xml",R.style.colorizedBoldText).show()
            true
        }

        bind.iconStart.setOnClickListener {
            val builder = ToastCompat.Builder(this)
                .backgroundType(ToastCompat.SOLID)
                .text("Icon Start in code")
                .textColor(Color.WHITE)
                .cornerRadius(10)
                .iconStart(R.drawable.ic_baseline_circle_notifications_24)
                .backgroundColor(Color.BLACK)
                .build()

            builder.show()
        }

        bind.iconStart.setOnLongClickListener {
            ToastCompat.makeText(this,"Icon Start in xml",R.style.iconStart).show()
            true
        }

        bind.iconEnd.setOnClickListener {
            val builder = ToastCompat.Builder(this)
                .backgroundType(ToastCompat.SOLID)
                .text("Icon End in code")
                .textColor(Color.WHITE)
                .cornerRadius(10)
                .iconEnd(R.drawable.ic_baseline_circle_notifications_24)
                .backgroundColor(Color.BLACK)
                .build()

            builder.show()
        }

        bind.iconEnd.setOnLongClickListener {
            ToastCompat.makeText(this,"Icon End xml",R.style.iconEnd).show()
            true
        }

        bind.iconBoth.setOnClickListener {
            val builder  = ToastCompat.Builder(this)
                .backgroundType(ToastCompat.SOLID)
                .text("Icon Both Code")
                .textColor(Color.WHITE)
                .cornerRadius(10)
                .iconEnd(R.drawable.ic_baseline_circle_notifications_24)
                .iconStart(R.drawable.ic_baseline_circle_notifications_24)
                .backgroundColor(Color.BLACK)
                .build()

            builder.show()
        }

        bind.iconBoth.setOnLongClickListener {
            ToastCompat.makeText(this,"Icon both in XML",R.style.iconBoth).show()
            true
        }

        bind.customizeGravity.setOnClickListener {
            val builder = ToastCompat.Builder(this)
                .backgroundType(ToastCompat.SOLID)
                .text("Toast Gravity.TOP xOffset=0 yOffset=100")
                .gravity(ToastCompat.GRAVITY_TOP,0,100)
                .textColor(Color.WHITE)
                .cornerRadius(10)
                .backgroundColor(Color.BLACK)
                .build()

            builder.show()

        }

        bind.customizeGravity.setOnLongClickListener {
            ToastCompat.makeText(this,"Toast Gravity in XML",R.style.gravityStyle).show()
            true
        }

        bind.paddingView.setOnClickListener {
            val builder = ToastCompat.Builder(this)
                .backgroundType(ToastCompat.SOLID)
                .text("Padding View in Code")
                .padding(15f,20f,22f,)
                .textColor(Color.WHITE)
                .cornerRadius(10)
                .backgroundColor(Color.BLACK)
                .build()

            builder.show()
        }

        bind.stroke.setOnClickListener {
            val builder = ToastCompat.Builder(this)
                .backgroundType(ToastCompat.SOLID)
                .text("Stroke width and color in code")
                .textColor(Color.WHITE)
                .stroke(2,Color.GREEN)
                .cornerRadius(10)
                .backgroundColor(Color.BLACK)
                .build()

            builder.show()
        }

        bind.stroke.setOnLongClickListener {
            ToastCompat.makeText(this,"Stroke width and color XML",R.style.strokeStyle).show()
            true
        }

        bind.allStyle.setOnLongClickListener {
            ToastCompat.makeText(this,"All style in XML",R.style.allStyle).show()
            true
        }

        bind.allStyle.setOnClickListener {
            val builder = ToastCompat.Builder(this)
                .backgroundType(ToastCompat.SOLID)
                .backgroundColor(Color.BLACK)
                .text("All style in Code ")
                .textBold()
                .textColor(Color.WHITE)
                .textSize(14f)
                .textBold()
                .font(R.font.marko_one)
                .stroke(1,Color.GREEN)
                .iconEnd(R.drawable.ic_baseline_circle_notifications_24)
                .iconStart(R.drawable.ic_baseline_circle_notifications_24)
                .iconSize(33)
                .gravity(ToastCompat.GRAVITY_CENTER)
                .padding(25f,30f,30f)
                .build()

            builder.show()
        }*/

    }
}