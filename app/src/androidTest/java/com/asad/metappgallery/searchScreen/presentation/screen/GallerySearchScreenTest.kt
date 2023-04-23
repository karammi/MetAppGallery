package com.asad.metappgallery.searchScreen.presentation.screen

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.asad.metappgallery.app.theme.lightThemeColors
import com.asad.metappgallery.searchScreen.data.dataSource.FakeGalleryRemoteDataSourceImpl
import com.asad.metappgallery.searchScreen.data.dataSource.GalleryRemoteDataSource
import com.asad.metappgallery.searchScreen.presentation.util.UiConstant
import com.asad.metappgallery.searchScreen.presentation.viewModel.GallerySearchViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val TAG = "GallerySearchScreenTest"

class GallerySearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var galleryRemoteDataSource: GalleryRemoteDataSource

    private lateinit var gallerySearchViewModel: GallerySearchViewModel

    @Before
    fun setup() {
        composeTestRule.setContent {
            MaterialTheme(colors = lightThemeColors) {
                galleryRemoteDataSource = FakeGalleryRemoteDataSourceImpl()

                gallerySearchViewModel = GallerySearchViewModel(galleryRemoteDataSource)

                GallerySearchScreen(
                    viewModel = gallerySearchViewModel,
                    onNavigationToObjectDetail = {},
                )
            }
        }
    }

    @Test
    fun gallerySearchScreenInitialState_shouldBeEmptyState() {
        composeTestRule.apply {
            onNodeWithContentDescription(UiConstant.GalleryEmptyContent)
                .assertIsDisplayed()

            onNodeWithText(UiConstant.GallerySearchEmptyStateTitle)
                .assertIsDisplayed()

            onNodeWithContentDescription(UiConstant.QuerySearchIcon)
                .assertIsDisplayed()

            onNodeWithText(UiConstant.QuerySearchTextFieldValue)
                .assertIsDisplayed()
        }
    }

    @Test
    fun gallerySearchScreenTextFieldInput_shouldBeFocusedToSearch() {
        composeTestRule.apply {
            onNodeWithText(UiConstant.QuerySearchTextFieldValue)
                .assertIsDisplayed()

            onNodeWithText(UiConstant.QuerySearchTextFieldValue)
                .performClick()

            onNodeWithText(UiConstant.QuerySearchTextFieldValue)
                .assertIsFocused()
        }
    }

    @Test
    fun whenSearchGalleryKeyword_shouldDisplayGallerySearchResponse() {
        composeTestRule
            .onNodeWithContentDescription(UiConstant.SearchGalleryTextField)
            .assertHasClickAction()

        composeTestRule
            .onNodeWithContentDescription(UiConstant.SearchGalleryTextField)
            .performClick()

        composeTestRule
            .onNode(hasContentDescription(UiConstant.GalleryEmptyContent))
            .assertIsDisplayed()

        composeTestRule.mainClock.advanceTimeByFrame()

        composeTestRule
            .onNodeWithContentDescription(UiConstant.SearchGalleryTextField)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(UiConstant.SearchGalleryTextField)
            .performTextInput(UiConstant.QuerySearchText)

        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithContentDescription(UiConstant.GalleryCardContentDescription)
                .fetchSemanticsNodes().size == 2
        }

        composeTestRule
            .onNodeWithContentDescription(UiConstant.ItemFirstContentDescription)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(UiConstant.ItemFirstContentDescription)
            .assertHasClickAction()

        composeTestRule
            .onNodeWithContentDescription(UiConstant.ItemSecondContentDescription)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(UiConstant.ItemSecondContentDescription)
            .assertHasClickAction()

        composeTestRule
            .onNodeWithContentDescription(UiConstant.ItemFirstContentDescription)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(UiConstant.ItemSecondContentDescription)
            .assertIsDisplayed()
    }
}
