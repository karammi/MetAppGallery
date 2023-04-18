package com.asad.metappgallery.detailScreen.presentation

import app.cash.turbine.test
import com.asad.metappgallery.app.UiState
import com.asad.metappgallery.detailScreen.data.dataSource.FakeObjectDetailRemoteDataSourceImpl
import com.asad.metappgallery.detailScreen.data.dataSource.ObjectDetailRemoteDataSource
import com.asad.metappgallery.detailScreen.presentation.model.ObjectDetailUiState
import com.asad.metappgallery.detailScreen.presentation.viewModel.ObjectDetailViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class ObjectDetailViewModelTest {

    private val newMainThread = newSingleThreadContext("Ui thread")

    private lateinit var objectDetailViewModel: ObjectDetailViewModel

    private lateinit var fakeObjectDetailRemoteDataSource: ObjectDetailRemoteDataSource

    @Before
    fun setup() {
        Dispatchers.setMain(newMainThread)
        fakeObjectDetailRemoteDataSource = FakeObjectDetailRemoteDataSourceImpl()
        objectDetailViewModel = ObjectDetailViewModel(fakeObjectDetailRemoteDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        newMainThread.close()
    }

    @Test
    fun whenInitialUiState_shouldBeEmptyState() = runBlocking {
        val expectedUiState = UiState.Empty

        objectDetailViewModel.uiState.test {
            val emission = awaitItem()

            assertThat(emission.isRefreshing).isEqualTo(false)
            assertThat(emission.objectDetailState).isEqualTo(expectedUiState)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun whenSetLoading_shouldBeLoadingState(): Unit = runBlocking {
        val job = launch(Dispatchers.Main) {
            objectDetailViewModel.uiState.test {
                skipItems(1)

                assertThat(awaitItem()).isEqualTo(
                    ObjectDetailUiState(
                        isRefreshing = true,
                        objectDetailState = UiState.Empty,
                    ),
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

        objectDetailViewModel.setLoading(true)

        job.join()
        job.cancel()
    }

    @Test
    fun whenSetHideLoading_shouldNotBeLoadingState(): Unit = runBlocking {
        val job = launch(Dispatchers.Main) {
            objectDetailViewModel.uiState.test {
                assertThat(awaitItem()).isEqualTo(
                    ObjectDetailUiState(
                        isRefreshing = false,
                        objectDetailState = UiState.Empty,
                    ),
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

        objectDetailViewModel.setLoading(false)

        job.join()
        job.cancel()
    }

    @Test
    fun whenFetchedObjectDetail_thenShouldUpdateUiState(): Unit = runBlocking {
        /**Arrange*/
        val fakeObjectId = 2023
        val fakeResponse = fakeObjectDetailRemoteDataSource.fetchObjectDetail(fakeObjectId)

        val job = launch(Dispatchers.Main) {
            objectDetailViewModel.uiState.test {
                /**Assert*/
                val firstEmission = awaitItem()

                val secondEmission = awaitItem()

//                assertThat(awaitItem())
//                    .isEqualTo()

                cancelAndIgnoreRemainingEvents()
            }
        }

        /**Act*/
        objectDetailViewModel.fetchObjectDetail(fakeObjectId)

        job.join()
        job.cancel()
    }
}
