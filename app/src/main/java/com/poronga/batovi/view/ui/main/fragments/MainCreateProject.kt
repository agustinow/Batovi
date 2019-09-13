package com.poronga.batovi.view.ui.main.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import android.content.ContentValues.TAG
import android.content.res.Resources
import android.widget.Toolbar
import androidx.core.view.*
import androidx.fragment.app.FragmentManager
import com.google.android.material.chip.Chip
import com.poronga.batovi.R
import kotlinx.android.synthetic.main.fragment_main_create_project.*
import kotlinx.android.synthetic.main.fragment_main_create_project.view.*


class MainCreateProject : DialogFragment() {

    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_create_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener{
            dismiss()
        }
        chipgroup.setChipSpacingVerticalResource(R.dimen.chip_spacing)
        toolbar.title = "New Project"
        toolbar.setTitleTextColor(resources.getColor(R.color.colorBackground, resources.newTheme()))
        btnAddTag.setOnClickListener {
            if(!txtProjectTags.text.isNullOrEmpty()){
                val chip = Chip(context)
                chip.setPadding(8)
                chip.text=txtProjectTags.text
                chip.setCloseIconResource(R.drawable.ic_close24dp)
                chip.isCloseIconVisible=true
                chipgroup.addView(chip)
                txtProjectTags.text!!.clear()
                chip.setOnClickListener {
                    chipgroup.removeView(chip)
                }
            }else{
                txtProjectTags.error = "Tag is empty!"
            }

        }



    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog!=null){
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height= ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width,height)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.AppTheme)
    }



    companion object {
        @JvmStatic
        fun newInstance() = MainCreateProject()
        fun display(fragmentManager: FragmentManager): MainCreateProject {
            val exampleDialog = MainCreateProject()
            exampleDialog.show(fragmentManager, TAG)
            return exampleDialog
        }
    }
}
