package com.asad.metappgallery

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.asad.metappgallery.app.MainActivity
import com.asad.metappgallery.app.MetGalleryFinderApp
import com.asad.metappgallery.core.CoreString
import com.asad.metappgallery.detailScreen.presentation.util.ObjectDetailConstants
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
            composeTestRule.mainClock.autoAdvance = true
            MetGalleryFinderApp()
        }
    }

    @Test
    fun whenASearchedGalleryItemClicked_shouldNavigateToDetailObjectInfoScreen() {
        composeTestRule.apply {
            onNodeWithContentDescription(UiConstant.GalleryEmptyContent)
                .assertIsDisplayed()

            onNodeWithContentDescription(UiConstant.SearchGalleryTextField)
                .assertIsDisplayed()

            onNodeWithContentDescription(UiConstant.SearchGalleryTextField)
                .performClick()

            onNodeWithContentDescription(UiConstant.SearchGalleryTextField)
                .assertIsDisplayed()

            onNodeWithContentDescription(UiConstant.SearchGalleryTextField)
                .performTextInput(UiConstant.QuerySearchText)

            waitUntil {
                composeTestRule
                    .onAllNodesWithContentDescription(UiConstant.GalleryGridContentDescription)
                    .fetchSemanticsNodes().size == 1
            }

            onNodeWithText("1")
                .assertIsDisplayed()

            onNodeWithText("2")
                .assertIsDisplayed()

            waitUntil {
                onAllNodesWithContentDescription(UiConstant.GalleryCardContentDescription)
                    .fetchSemanticsNodes().size == 2
            }

            onNodeWithContentDescription(UiConstant.ItemFirstContentDescription)
                .assertIsDisplayed()

            onNodeWithContentDescription(UiConstant.ItemSecondContentDescription)
                .assertIsDisplayed()

            onNodeWithText("1")
                .performClick()

            onAllNodesWithContentDescription(
                ObjectDetailConstants.ObjectContentDescriptionTitle,
            )

            onAllNodesWithContentDescription(CoreString.CustomNetworkImageLoadingTitle)
                .fetchSemanticsNodes()
                .isNotEmpty()

            onNodeWithContentDescription(ObjectDetailConstants.ArtistContentDescriptionTitle)
                .assertIsDisplayed()

            onNodeWithContentDescription(ObjectDetailConstants.DepartmentContentDescriptionTitle)
                .assertIsDisplayed()

            onNodeWithContentDescription(ObjectDetailConstants.ClassificationContentDescriptionTitle)
                .assertIsDisplayed()
        }
    }
}
