package com.poronga.batovi.view.ui.main

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.poronga.batovi.R
import kotlinx.android.synthetic.main.dialog_difficulty.*

class MainActivity : AppCompatActivity() {
    var pene: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dialog = Dialog(this)
        with(dialog){
            setContentView(R.layout.dialog_difficulty)
            findViewById<ImageButton>(R.id.imgBtnJunior).setOnClickListener{
                this@MainActivity.pene = "boton pija"
                Toast.makeText(this@MainActivity,pene,Toast.LENGTH_SHORT).show()
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnNormal).setOnClickListener{
                this@MainActivity.pene = "boton pija1"
                Toast.makeText(this@MainActivity,pene,Toast.LENGTH_SHORT).show()
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnComplex).setOnClickListener{
                this@MainActivity.pene = "boton pija2"
                Toast.makeText(this@MainActivity,pene,Toast.LENGTH_SHORT).show()
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnProfesional).setOnClickListener{
                this@MainActivity.pene = "boton pija3"
                Toast.makeText(this@MainActivity,pene,Toast.LENGTH_SHORT).show()
                dismiss()
            }

        }


    }
}
