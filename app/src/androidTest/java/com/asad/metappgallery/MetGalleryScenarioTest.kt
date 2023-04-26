package com.asad.metappgallery

import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.asad.metappgallery.app.MainActivity
import com.asad.metappgallery.app.MetGalleryFinderApp
import com.asad.metappgallery.app.theme.lightThemeColors
import com.asad.metappgallery.core.CoreString
import com.asad.metappgallery.core.MockServerDispatcher
import com.asad.metappgallery.core.di.module.NetworkModule
import com.asad.metappgallery.detailScreen.presentation.util.ObjectDetailConstants
import com.asad.metappgallery.searchScreen.presentation.util.UiConstant
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@UninstallModules(
    value = [NetworkModule::class],
)
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MetGalleryScenarioTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var okHttpClient: OkHttpClient
    var mockWebServer: MockWebServer = MockWebServer()

    private val serviceMap: Map<String, String> = mapOf(
        Pair("search", "gallery_search_success.json"),
        Pair("objects/", "object_detail_success.json"),
    )
    lateinit var idlingResources: OkHttp3IdlingResource

    @Before
    fun setup() {
        hiltRule.inject()
//        mockWebServer = MockWebServer()

        mockWebServer.start(port = 8607)

        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.activity.setContent {
            MaterialTheme(colors = lightThemeColors) {
                MetGalleryFinderApp()
            }
        }

        idlingResources = OkHttp3IdlingResource.create("okhttp", okHttpClient)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun whenASearchedGalleryItemClicked_shouldNavigateToDetailObjectInfoScreen() {
        mockWebServer.dispatcher = MockServerDispatcher().successDispatcher(serviceMap)

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

            mainClock.advanceTimeBy(5000)

            waitUntil(3000) {
                onAllNodesWithContentDescription(UiConstant.SearchGalleryTextField)
                    .assertAny(hasText(UiConstant.QuerySearchText))
                    .fetchSemanticsNodes().size == 1
            }
//            waitUntil {
//                composeTestRule
//                    .onAllNodesWithContentDescription(UiConstant.GalleryGridContentDescription)
//                    .fetchSemanticsNodes().size == 1
//            }

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
