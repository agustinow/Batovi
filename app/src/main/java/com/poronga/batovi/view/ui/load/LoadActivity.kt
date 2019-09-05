package com.poronga.batovi.view.ui.load

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.poronga.batovi.R
import com.poronga.batovi.view.ui.main.MainActivity
import com.poronga.batovi.viewmodel.load.LoadViewModel

class LoadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        //LOAD VIEWMODEL
        ViewModelProviders.of(this@LoadActivity)[LoadViewModel::class.java]
    }

    fun next(){
        val intent = Intent(this@LoadActivity, MainActivity::class.java)
        startActivity(intent)
    }

}
