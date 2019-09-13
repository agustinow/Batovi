package com.poronga.batovi

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.input.getInputField
import com.google.common.truth.Truth.assertThat
import com.poronga.batovi.model.json.User
import com.poronga.batovi.view.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_difficulty.*
import kotlinx.android.synthetic.main.drawer_top_menu.view.*
import kotlinx.android.synthetic.main.fragment_main_home.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowAlertDialog
import org.robolectric.shadows.ShadowDialog

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

    @Test
    fun testDifficultySelected() {
        subject.btnNewProject.performClick()
        val dialogoso = ShadowAlertDialog.getLatestDialog()
        dialogoso.imgBtnComplex.performClick()
        assertThat(subject.model.newProjectDifficulty).isEqualTo(DIFF_COMPLEX)
    }

    @Test
    fun testResetProgressBar() {
        (App.currentUser as User).name = "Testgirl"
        subject.resetProgressBar()
        assertThat(subject.drawerHeader.txt_user_name.text).isEqualTo("Testgirl")
    }

    @Test
    fun testNewUser() {
        subject.newUser()
        val dialogoso = ShadowDialog.getLatestDialog()
        assertThat(dialogoso).isInstanceOf(MaterialDialog::class.java)
        dialogoso as MaterialDialog
        dialogoso.getInputField().setText("Josue")
        dialogoso.getActionButton(WhichButton.POSITIVE).performClick()
        assertThat((App.currentUser as User).name).isEqualTo("Josue")
    }

    @Test
    fun testRelocoJajajaja() {
        //MEME ME ME ME MMEMEME
    }
}