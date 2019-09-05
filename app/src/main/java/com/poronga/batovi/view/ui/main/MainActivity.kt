package com.poronga.batovi.view.ui.main

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.MaterialDialog
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.poronga.batovi.R
import com.poronga.batovi.model.json.Project
import com.poronga.batovi.model.json.Task
import com.poronga.batovi.viewmodel.main.MainViewModel
import java.sql.Date
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    lateinit var model: MainViewModel

    var pene: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //LOAD VIEWMODEL
        model = ViewModelProviders.of(this@MainActivity)[MainViewModel::class.java]

        val dialog = Dialog(this)
        with(dialog){
            setContentView(R.layout.dialog_difficulty)
            findViewById<ImageButton>(R.id.imgBtnJunior).setOnClickListener{
                this@MainActivity.pene = "boton pija"
                Toast.makeText(this@MainActivity, pene, Toast.LENGTH_SHORT).show()
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnNormal).setOnClickListener{
                this@MainActivity.pene = "boton pija1"
                Toast.makeText(this@MainActivity, pene, Toast.LENGTH_SHORT).show()
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnComplex).setOnClickListener{
                this@MainActivity.pene = "boton pija2"
                Toast.makeText(this@MainActivity, pene, Toast.LENGTH_SHORT).show()
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnProfesional).setOnClickListener{
                this@MainActivity.pene = "boton pija3"
                Toast.makeText(this@MainActivity, pene, Toast.LENGTH_SHORT).show()
                dismiss()
            }
            show()
        }


    }
}
