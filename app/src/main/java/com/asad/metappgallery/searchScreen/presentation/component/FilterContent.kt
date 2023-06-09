package com.asad.metappgallery.searchScreen.presentation.component

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asad.metappgallery.searchScreen.domain.model.DepartmentModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun FilterContent(
    currentState: ModalBottomSheetValue,
    departmentModels: List<DepartmentModel>,
    onDepartmentClicked: (DepartmentModel) -> Unit,
    updateBottomSheetState: (List<DepartmentModel>) -> Unit,
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

                items(count = departmentModels.size) { index ->
                    ListItem(
                        modifier = Modifier
                            .clickable {
                                Toast.makeText(
                                    contextForToast,
                                    departmentModels[index].displayName,
                                    Toast.LENGTH_SHORT,
                                ).show()

                                onHideBottomSheet.invoke()
                            },
                        text = {
                            Text(text = departmentModels[index].displayName)
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
