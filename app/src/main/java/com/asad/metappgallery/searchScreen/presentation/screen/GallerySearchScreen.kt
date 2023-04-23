package com.asad.metappgallery.searchScreen.presentation.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asad.metappgallery.R
import com.asad.metappgallery.core.presentation.UiState
import com.asad.metappgallery.core.util.ComposeUtil
import com.asad.metappgallery.core.util.SystemUiUtil
import com.asad.metappgallery.searchScreen.data.GallerySearchConstant
import com.asad.metappgallery.searchScreen.data.model.Department
import com.asad.metappgallery.searchScreen.presentation.component.CustomAppBar
import com.asad.metappgallery.searchScreen.presentation.component.CustomBottomSearchBar
import com.asad.metappgallery.searchScreen.presentation.component.CustomEmptyContent
import com.asad.metappgallery.searchScreen.presentation.component.GallerySearchData
import com.asad.metappgallery.searchScreen.presentation.component.GallerySearchError
import com.asad.metappgallery.searchScreen.presentation.util.UiConstant
import com.asad.metappgallery.searchScreen.presentation.viewModel.GallerySearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GallerySearchScreen(
    viewModel: GallerySearchViewModel = hiltViewModel(),
    onNavigationToObjectDetail: (Int) -> Unit,
) {
    SystemUiUtil.ConfigStatusBar(color = MaterialTheme.colors.primaryVariant)

    val uiState by ComposeUtil.rememberStateWithLifecycle(stateFlow = viewModel.uiState)

    val localFocusManager = LocalFocusManager.current

    val onSearchedValueChanged: (TextFieldValue) -> Unit = { value ->
        viewModel.setSearchText(value)
    }

    val onItemClicked: (Int) -> Unit = { objectId ->
        onNavigationToObjectDetail.invoke(objectId)
        localFocusManager.clearFocus()
    }

    val onRetiredFetchingData: () -> Unit = {
        viewModel.fetchGalleryList(uiState.searchQuery.text)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .padding(top = 64.dp),
        ) {
            when (uiState.searchResult) {
                is UiState.Success -> {
                    val data = uiState.searchResult.data?.objectIDs!!
                    localFocusManager.clearFocus()
                    GallerySearchData(data = data, onItemClicked = onItemClicked)
                }

                is UiState.Error -> {
                    localFocusManager.clearFocus()
                    GallerySearchError(
                        errorMessage = uiState.searchResult.message
                            ?: "Oops! An error occurred!\n${GallerySearchConstant.FailedToFetchDataDefaultMessage}",
                        tryAgain = onRetiredFetchingData,
                    )
                }

                is UiState.Empty -> {
                    CustomEmptyContent(body = UiConstant.GallerySearchEmptyStateTitle)
                }

                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .requiredSize(48.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colors.secondaryVariant,
                        strokeWidth = 4.dp,
                    )
                }
            }
        }

        // App Bar
        CustomAppBar(
            title = stringResource(id = R.string.app_name),
            onFilterClicked = {
                viewModel.setIsHighlightValue(true)
            },
        )

        // Bottom Search Bar
        CustomBottomSearchBar(
            searchedValue = uiState.searchQuery,
            onSearchedValueChanged = onSearchedValueChanged,
        )

        if (uiState.isHighlightSelected == true) {
            uiState.departments.data?.let {
                FilterContent(
                    currentState = ModalBottomSheetValue.HalfExpanded,
                    departments = it.departments,
                    onDepartmentClicked = {},
                    updateBottomSheetState = {},
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun FilterContent(
    currentState: ModalBottomSheetValue,
    departments: List<Department>,
    onDepartmentClicked: (Department) -> Unit,
    updateBottomSheetState: (List<Department>) -> Unit,
) {
    val contextForToast = LocalContext.current.applicationContext

    val coroutineScope = rememberCoroutineScope()

    val bottomSheetState = rememberModalBottomSheetState(initialValue = currentState)

    val onHideBottomSheet: () -> Unit = {
        coroutineScope.launch {
            bottomSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            LazyColumn {
                stickyHeader {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OutlinedButton(
                            onClick = {
                                coroutineScope.launch {
                                    bottomSheetState.hide()
                                }
                            },
                            border = BorderStroke(1.dp, color = Color.Black),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.DarkGray,
                                contentColor = Color.White,
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                        ) {
                            Text(
                                text = "Done",
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .fillMaxHeight(),
                                textAlign = TextAlign.Center,
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = onHideBottomSheet,
                            border = BorderStroke(1.dp, color = Color.Black),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.onSecondary,
                                contentColor = Color.Black,
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                        ) {
                            Text(
                                text = "Cancel",
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .fillMaxHeight(),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

                items(count = departments.size) { index ->
                    ListItem(
                        modifier = Modifier
                            .clickable {
                                Toast.makeText(
                                    contextForToast,
                                    departments[index].displayName,
                                    Toast.LENGTH_SHORT,
                                ).show()

                                onHideBottomSheet.invoke()
                            },
                        text = {
                            Text(text = departments[index].displayName)
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "SelectedDepartment",
                            )
                        },
                    )
                }
            }
        },
        sheetState = bottomSheetState,
        scrimColor = Color.LightGray,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onHideBottomSheet.invoke()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        }
    }
}
