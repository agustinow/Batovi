package com.poronga.batovi.view.ui.main.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageButton
import androidx.core.view.*
import androidx.fragment.app.FragmentManager
import com.google.android.material.chip.Chip
import com.poronga.batovi.*
import com.poronga.batovi.view.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_main_create_project.*
import java.util.*
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_main.*




class MainCreateProject : DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        //dostuff
    }

    var chosenDifficulty: Int = 0
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
        txtProjectDescriptionLayout.requestFocus()
        //txtProjectNameLayout.requestFocus()
        chipgroupTag.setChipSpacingVerticalResource(R.dimen.chip_spacing)
        chipgroupLanguages.setChipSpacingVerticalResource(R.dimen.chip_spacing)
        toolbar.title = "New Project"
        toolbar.setTitleTextColor(resources.getColor(R.color.colorBackground, resources.newTheme()))
        btnAddTag.setOnClickListener {
            if (!txtProjectTags.text.isNullOrEmpty()) {
                val chip = Chip(context)
                chip.setPadding(8)
                chip.text = txtProjectTags.text
                chip.setCloseIconResource(R.drawable.ic_close24dp)
                chip.isCloseIconVisible = true
                chipgroupTag.addView(chip)
                txtProjectTags.text!!.clear()
                chip.setOnCloseIconClickListener {
                    chipgroupTag.removeView(chip)
                }
            } else {
                txtProjectTags.error = "Tag is empty!"
            }
        }
        btnAddLanguages.setOnClickListener {
            if(!txtProjectLanguages.text.isNullOrEmpty()){
                val chip = Chip(context)
                chip.setPadding(8)
                chip.text=txtProjectLanguages.text
                chip.setCloseIconResource(R.drawable.ic_close24dp)
                chip.isCloseIconVisible=true
                chipgroupLanguages.addView(chip)
                txtProjectLanguages.text!!.clear()
                chip.setOnCloseIconClickListener{
                chipgroupLanguages.removeView(chip)
                }
            }else{
                txtProjectLanguages.error= "Language is empty!"
            }
        }
        btnSelectDifficulty.setOnClickListener {
            askDifficulty()
        }
        btnAddTime.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                this@MainCreateProject,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            )
            dpd.show(fragmentManager!!,"Datepickerdialog")
        }

        btnCreateProject.setOnClickListener {
            txtProjectNameLayout.error = if(txtProjectName.text.isNullOrEmpty()){
                 "Project Name is empty!"
            } else null
            txtProjectDescription.error = if(txtProjectDescription.text.isNullOrEmpty()){
                "Project Description is empty!"
            } else null
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

    fun askDifficulty(){
        val dialog = Dialog(context!!)
        with(dialog) {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.dialog_difficulty)
            findViewById<ImageButton>(R.id.imgBtnJunior).setOnClickListener {
                this@MainCreateProject.btnSelectDifficulty.icon = resources.getDrawable(R.drawable.ic_coffe_cup2, resources.newTheme())
                this@MainCreateProject.btnSelectDifficulty.text="Junior"
                chosenDifficulty = DIFF_JUNIOR
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnNormal).setOnClickListener {
                this@MainCreateProject.btnSelectDifficulty.icon = resources.getDrawable(R.drawable.ic_coffe_cup1, resources.newTheme())
                this@MainCreateProject.btnSelectDifficulty.text="Normal"
                chosenDifficulty = DIFF_NORMAL
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnComplex).setOnClickListener {
                this@MainCreateProject.btnSelectDifficulty.icon = resources.getDrawable(R.drawable.ic_coffe_ma3, resources.newTheme())
                this@MainCreateProject.btnSelectDifficulty.text=" Complex"
                chosenDifficulty = DIFF_COMPLEX
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnProfesional).setOnClickListener {
                this@MainCreateProject.btnSelectDifficulty.icon = resources.getDrawable(R.drawable.ic_coffe_ma1, resources.newTheme())
                this@MainCreateProject.btnSelectDifficulty.text="Professional"
                chosenDifficulty = DIFF_PRO
                dismiss()
            }
            show()
        }
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
