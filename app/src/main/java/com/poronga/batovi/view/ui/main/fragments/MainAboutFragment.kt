package com.poronga.batovi.view.ui.main.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import kotlinx.android.synthetic.main.fragment_main_about.*
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.lifecycle.ViewModelProviders
import com.poronga.batovi.R
import com.poronga.batovi.services.CustomAnimationDrawable
import com.poronga.batovi.viewmodel.main.MainViewModel


class MainAboutFragment : Fragment() {
    lateinit var drawable: CustomAnimationDrawable
    lateinit var model: MainViewModel
    lateinit var objAnim: ObjectAnimator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        model = ViewModelProviders.of(activity!!)[MainViewModel::class.java]
        btn_contact_us.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "agustinodella@gmail.com", null
            ))
            startActivity(Intent.createChooser(intent, "Send email"))
        }
        btn_meet_the_team.setOnClickListener {
            Toast.makeText(context!!, "TODO XD", Toast.LENGTH_SHORT).show()

        }

        objAnim = ObjectAnimator.ofPropertyValuesHolder(
            img_about,
            PropertyValuesHolder.ofFloat("scaleX", 1.2f),
            PropertyValuesHolder.ofFloat("scaleY", 1.2f)
        ).apply{
            duration = 500
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
        }

        drawable = CustomAnimationDrawable().apply {
            addFrame(resources.getDrawable(R.drawable.gradient_one, resources.newTheme()), 1000)
            addFrame(resources.getDrawable(R.drawable.gradient_three, resources.newTheme()), 1000)
            addFrame(resources.getDrawable(R.drawable.gradient_two, resources.newTheme()), 1000)
            addFrame(resources.getDrawable(R.drawable.gradient_four, resources.newTheme()), 1000)
            setEnterFadeDuration(1000)
            setExitFadeDuration(1000)
            setDuration(1000)
        }
        fragment_main_about_layout.background = drawable
        drawable.start()
        img_about.setOnClickListener {
            it.scaleX = 1f
            it.scaleY = 1f
            when {
                model.heartbeatSpeedLevel == 0.5f -> model.heartbeatSpeedLevel = 10f
                model.heartbeatSpeedLevel == 2f -> {
                    model.heartbeatSpeedLevel = 0.5f
                    it.scaleX = 2f
                    it.scaleY = 2f
                    Toast.makeText(context, "Imminent heart failure!", Toast.LENGTH_SHORT).show()
                }
                else -> model.heartbeatSpeedLevel -= 2
            }
            setSpeed()
        }
    }

    fun setSpeed(){
        with((model.heartbeatSpeedLevel * 100).toInt()){
            drawable.setDuration(this)
            drawable.setEnterFadeDuration(this)
            drawable.setExitFadeDuration(this)
            objAnim.duration = this.toLong()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = MainAboutFragment()
    }
}
