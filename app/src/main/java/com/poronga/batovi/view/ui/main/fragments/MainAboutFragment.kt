package com.poronga.batovi.view.ui.main.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.poronga.batovi.R
import kotlinx.android.synthetic.main.fragment_main_about.*

class MainAboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_contact_us.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "agustinodella@gmail.com", null
            ))
            startActivity(Intent.createChooser(intent, "Send email"))
        }
        btn_meet_the_team.setOnClickListener {
            Toast.makeText(context!!, "TODO XD", Toast.LENGTH_SHORT).show()

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainAboutFragment()
    }
}
