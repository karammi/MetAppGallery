package com.asad.metappgallery.detailScreen.presentation.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performScrollToNode
import com.asad.metappgallery.core.CoreString
import com.asad.metappgallery.detailScreen.data.dataSource.FakeObjectDetailRemoteDataSourceImpl
import com.asad.metappgallery.detailScreen.data.dataSource.remote.ObjectDetailRemoteDataSource
import com.asad.metappgallery.detailScreen.presentation.util.ObjectDetailScreen
import com.asad.metappgallery.detailScreen.presentation.viewModel.ObjectDetailViewModel
import com.asad.metappgallery.searchScreen.presentation.util.UiConstant
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ObjectDetailScreenTest {

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
                ObjectDetailViewModel(objectDetailRemoteDataSource = objectDetailRemoteDataSource)
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
                ObjectDetailScreen.ObjectContentDescriptionTitle,
            )

        composeTestRule
            .onAllNodesWithContentDescription(CoreString.CustomNetworkImageLoadingTitle)
            .fetchSemanticsNodes()
            .isNotEmpty()

        composeTestRule
            .onNodeWithContentDescription(ObjectDetailScreen.ArtistContentDescriptionTitle)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(ObjectDetailScreen.DepartmentContentDescriptionTitle)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(ObjectDetailScreen.ClassificationContentDescriptionTitle)
            .assertIsDisplayed()
    }

    @Test
    fun onObjectDetailScreen_thenScrollToBottom() {
        composeTestRule
            .onNodeWithContentDescription(UiConstant.GalleryEmptyContent)
            .assertIsDisplayed()

        composeTestRule.mainClock.advanceTimeByFrame()

        composeTestRule
            .onNodeWithContentDescription(ObjectDetailScreen.ColumnContentDescription)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(ObjectDetailScreen.ColumnContentDescription)
            .performScrollToNode(hasContentDescription("key"))

        composeTestRule.mainClock.advanceTimeBy(1000)
    }
}
