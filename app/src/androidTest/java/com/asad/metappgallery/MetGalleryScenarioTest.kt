package com.asad.metappgallery

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.asad.metappgallery.app.MainActivity
import com.asad.metappgallery.app.MetGalleryFinderApp
import com.asad.metappgallery.core.MockServerDispatcher
import com.asad.metappgallery.core.di.module.NetworkModule
import com.asad.metappgallery.searchScreen.presentation.util.UiConstant
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
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

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var okHttpClient: OkHttpClient
    lateinit var mockWebServer: MockWebServer

    private val serviceMap: Map<String, String> = mapOf(
        Pair("search", "gallery_search_success.json"),
        Pair("objects/", "object_detail_success.json"),
    )
    lateinit var idlingResources: OkHttp3IdlingResource

    @Before
    fun setup() {
        hiltRule.inject()

        composeTestRule.setContent {
            composeTestRule.mainClock.autoAdvance = true
            MetGalleryFinderApp()
        }

        idlingResources = OkHttp3IdlingResource.create("okhttp", okHttpClient)

        mockWebServer = MockWebServer()
        mockWebServer.start(port = 8080)
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
        }
    }
}
