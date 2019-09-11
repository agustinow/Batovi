package com.poronga.batovi

import android.content.Intent
import com.google.common.truth.Truth.assertThat
import com.poronga.batovi.view.ui.load.LoadActivity
import com.poronga.batovi.view.ui.main.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23])
class LoadActivityTest {
    lateinit var subject: LoadActivity

    @Before
    fun setup(){
        subject = Robolectric.buildActivity(LoadActivity::class.java)
            .create()
            .resume()
            .get()
    }

    @Test
    fun testNext() {
        subject.next()
        val expectedIntent = Intent(subject, MainActivity::class.java)
        val actual = Shadows.shadowOf(subject).nextStartedActivity
        assertThat(expectedIntent.component).isEqualTo(actual.component)
    }

    fun testFragmentSwap() {

    }

    fun testUserCreate() {

    }

    fun testGetUser() {

    }
}