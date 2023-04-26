package com.asad.metappgallery.detailScreen.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.asad.metappgallery.core.presentation.UiState
import com.asad.metappgallery.detailScreen.data.repository.FakeErrorObjectDetailRepositoryImpl
import com.asad.metappgallery.detailScreen.data.repository.FakeSuccessObjectDetailRepositoryImpl
import com.asad.metappgallery.detailScreen.domain.model.ObjectModel
import com.asad.metappgallery.detailScreen.domain.model.TagModel
import com.asad.metappgallery.detailScreen.domain.repository.ObjectDetailRepository
import com.asad.metappgallery.detailScreen.presentation.model.ObjectDetailUiState
import com.asad.metappgallery.detailScreen.presentation.viewModel.ObjectDetailViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
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
class ObjectDetailViewModelTest {

    private val newMainThread = newSingleThreadContext("Ui thread")

    private lateinit var objectDetailViewModel: ObjectDetailViewModel

    private lateinit var fakedSuccessObjectDetailRepository: ObjectDetailRepository
    private lateinit var fakedErrorObjectDetailRepository: ObjectDetailRepository

    private val testDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(newMainThread)
        fakedErrorObjectDetailRepository = FakeErrorObjectDetailRepositoryImpl()
        fakedSuccessObjectDetailRepository = FakeSuccessObjectDetailRepositoryImpl()

        objectDetailViewModel = ObjectDetailViewModel(
            repository = fakedSuccessObjectDetailRepository,
            savedStateHandle = SavedStateHandle(),
            ioDispatcher = testDispatcher,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        newMainThread.close()
    }

    @Test
    fun whenInitialUiState_shouldBeEmptyState() = runTest {
        val expectedUiState = UiState.Empty

        objectDetailViewModel.uiState.test {
            val emission = awaitItem()

            assertThat(emission.objectDetailState).isEqualTo(expectedUiState)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun whenSetLoadingIsCalled_thenUpdateUiStateToLoading(): Unit = runTest {
        val expectedUiState = ObjectDetailUiState(objectDetailState = UiState.Loading)

        val job = launch(Dispatchers.Main) {
            objectDetailViewModel.uiState.test {
                skipItems(1)

                assertThat(awaitItem()).isEqualTo(expectedUiState)

                cancelAndIgnoreRemainingEvents()
            }
        }

        objectDetailViewModel.showLoading()

        job.join()
        job.cancel()
    }

    @Test
    fun whenFetchedObjectDetail_thenUpdateUiStateToSuccess(): Unit = runTest {
        /**Arrange*/
        val fakeObjectId = 1
        val expectedResult = ObjectDetailUiState(
            objectDetailState = UiState.Success(
                data = ObjectModel(
                    objectID = 1,
                    isHighlight = false,
                    isPublicDomain = true,
                    primaryImage = "https://images.metmuseum.org/CRDImages/ad/original/85I_ACF3100R5.jpg",
                    primaryImageSmall = " https://images.metmuseum.org/CRDImages/ad/web-large/85I_ACF3100R5.jpg",
                    additionalImages = listOf(
                        "https://images.metmuseum.org/CRDImages/ad/original/85P_PAINTDEC01R4.jpg",
                        "https://images.metmuseum.org/CRDImages/ad/original/257477.jpg",
                        "https://images.metmuseum.org/CRDImages/ad/original/258476.jpg",
                        "https://images.metmuseum.org/CRDImages/ad/original/258475.jpg",
                        "https://images.metmuseum.org/CRDImages/ad/original/197998.jpg",
                        "https://images.metmuseum.org/CRDImages/ad/original/17636.jpg",
                    ),
                    constituentModels = null,
                    department = "The American Wing",
                    objectName = "Chest with drawer",
                    title = "Chest with drawer",
                    culture = "American",
                    portfolio = "",
                    artistDisplayName = "",
                    artistDisplayBio = "",
                    objectDate = "1705",
                    objectBeginDate = 1705,
                    objectEndDate = 1705,
                    classification = "",
                    metadataDate = "2023-02-07 T04 :46:51.34 Z",
                    repository = "Metropolitan Museum of Art, New York, NY",
                    objectURL = "https://www.metmuseum.org/art/collection/search/2032",
                    tagModels = listOf(
                        TagModel(
                            term = "Flowers",
                            aatUrl = "http://vocab.getty.edu/page/aat/300132399",
                            wikidataURL = "https://www.wikidata.org/wiki/Q506",
                        ),
                    ),
                ),
            ),
        )

        val job = launch(Dispatchers.Main) {
            objectDetailViewModel.uiState.test {
                skipItems(2)
                /**Assert*/
                assertThat(awaitItem()).isEqualTo(expectedResult)

                cancelAndIgnoreRemainingEvents()
            }
        }

        /**Act*/
        objectDetailViewModel.fetchObjectDetail(fakeObjectId)

        job.join()
        job.cancel()
    }

    @Test
    fun whenFetchedObjectDetailFailed_thenUpdateUiStateToError(): Unit = runTest {
        /**Arrange*/
        val fakeObjectId = 1
        objectDetailViewModel = ObjectDetailViewModel(
            repository = fakedErrorObjectDetailRepository,
            savedStateHandle = SavedStateHandle(),
            ioDispatcher = testDispatcher,
        )

        val expectedResult = ObjectDetailUiState(
            objectDetailState = UiState.Error("Oops! An error occurred!"),
        )

        val job = launch(Dispatchers.Main) {
            objectDetailViewModel.uiState.test {
                skipItems(2)
                /**Assert*/
                assertThat(awaitItem()).isEqualTo(expectedResult)

                cancelAndIgnoreRemainingEvents()
            }
        }

        /**Act*/
        objectDetailViewModel.fetchObjectDetail(fakeObjectId)

        job.join()
        job.cancel()
    }
}
