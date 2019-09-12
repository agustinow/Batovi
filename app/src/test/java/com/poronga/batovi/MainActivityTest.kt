package com.poronga.batovi

import android.content.Intent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.contains
import androidx.core.view.get
import com.google.common.truth.Truth.assertThat
import com.poronga.batovi.model.json.User
import com.poronga.batovi.view.ui.load.LoadActivity
import com.poronga.batovi.view.ui.main.MainActivity
import com.poronga.batovi.view.ui.main.fragments.MainHomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_top_menu.view.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23], application = App::class)
class MainActivityTest {
    lateinit var subject: MainActivity

    @Before
    fun setup(){
        App.currentUser = User(
            id = 1,
            name = "Testman",
            lvl = 4,
            xp = 47,
            image = "",
            achievements = mutableListOf(1, 4, 7)
        )
        subject = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()
        //App.injector =

    }

    @Test
    fun testApplication() {
        assertThat(subject.application).isInstanceOf(App::class.java)
    }

    @Test
    fun testDrawerHeader() {
        assertThat(subject.drawer.header!!.txt_user_name.text).isEqualTo("Testman")
    }
    @Test
    fun testSelectedFrag() {
        assertThat(subject.model.selectedFrag).isEqualTo(1)
        assertThat(subject.ui_content[0].findViewById<ConstraintLayout>(R.id.fragment_main_home_layout)).isNotNull()
    }



}