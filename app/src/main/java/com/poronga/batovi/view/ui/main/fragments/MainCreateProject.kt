package com.poronga.batovi.view.ui.main.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageButton
import androidx.core.view.*
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.chip.Chip
import com.poronga.batovi.*
import com.poronga.batovi.model.json.Project
import com.poronga.batovi.services.UserManager
import com.poronga.batovi.view.ui.main.MainActivity
import com.poronga.batovi.viewmodel.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_main_create_project.*
import java.util.*
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.text.SimpleDateFormat
import javax.inject.Inject


class MainCreateProject(var actualProject: Project?) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    @Inject
    lateinit var userManager: UserManager
    lateinit var model: MainViewModel
    var isUpdating = false

    val tagsList = mutableListOf<String?>()
    val languageList= mutableListOf<String?>()


    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        txtProjectSelecteDate.text= "$monthOfYear/$dayOfMonth/$year"

    }

    var chosenDifficulty: Int = 0

    override fun getTheme(): Int {
        return R.style.AppThemeMaterialCustomAnimation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_create_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.injector.inject(this@MainCreateProject)
        if(actualProject != null){
            isUpdating = true
            fillForm()
        } else {

        }

            model = ViewModelProviders.of(this)[MainViewModel::class.java]
            toolbar.setNavigationOnClickListener {
                dismiss()
            }
            txtProjectNameLayout.requestFocus()
            chipgroupTag.setChipSpacingVerticalResource(R.dimen.chip_spacing)
            chipgroupLanguages.setChipSpacingVerticalResource(R.dimen.chip_spacing)
            btnAddTag.setOnClickListener {
                if (!txtTaskName.text.isNullOrEmpty()) {
                    val chip = Chip(context)
                    chip.setPadding(8)
                    chip.text = txtTaskName.text
                    chip.setCloseIconResource(R.drawable.ic_close24dp)
                    chip.isCloseIconVisible = true
                    chipgroupTag.addView(chip)
                    txtTaskName.text!!.clear()
                    chip.setOnCloseIconClickListener {
                        chipgroupTag.removeView(chip)
                    }
                } else {
                    txtTaskName.error = "Tag is empty!"
                }
            }
            btnAddLanguages.setOnClickListener {
                if (!txtProjectLanguages.text.isNullOrEmpty()) {
                    val chip = Chip(context)
                    chip.setPadding(8)
                    chip.text = txtProjectLanguages.text
                    chip.setCloseIconResource(R.drawable.ic_close24dp)
                    chip.isCloseIconVisible = true
                    chipgroupLanguages.addView(chip)
                    txtProjectLanguages.text!!.clear()
                    chip.setOnCloseIconClickListener {
                        chipgroupLanguages.removeView(chip)
                        languageList.remove(txtProjectLanguages.text.toString())
                    }
                } else {
                    txtProjectLanguages.error = "Language is empty!"
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
                dpd.show(fragmentManager!!, "Datepickerdialog")
                now.get(Calendar.DATE)
            }

            imgCleanForm.setOnClickListener {
                clearForm()
            }

            txtProjectName.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    var nameExists = false
                    App.projects.forEach {
                        if (it.name == txtProjectName.text.toString()) {
                            nameExists = true
                        }
                    }
                    if (nameExists) txtProjectNameLayout.error =
                        "There is already a project with that name!"
                    else txtProjectNameLayout.error = null

                }

            })

            btnCreateProject.setOnClickListener {
                var isSuccessful = true
                txtProjectNameLayout.error = if (txtProjectName.text.isNullOrEmpty()) {
                    isSuccessful = false
                    "Project Name is empty!"
                } else null
                txtProjectDescriptionLayout.error =
                    if (txtProjectDescription.text.isNullOrEmpty()) {
                        isSuccessful = false
                        "Project Description is empty!"
                    } else null
                if (chosenDifficulty == 0) {
                    isSuccessful = false
                }
                if (txtProjectSelecteDate.text.isNullOrEmpty()) {
                    isSuccessful = false

                }
                chipgroupTag.forEach {
                    tagsList.add((it as Chip).text.toString())
                }
                chipgroupLanguages.forEach {
                    languageList.add((it as Chip).text.toString())
                }
                if (isSuccessful) {
                    if (!isUpdating) {
                        newProject()
                    } else {
                        updateProject()
                    }
                    dismiss()
                }

            }

    }

    fun fillForm(){
        txtProjectName.setText(actualProject!!.name)
        txtProjectDescription.setText(actualProject!!.description)
        txtProjectSelecteDate.setText(actualProject!!.dateFinish.toString())

        actualProject!!.tags.forEach {
            val chip = Chip(context)
            chip.setPadding(8)
            chip.setCloseIconResource(R.drawable.ic_close24dp)
            chip.isCloseIconVisible = true
            chip.text=it
            chipgroupTag.addView(chip)
        }
        actualProject!!.languages.forEach {
            val chip = Chip(context)
            chip.setPadding(8)
            chip.setCloseIconResource(R.drawable.ic_close24dp)
            chip.isCloseIconVisible = true
            chip.text=it
            chipgroupTag.addView(chip)
        }

        chosenDifficulty=actualProject!!.difficulty!!
        //imagen dificultad xdxdx
    }

    fun clearForm(){
        txtProjectName.text!!.clear()
        txtProjectDescription.text!!.clear()
        txtProjectSelecteDate.text=""
        txtTaskName.text!!.clear()
        txtProjectLanguages.text!!.clear()
        chipgroupTag.removeAllViews()
        chipgroupLanguages.removeAllViews()
        chosenDifficulty=0

        this@MainCreateProject.btnSelectDifficulty.icon = resources.getDrawable(R.drawable.ic_coffe_cup2, resources.newTheme())
        this@MainCreateProject.btnSelectDifficulty.text="Select Difficulty"
    }

    fun newProject(){
        //VERIFICAR NOMBRE NO REPETIDO CSM
        val actualProject = Project(
            name = txtProjectName.text.toString(),
            description = txtProjectDescription.text.toString(),
            tags =tagsList,
            languages =languageList,
            tasks = mutableListOf(),
            dateCreated = Date(),
            dateFinish = SimpleDateFormat("M/d/yyyy").parse(txtProjectSelecteDate.text.toString()),
            thumbnailURL = null,
            difficulty = chosenDifficulty,
            completed = false)
        userManager.createProject(actualProject)
        userManager.giveXp(5)
        if (App.projects.size==1){
            userManager.giveAchievementent(ACHIV_NEWBORN)
        }
        val uncompletedProjects = App.projects.filter {
            !it.completed
        }.size
        if(uncompletedProjects>=5){
            userManager.giveAchievementent(ACHIV_SUICIDAL)
        }
    }
    fun updateProject(){
        actualProject = Project(
            name = txtProjectName.text.toString(),
            description = txtProjectDescription.text.toString(),
            tags =tagsList,
            languages =languageList,
            tasks = mutableListOf(),
            dateCreated = Date(),
            dateFinish = SimpleDateFormat("M/d/yyyy").parse(txtProjectSelecteDate.text.toString()),
            thumbnailURL = null,
            difficulty = chosenDifficulty,
            completed = false)
        userManager.saveProjects()
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog!=null){
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height= ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width,height)
            activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
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
                this@MainCreateProject.btnSelectDifficulty.text="Complex"
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

    override fun onDismiss(dialog: DialogInterface) {
        with(activity as MainActivity){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        }
        super.onDismiss(dialog)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainCreateProject(null)
        fun display(fragmentManager: FragmentManager): MainCreateProject {
            val exampleDialog = MainCreateProject(null)
            exampleDialog.show(fragmentManager, TAG)
            return exampleDialog
        }
    }
}
