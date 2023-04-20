package com.asad.metappgallery.searchScreen.presentation.viewModel

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.asad.metappgallery.app.UiState
import com.asad.metappgallery.searchScreen.data.dataSource.FakeGalleryRemoteDataSourceImpl
import com.asad.metappgallery.searchScreen.data.dataSource.GalleryRemoteDataSource
import com.asad.metappgallery.searchScreen.data.model.GalleryResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class GallerySearchViewModelTest {

    private val mainThread = newSingleThreadContext("UI thread")

    private lateinit var galleryRemoteDataSource: GalleryRemoteDataSource

    private lateinit var gallerySearchViewModel: GallerySearchViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher = mainThread)
        galleryRemoteDataSource = FakeGalleryRemoteDataSourceImpl()
        gallerySearchViewModel = GallerySearchViewModel(galleryRemoteDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThread.close()
    }

    @Test
    fun initialUiState() = runBlocking {
        gallerySearchViewModel.uiState.test {
            val emission = awaitItem()

            assertThat(emission.isSearching).isEqualTo(false)
            assertThat(emission.searchResult).isEqualTo(UiState.Empty)
            assertThat(emission.searchQuery).isEqualTo(TextFieldValue(""))
        }
    }

    @Test
    fun resetUiState_whenDifferentWithCurrentState_thenUpdateUiState() = runBlocking {
        if (gallerySearchViewModel.uiState.value != gallerySearchViewModel.initialState) {
            val job = launch(Dispatchers.Main) {
                gallerySearchViewModel.uiState.test {
                    skipItems(1)

                    val emission = awaitItem()
                    assertThat(emission.isSearching).isEqualTo(false)
                    assertThat(emission.searchResult).isEqualTo(UiState.Empty)

                    cancelAndConsumeRemainingEvents()
                }
            }

            gallerySearchViewModel.resetUiState()

            job.join()
            job.cancel()
        }
    }

    @Test
    fun setIsSearching_whenDifferentWithCurrentState_thenUpdateUiState() = runBlocking {
        val newState = true

        if (gallerySearchViewModel.uiState.value.isSearching != newState) {
            val job = launch(Dispatchers.Main) {
                gallerySearchViewModel.uiState.test {
                    skipItems(1)

                    val emission = awaitItem()
                    assertThat(emission.isSearching).isEqualTo(newState)

                    cancelAndConsumeRemainingEvents()
                }
            }

            gallerySearchViewModel.setIsSearching(newState)

            job.join()
            job.cancel()
        }
    }

    @Test
    fun setSearchResult_thenUpdateUiState() = runBlocking {
        val queryString = "sunflower"
        val searchResult = galleryRemoteDataSource.fetchList(query = queryString)

        val job = launch(Dispatchers.Main) {
            gallerySearchViewModel.uiState.test {
                // skip two state
                skipItems(2)

                val emission = awaitItem()
                assertThat(emission.searchResult).isEqualTo(UiState.Success(searchResult.value))

                cancelAndConsumeRemainingEvents()
            }
        }

        gallerySearchViewModel.fetchGalleryList(queryString)

        job.join()
        job.cancel()
    }

    @Test
    fun setSearchText_thenUpdateUiState() = runBlocking {
        val searchedText = TextFieldValue("sunflower")

        val job = launch(Dispatchers.Main) {
            gallerySearchViewModel.uiState.test {
                skipItems(2)

                val emission = awaitItem()
                assertThat(emission.searchQuery).isEqualTo(searchedText)

                cancelAndConsumeRemainingEvents()
            }
        }

        gallerySearchViewModel.setSearchText(searchedText)

        job.join()
        job.cancel()
    }

    @Test
    fun fetchObjectsWhichContainsQuery_thenUpdateUiState() = runBlocking {
        val searchedQueryText = "sunflower"
        val response = UiState.Success(
            GalleryResponse(
                total = 10,
                objectIDs = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            ),
        )

        val job = launch(Dispatchers.Main) {
            gallerySearchViewModel.uiState.test {
                skipItems(1)

                val firstEmission = awaitItem()
                assertThat(firstEmission.isSearching).isEqualTo(true)
                assertThat(firstEmission.searchResult).isEqualTo(UiState.Empty)

                val secondEmission = awaitItem()
                assertThat(secondEmission.isSearching).isEqualTo(true)
                assertThat(secondEmission.searchResult).isEqualTo(response)

                val thirdEmission = awaitItem()
                assertThat(thirdEmission.isSearching).isEqualTo(false)
                assertThat(thirdEmission.searchResult).isEqualTo(response)

                cancelAndConsumeRemainingEvents()
            }
        }

        gallerySearchViewModel.fetchGalleryList(queryString = searchedQueryText)

        job.join()
        job.cancel()
    }

    @Test
    fun fetchObjectsWhichContainsEmptyQuery_shouldEmitEmptyState() = runBlocking {
        val searchedQueryText = ""
        val response = UiState.Empty

        val job = launch(Dispatchers.Main) {
            gallerySearchViewModel.uiState.test {
                skipItems(1)

                val firstEmission = awaitItem()
                assertThat(firstEmission.isSearching).isEqualTo(true)
                assertThat(firstEmission.searchResult).isEqualTo(UiState.Empty)

                val secondEmission = awaitItem()
                assertThat(secondEmission.isSearching).isEqualTo(false)
                assertThat(secondEmission.searchResult).isEqualTo(response)

                cancelAndConsumeRemainingEvents()
            }
        }

        gallerySearchViewModel.fetchGalleryList(queryString = searchedQueryText)

        job.join()
        job.cancel()
    }

    @Test
    fun fetchObjectWhichContainError_shouldEmitErrorState(): Unit = runBlocking {
        val searchQuery = ""
        val response = UiState.Error("Oops!,An error occurred!")
        val job = launch(Dispatchers.Main) {
            gallerySearchViewModel.uiState.test {
                skipItems(2)

                val firstEmission = awaitItem()

                assertThat(firstEmission.searchResult).isEqualTo(response)

                cancelAndConsumeRemainingEvents()
            }
        }

        gallerySearchViewModel.fetchGalleryList(searchQuery)

        job.join()
        job.cancel()
    }
}