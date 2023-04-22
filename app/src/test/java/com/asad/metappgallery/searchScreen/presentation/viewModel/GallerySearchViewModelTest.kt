package com.asad.metappgallery.searchScreen.presentation.viewModel

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.core.presentation.UiState
import com.asad.metappgallery.searchScreen.data.dataSource.FakeErrorGalleryRemoteDataSourceImpl
import com.asad.metappgallery.searchScreen.data.dataSource.FakeSuccessGalleryRemoteDataSourceImpl
import com.asad.metappgallery.searchScreen.data.dataSource.GalleryRemoteDataSource
import com.asad.metappgallery.searchScreen.data.dataSource.remote.model.GalleryResponseEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class GallerySearchViewModelTest {

    private val mainThread = newSingleThreadContext("UI thread")

    private lateinit var mockedSuccessGalleryRemoteDataSource: GalleryRemoteDataSource

    private lateinit var mockedErrorGalleryRemoteDataSource: GalleryRemoteDataSource

    @Mock
    private lateinit var galleryRemoteDataSource: GalleryRemoteDataSource


    private lateinit var gallerySearchViewModel: GallerySearchViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher = mainThread)
        mockedSuccessGalleryRemoteDataSource = FakeSuccessGalleryRemoteDataSourceImpl()
        mockedErrorGalleryRemoteDataSource = FakeErrorGalleryRemoteDataSourceImpl()
        galleryRemoteDataSource = mock(GalleryRemoteDataSource::class.java)
        gallerySearchViewModel = GallerySearchViewModel(galleryRemoteDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThread.close()
    }

    @Test
    fun initialUiState() = runTest {
        gallerySearchViewModel = GallerySearchViewModel(mockedSuccessGalleryRemoteDataSource)
        gallerySearchViewModel.uiState.test {
            val emission = awaitItem()

            assertThat(emission.isSearching).isEqualTo(false)
            assertThat(emission.searchResult).isEqualTo(UiState.Empty)
            assertThat(emission.searchQuery).isEqualTo(TextFieldValue(""))
        }
    }

    @Test
    fun resetUiState_whenDifferentWithCurrentState_thenUpdateUiState() = runTest {
        gallerySearchViewModel = GallerySearchViewModel(mockedSuccessGalleryRemoteDataSource)
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
        gallerySearchViewModel = GallerySearchViewModel(mockedSuccessGalleryRemoteDataSource)
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
        gallerySearchViewModel = GallerySearchViewModel(mockedSuccessGalleryRemoteDataSource)
        /** Arrange*/
        val queryString = "sunflower"
        val expectedSearchResult = DataResult.Success(
            value = GalleryResponseEntity(
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
        gallerySearchViewModel = GallerySearchViewModel(mockedSuccessGalleryRemoteDataSource)
    fun setIsSearching_whenDifferentWithCurrentState_thenUpdateUiState() = runTest {
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
    fun setSearchText_thenUpdateUiState() = runTest {
        val searchedText = TextFieldValue("sunflower")

        val job = launch(Dispatchers.Main) {
            gallerySearchViewModel.uiState.test {
                skipItems(1)

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
        gallerySearchViewModel = GallerySearchViewModel(mockedSuccessGalleryRemoteDataSource)
        /** Arrange*/
        val searchedQueryText = "sunflower"

        val fakeGalleryResponse = GalleryResponseEntity(
            total = 10,
            objectIDs = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
        )

        val response = UiState.Success(data = fakeGalleryResponse)

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
        gallerySearchViewModel = GallerySearchViewModel(mockedSuccessGalleryRemoteDataSource)
        val searchedQueryText = TextFieldValue("")

        val job = launch(Dispatchers.Main) {
            gallerySearchViewModel.uiState.test {
        doReturn(
            DataResult.Success(value = fakeGalleryResponse),
        ).`when`(galleryRemoteDataSource).fetchList(searchedQueryText)

        val job = launch(Dispatchers.Main) {
            gallerySearchViewModel.uiState.test {
                /** Assert*/
                val firstEmission = awaitItem()
                assertThat(firstEmission.searchQuery).isEqualTo(searchedQueryText)
                assertThat(firstEmission.searchResult).isEqualTo(UiState.Empty)


                val secondEmission = awaitItem()
                assertThat(secondEmission.isSearching).isEqualTo(true)
//                assertThat(secondEmission.searchResult).isEqualTo(response)

                val thirdEmission = awaitItem()
                assertThat(thirdEmission.isSearching).isEqualTo(true)
                assertThat(thirdEmission.searchResult).isEqualTo(response)

                val fourthEmission = awaitItem()
                assertThat(fourthEmission.isSearching).isEqualTo(false)
                assertThat(fourthEmission.searchResult).isEqualTo(response)

                cancelAndConsumeRemainingEvents()
            }
        }

        /** Act*/
        gallerySearchViewModel.fetchGalleryList(queryString = searchedQueryText)

        job.join()
        job.cancel()
    }

    @Test
    fun fetchObjectsWhichContainsEmptyQuery_shouldEmitEmptyState() = runTest {
        val searchedQueryText = ""
        val response = UiState.Empty

        doReturn(
            DataResult.Success(value = GalleryResponseEntity(total = 0, objectIDs = emptyList())),
        ).`when`(galleryRemoteDataSource).fetchList(searchedQueryText)

        val job = launch(Dispatchers.Main) {
            gallerySearchViewModel.uiState.test {
                skipItems(1)

                val firstEmission = awaitItem()
                assertThat(firstEmission.searchResult).isEqualTo(UiState.Empty)

                val secondEmission = awaitItem()
                assertThat(secondEmission.searchResult).isEqualTo(response)

                cancelAndConsumeRemainingEvents()
            }
        }

        gallerySearchViewModel.setSearchText(value = searchedQueryText)

        job.join()
        job.cancel()
    }

    @Test
    fun fetchObjectWhichContainError_shouldEmitErrorState(): Unit = runTest {
        gallerySearchViewModel = GallerySearchViewModel(mockedErrorGalleryRemoteDataSource)
        val searchQuery = ""

        val response = UiState.Error("Oops!,An error occurred!")
        doReturn(
            DataResult.Error(exception = Exception("Oops!,An error occurred!")),
        ).`when`(galleryRemoteDataSource).fetchList(searchQuery)

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
