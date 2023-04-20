package com.asad.metappgallery.detailScreen.presentation.screen

import androidx.compose.ui.test.junit4.createComposeRule
import com.asad.metappgallery.detailScreen.data.dataSource.FakeObjectDetailRemoteDataSourceImpl
import com.asad.metappgallery.detailScreen.data.dataSource.ObjectDetailRemoteDataSource
import com.asad.metappgallery.detailScreen.presentation.viewModel.ObjectDetailViewModel
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
            val fakeCurrentObjectId = "2032"

            ObjectDetailScreen(
                viewModel = detailViewModel,
                currentObjectId = fakeCurrentObjectId,
                onNavigationBack = {},
            )
        }
    }

    @Test
    fun detailScreenInitialState_shouldBeEmptyState() {
        composeTestRule.apply {
//            onNodeWithText()
        }
    }
}
