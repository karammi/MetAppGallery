package com.asad.metappgallery.searchScreen.presentation.viewModel

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.core.presentation.UiState
import com.asad.metappgallery.searchScreen.data.dataSource.FakeSuccessGalleryRemoteDataSourceImpl
import com.asad.metappgallery.searchScreen.data.repository.FakeErrorGalleryRepositoryImpl
import com.asad.metappgallery.searchScreen.domain.model.GalleryResponseModel
import com.asad.metappgallery.searchScreen.domain.repository.GalleryRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GallerySearchViewModelTest {

    private val mainThread = newSingleThreadContext("UI thread")

    lateinit var fakeSuccessGalleryRepository: GalleryRepository
    lateinit var fakeErrorGalleryRepository: GalleryRepository

    private lateinit var gallerySearchViewModel: GallerySearchViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher = mainThread)
        fakeSuccessGalleryRepository = FakeSuccessGalleryRemoteDataSourceImpl()
        fakeErrorGalleryRepository = FakeErrorGalleryRepositoryImpl()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThread.close()
    }

    @Test
    fun initialUiState() = runTest {
        gallerySearchViewModel =
            GallerySearchViewModel(fakeSuccessGalleryRepository, testDispatcher)
        gallerySearchViewModel.uiState.test {
            val emission = awaitItem()

            assertThat(emission.isSearching).isEqualTo(false)
            assertThat(emission.searchResult).isEqualTo(UiState.Empty)
            assertThat(emission.searchQuery).isEqualTo(TextFieldValue(""))
        }
    }

    @Test
    fun resetUiState_whenDifferentWithCurrentState_thenUpdateUiState() = runTest {
        gallerySearchViewModel =
            GallerySearchViewModel(fakeSuccessGalleryRepository, testDispatcher)
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
    fun whenSetRefreshingTriggered_thenUpdateUiStateToLoading() = runTest {
        gallerySearchViewModel =
            GallerySearchViewModel(fakeSuccessGalleryRepository, testDispatcher)
        val newState = UiState.Loading

        val job = launch(Dispatchers.Main) {
            gallerySearchViewModel.uiState.test {
                skipItems(1)

                val emission = awaitItem()
                assertThat(emission.searchResult).isEqualTo(newState)

                cancelAndConsumeRemainingEvents()
            }
        }

        gallerySearchViewModel.showLoading()

        job.join()
        job.cancel()
    }

    @Test
    fun whenSearchGalleryIsTriggered_thenUpdateUiStateToSuccess() = runTest {
        gallerySearchViewModel =
            GallerySearchViewModel(fakeSuccessGalleryRepository, testDispatcher)
        /** Arrange*/
        val queryString = "sunflower"
        val expectedSearchResult = DataResult.Success(
            value = GalleryResponseModel(
                total = 10,
                objectIDs = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            ),
        )
        val job = launch(Dispatchers.Main) {
            gallerySearchViewModel.uiState.test {
                // skip two state
                skipItems(2)

                /** Assert*/
                val emission = awaitItem()
                assertThat(emission.searchResult).isEqualTo(UiState.Success(expectedSearchResult.value))

                cancelAndConsumeRemainingEvents()
            }
        }

        /** Act*/
        gallerySearchViewModel.fetchGalleryList(queryString)

        job.join()
        job.cancel()
    }

    @Test
    fun whenSetSearchTextFieldIsCalled_thenUpdateUiStateWithCorrectTextField() = runTest {
        gallerySearchViewModel =
            GallerySearchViewModel(fakeSuccessGalleryRepository, testDispatcher)
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
    fun fetchObjectsWhichContainsQuery_thenUpdateUiStateToSuccess() = runTest {
        gallerySearchViewModel =
            GallerySearchViewModel(fakeSuccessGalleryRepository, testDispatcher)
        val searchedQueryText = "sunflower"
        val response = UiState.Success(
            GalleryResponseModel(
                total = 10,
                objectIDs = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            ),
        )

        val job = launch(Dispatchers.Main) {
            gallerySearchViewModel.uiState.test {
                skipItems(1)

                val firstEmission = awaitItem()
                assertThat(firstEmission.searchResult).isEqualTo(UiState.Loading)

                val secondEmission = awaitItem()
                assertThat(secondEmission.searchResult).isEqualTo(response)

                cancelAndConsumeRemainingEvents()
            }
        }

        gallerySearchViewModel.fetchGalleryList(queryString = searchedQueryText)

        job.join()
        job.cancel()
    }

    @Test
    fun whenSearchTextFieldIsCleared_thenUpdateUiStateToEmptyTextField() = runTest {
        gallerySearchViewModel =
            GallerySearchViewModel(fakeSuccessGalleryRepository, testDispatcher)
        val searchedQueryText = TextFieldValue("")

        val job = launch(Dispatchers.Main) {
            gallerySearchViewModel.uiState.test {
                val firstEmission = awaitItem()
                assertThat(firstEmission.searchQuery).isEqualTo(searchedQueryText)
                assertThat(firstEmission.searchResult).isEqualTo(UiState.Empty)

                cancelAndConsumeRemainingEvents()
            }
        }

        gallerySearchViewModel.setSearchText(value = searchedQueryText)

        job.join()
        job.cancel()
    }

    @Test
    fun fetchObjectWhichContainError_shouldEmitErrorState(): Unit = runTest {
        gallerySearchViewModel = GallerySearchViewModel(fakeErrorGalleryRepository, testDispatcher)
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
