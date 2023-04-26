package com.asad.metappgallery.searchScreen.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asad.metappgallery.searchScreen.presentation.util.UiConstant

/**
 * A BoxScope provides a scope for the children of Box and BoxWithConstraints.
 * */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BoxScope.CustomBottomSearchBar(
    searchedValue: TextFieldValue,
    onSearchedValueChanged: (TextFieldValue) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    val (searchInputFocusRequester) = remember { FocusRequester.createRefs() }

    val onKeyboardDonePressed: KeyboardActionScope.() -> Unit = {
        focusManager.clearFocus()
    }

    // This supposed to be run only once
    LaunchedEffect(key1 = true) {
        searchInputFocusRequester.requestFocus()
    }

    Card(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSecondary,
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(12.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 4.dp,
                    horizontal = 8.dp,
                ), // Due to Material Design 3: https://m3.material.io/components/top-app-bar/specs#e3fd3eba-0444-437c-9a82-071ef03d85b1
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "search_icon",
                modifier = Modifier
                    .requiredWidth(48.dp)
                    .padding(horizontal = 12.dp),
            )

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = searchedValue,
                onValueChange = onSearchedValueChanged,
                placeholder = {
                    Text(
                        text = "Start searching...",
                        style = MaterialTheme.typography.caption.copy(
                            color = MaterialTheme.colors.onSecondary,
                        ),
                        modifier = Modifier.fillMaxSize(),
                    )
                },
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .focusRequester(searchInputFocusRequester)
                    .requiredHeight(48.dp)
                    .semantics {
                        contentDescription = UiConstant.SearchGalleryTextField
                    },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = onKeyboardDonePressed,
                ),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                    backgroundColor = MaterialTheme.colors.surface,
                ),
                textStyle = MaterialTheme.typography.caption.copy(
                    color = MaterialTheme.colors.onSecondary,
                ),
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomBottomSearchBarPreview() {
    Box(modifier = Modifier.fillMaxSize()){
        CustomBottomSearchBar(searchedValue = TextFieldValue("sunflower"), onSearchedValueChanged = {})
    }
}
