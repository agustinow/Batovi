package com.poronga.batovi.view.ui.main

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import android.widget.ImageButton
import android.widget.Toast
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.poronga.batovi.App
import com.poronga.batovi.PREF_NAME
import com.poronga.batovi.PREF_USER
import com.poronga.batovi.R
import com.poronga.batovi.model.json.User
import com.poronga.batovi.viewmodel.main.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var model: MainViewModel
    @Inject
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //LOAD VIEW MODEL
        model = ViewModelProviders.of(this@MainActivity)[MainViewModel::class.java]
        //INJECT
        App.injector.inject(this@MainActivity)
        //CHECK IF USER IS FOUND, IF NOT, ASK.
        if (getUser() == null){
            //Create user
            newUser()
        } else {
            whenUserExists()
        }

        //SET ONCLICK LISTENERS
        btnNewProject.setOnClickListener {
            askDifficulty()
        }
    }

    fun askDifficulty(){
        val dialog = Dialog(this)
        with(dialog) {
            setContentView(R.layout.dialog_difficulty)
            findViewById<ImageButton>(R.id.imgBtnJunior).setOnClickListener {
                newProjectDialog(1)
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnNormal).setOnClickListener {
                newProjectDialog(2)
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnComplex).setOnClickListener {
                newProjectDialog(3)
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnProfesional).setOnClickListener {
                newProjectDialog(4)
                dismiss()
            }
            show()
        }
    }

    fun newProjectDialog(difficulty: Int){
        //SHOWS NEW FULLSCREEN DIALOG TO CREATE PROJECT WITH GIVEN DIFFICULTY
        Toast.makeText(this@MainActivity, "TODO (New Project Dialog) diff=$difficulty", Toast.LENGTH_SHORT).show()
    }

    fun newUser(){
        MaterialDialog(this@MainActivity, MaterialDialog.DEFAULT_BEHAVIOR).show{
            input(hint = "What's your name?", maxLength = 20, allowEmpty = false) { dialog, text ->
                createUser(text.toString())
                whenUserExists()
                dialog.dismiss()
            }
        }
    }

    fun createUser(name: String){
        val user = User(id = 1, name = name)
        val userJson = gson.toJson(user)
        getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(PREF_USER, userJson)
            .apply()
    }

    fun getUser(): User?{
        val userJson = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(PREF_USER, null)
        if(userJson.isNullOrEmpty()) return null
        return gson.fromJson<User>(userJson, User::class.java)
    }

    fun whenUserExists(){
        Toast.makeText(this@MainActivity, App.currentUser.name, Toast.LENGTH_SHORT).show()
        App.currentUser = getUser()!!
    }
}
