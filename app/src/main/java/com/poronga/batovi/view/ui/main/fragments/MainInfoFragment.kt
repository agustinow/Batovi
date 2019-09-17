package com.poronga.batovi.view.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.poronga.batovi.R


import kotlinx.android.synthetic.main.fragment_main_info.*

class MainInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        poronga.text = "hola"
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainInfoFragment()
    }
}
