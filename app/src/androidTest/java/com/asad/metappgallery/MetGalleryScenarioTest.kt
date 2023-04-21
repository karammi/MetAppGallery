package com.asad.metappgallery

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.asad.metappgallery.app.MainActivity
import com.asad.metappgallery.searchScreen.presentation.util.UiConstant
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MetGalleryScenarioTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            composeTestRule.mainClock.autoAdvance = false
            FakeMetGalleryFinderApp()
        }
    }

    @Test
    fun whenAppIsLaunched_thenSearchDetailScreenIsDisplayed() {
        composeTestRule.apply {
            onNodeWithContentDescription(UiConstant.GalleryEmptyContent)
                .assertIsDisplayed()

            onNodeWithTag(UiConstant.SearchGalleryTextField)
                .assertIsDisplayed()

            onNodeWithTag(UiConstant.SearchGalleryTextField)
                .performClick()

            composeTestRule
                .mainClock
                .advanceTimeBy(1000)

            composeTestRule
                .onNodeWithTag(UiConstant.SearchGalleryTextField)
                .assertIsDisplayed()

            onNodeWithTag(UiConstant.SearchGalleryTextField)
                .performTextInput("s")

            onNodeWithTag(UiConstant.SearchGalleryTextField)
                .performTextInput("u")

            onNodeWithTag(UiConstant.SearchGalleryTextField)
                .performTextInput("n")
        }
    }
}
