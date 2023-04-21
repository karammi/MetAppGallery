package com.asad.metappgallery

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.asad.metappgallery.app.MainActivity
import com.asad.metappgallery.core.CoreString
import com.asad.metappgallery.detailScreen.presentation.util.ObjectDetailScreen
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
    fun whenPressedSearchedGalleryItem_shouldNavigateObjectDetailScreen() {
        composeTestRule.apply {
            onNodeWithContentDescription(UiConstant.GalleryEmptyContent)
                .assertIsDisplayed()

            onNodeWithTag(UiConstant.SearchGalleryTextField)
                .assertIsDisplayed()

            onNodeWithTag(UiConstant.SearchGalleryTextField)
                .performClick()

            mainClock
                .advanceTimeBy(1000)

            onNodeWithTag(UiConstant.SearchGalleryTextField)
                .assertIsDisplayed()

            mainClock.advanceTimeByFrame()

            onNodeWithTag(UiConstant.SearchGalleryTextField)
                .performTextInput(UiConstant.QuerySearchText)

            mainClock.advanceTimeBy(5000)

            waitUntil {
                composeTestRule
                    .onAllNodesWithContentDescription(UiConstant.GalleryGridContentDescription)
                    .fetchSemanticsNodes().size == 1
            }

            mainClock.advanceTimeByFrame()

            onNodeWithText("1")
                .assertIsDisplayed()

            mainClock.advanceTimeByFrame()

            onNodeWithText("2")
                .assertIsDisplayed()

            mainClock.advanceTimeByFrame()

            waitUntil {
                onAllNodesWithContentDescription(UiConstant.GalleryCardContentDescription)
                    .fetchSemanticsNodes().size == 2
            }

            mainClock.advanceTimeByFrame()

            onNodeWithContentDescription(UiConstant.ItemFirstContentDescription)
                .assertIsDisplayed()

            mainClock.advanceTimeByFrame()

            onNodeWithContentDescription(UiConstant.ItemSecondContentDescription)
                .assertIsDisplayed()

            mainClock.advanceTimeByFrame()

            onNodeWithText("1")
                .performClick()

            mainClock.advanceTimeBy(2000)

            onAllNodesWithContentDescription(
                ObjectDetailScreen.ObjectContentDescriptionTitle,
            )

            onAllNodesWithContentDescription(CoreString.CustomNetworkImageLoadingTitle)
                .fetchSemanticsNodes()
                .isNotEmpty()

            onNodeWithContentDescription(ObjectDetailScreen.ArtistContentDescriptionTitle)
                .assertIsDisplayed()

            onNodeWithContentDescription(ObjectDetailScreen.DepartmentContentDescriptionTitle)
                .assertIsDisplayed()

            onNodeWithContentDescription(ObjectDetailScreen.ClassificationContentDescriptionTitle)
                .assertIsDisplayed()
        }
    }
}
