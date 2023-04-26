package com.asad.metappgallery.searchScreen.presentation.component

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.asad.metappgallery.R
import com.asad.metappgallery.searchScreen.presentation.util.UiConstant.HEADER_DEFAULT_HEIGHT
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BoxScope.CustomAppBar(
    title: String,
    onNavigateUp: (() -> Unit)? = null,
    isTransparent: Boolean = false,
    onFilterClicked: (() -> Unit)? = null,
) {
    /**
     * This will be updated only when [isTransparent] changes
     * with the help of [derivedStateOf] API
     */
    val elevation by remember(isTransparent) {
        derivedStateOf {
            if (isTransparent) {
                0.dp
            } else {
                10.dp
            }
        }
    }

    val primaryColor = MaterialTheme.colors.primary

    /**
     * This will be updated only when [isTransparent] changes
     * with the help of the [derivedStateOf] API
     */
    val backgroundColor by remember(isTransparent) {
        derivedStateOf {
            if (isTransparent) {
                Color.Transparent
            } else {
                primaryColor
            }
        }
    }

    Card(
        elevation = elevation,
        backgroundColor = backgroundColor,
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(HEADER_DEFAULT_HEIGHT)
            .align(Alignment.TopCenter)
            .zIndex(1f), // zIndex is added so that card's shadow would be drawn on top of the content
        contentColor = MaterialTheme.colors.onPrimary,
        shape = RectangleShape,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (onFilterClicked != null) Arrangement.SpaceBetween else Arrangement.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp), // Due to Material Design 3: https://m3.material.io/components/top-app-bar/specs#e3fd3eba-0444-437c-9a82-071ef03d85b1
        ) {
            onNavigateUp?.let {
                CustomTouchableScale(onClick = it) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .requiredWidth(24.dp)
                            .requiredHeight(48.dp),
                        tint = MaterialTheme.colors.onPrimary,
                    )
                }

                Spacer(modifier = Modifier.requiredWidth(24.dp))
            }

            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary,
            )

            onFilterClicked?.let {
                CustomTouchableScale(onClick = it) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_filter_alt_24),
                        contentDescription = null,
                        modifier = Modifier
                            .requiredWidth(24.dp)
                            .requiredHeight(48.dp),
                        tint = MaterialTheme.colors.onPrimary,
                    )
                }
            }
        }
    }
}

private enum class TouchableScaleState {
    Pressed,
    Idle,
}

@Composable
fun CustomTouchableScale(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val currentState = remember { MutableTransitionState(TouchableScaleState.Idle) }
    val transition = updateTransition(currentState, label = "transition")

    val contentScale by transition.animateFloat(
        label = "opacity",
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow,
            )
        },
    ) { state ->
        when (state) {
            TouchableScaleState.Pressed -> 0.9f
            TouchableScaleState.Idle -> 1f
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .graphicsLayer {
                scaleX = contentScale
                scaleY = contentScale
                clip = false
            }
            .pointerInput(enabled) {
                detectTapGestures(
                    onPress = {
                        if (enabled) {
                            coroutineScope.launch {
                                try {
                                    currentState.targetState = TouchableScaleState.Pressed
                                    awaitRelease()
                                    currentState.targetState = TouchableScaleState.Idle
                                    delay(200)
                                    onClick()
                                } catch (e: CancellationException) {
                                    currentState.targetState = TouchableScaleState.Idle
                                }
                            }
                        }
                    },
                )
            },
    ) {
        content()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CustomAppBarPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        CustomAppBar(
            title = "App Gallery",
            onNavigateUp = {},
            isTransparent = false,
            onFilterClicked = null,
        )
    }
}
