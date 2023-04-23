package com.asad.metappgallery.detailScreen.presentation.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performScrollToNode
import androidx.lifecycle.SavedStateHandle
import com.asad.metappgallery.core.CoreString
import com.asad.metappgallery.detailScreen.data.dataSource.FakeObjectDetailRemoteDataSourceImpl
import com.asad.metappgallery.detailScreen.data.dataSource.remote.ObjectDetailRemoteDataSource
import com.asad.metappgallery.detailScreen.presentation.util.ObjectDetailConstants
import com.asad.metappgallery.detailScreen.presentation.viewModel.ObjectDetailViewModel
import com.asad.metappgallery.searchScreen.presentation.util.UiConstant
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ObjectDetailConstantsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var objectDetailRemoteDataSource: ObjectDetailRemoteDataSource
    lateinit var detailViewModel: ObjectDetailViewModel

    @Before
    fun setup() {
        composeTestRule.setContent {
            composeTestRule.mainClock.autoAdvance = false

            objectDetailRemoteDataSource = FakeObjectDetailRemoteDataSourceImpl()
            detailViewModel =
                ObjectDetailViewModel(
                    objectDetailRemoteDataSource = objectDetailRemoteDataSource,
                    savedStateHandle = SavedStateHandle(),
                )
            val fakeCurrentObjectId = "1"

            ObjectDetailScreen(
                viewModel = detailViewModel,
                currentObjectId = fakeCurrentObjectId,
                onNavigationBack = {},
            )
        }
    }

    @Test
    fun whenInitialState_thenUpdateUiStateToEmpty() {
        composeTestRule.apply {
            onNodeWithContentDescription(UiConstant.GalleryEmptyContent)
                .assertIsDisplayed()
        }
    }

    @Test
    fun whenFetchedObjectDetail_thenUpdateObjectInfoOnScreen() {
        composeTestRule
            .onNodeWithContentDescription(UiConstant.GalleryEmptyContent)
            .assertIsDisplayed()

        composeTestRule.mainClock.advanceTimeByFrame()

        composeTestRule
            .onAllNodesWithContentDescription(
                ObjectDetailConstants.ObjectContentDescriptionTitle,
            )

        composeTestRule
            .onAllNodesWithContentDescription(CoreString.CustomNetworkImageLoadingTitle)
            .fetchSemanticsNodes()
            .isNotEmpty()

        composeTestRule
            .onNodeWithContentDescription(ObjectDetailConstants.ArtistContentDescriptionTitle)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(ObjectDetailConstants.DepartmentContentDescriptionTitle)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(ObjectDetailConstants.ClassificationContentDescriptionTitle)
            .assertIsDisplayed()
    }

    @Test
    fun onObjectDetailScreen_thenScrollToBottom() {
        composeTestRule
            .onNodeWithContentDescription(UiConstant.GalleryEmptyContent)
            .assertIsDisplayed()

        composeTestRule.mainClock.advanceTimeByFrame()

        composeTestRule
            .onNodeWithContentDescription(ObjectDetailConstants.ColumnContentDescription)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(ObjectDetailConstants.ColumnContentDescription)
            .performScrollToNode(hasContentDescription("key"))

        composeTestRule.mainClock.advanceTimeBy(1000)
    }
}
