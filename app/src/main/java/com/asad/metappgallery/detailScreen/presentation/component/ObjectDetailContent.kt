package com.asad.metappgallery.detailScreen.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asad.metappgallery.R
import com.asad.metappgallery.core.CoreString
import com.asad.metappgallery.core.presentation.CustomNetworkImage
import com.asad.metappgallery.detailScreen.domain.model.ObjectModel
import com.asad.metappgallery.detailScreen.domain.model.TagModel
import com.asad.metappgallery.detailScreen.presentation.util.ObjectDetailConstants

@Composable
fun BoxScope.ObjectDetailContent(data: ObjectModel) {
    val configuration = LocalConfiguration.current

    val surfaceColor = MaterialTheme.colors.surface
    val statusBarColor = MaterialTheme.colors.primaryVariant

    val scrollState = rememberScrollState()

    (data.primaryImageSmall ?: data.primaryImage)?.let {
        CustomNetworkImage(
            url = it,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .requiredWidth(
                    configuration.screenWidthDp.dp,
                )
                .requiredHeight(
                    configuration.screenWidthDp.dp,
                )
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        val topGradient = Brush.verticalGradient(
                            colors = listOf(statusBarColor, Color.Transparent),
                            startY = 0f,
                            endY = 3 * size.height / 4,
                        )
                        drawRect(topGradient)
                    }
                }
                .graphicsLayer(),
            imageModifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            errorBuilder = {
                Image(
                    painter = painterResource(id = R.drawable.place_holder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics {
                            this.contentDescription =
                                CoreString.CustomNetworkImageErrorTitle
                        },
                    contentDescription = null,
                )
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .semantics {
                contentDescription = ObjectDetailConstants.ColumnContentDescription
            },
    ) {
        Box(
            modifier = Modifier
                .requiredHeight(configuration.screenWidthDp.dp)
                .fillMaxWidth(),
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()

                            val bottomGradient = Brush.verticalGradient(
                                colors = listOf(surfaceColor, Color.Transparent),
                                startY = size.height,
                                endY = size.height / 3,
                            )
                            drawRect(bottomGradient)
                        }
                    },
            )

            Text(
                text = data.title ?: data.objectName ?: ObjectDetailConstants.GalleryInfo,
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onSecondary,
                ),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .semantics {
                        contentDescription = ObjectDetailConstants.ObjectContentDescriptionTitle
                    },
            )
        }

        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
                .background(surfaceColor),
        )

        Text(
            text = ObjectDetailConstants.artistDisplayName(data.artistDisplayName),
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onSecondary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(surfaceColor)
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .semantics {
                    contentDescription = ObjectDetailConstants.ArtistContentDescriptionTitle
                },
        )

        Text(
            text = ObjectDetailConstants.department(data.department),
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onSecondary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = surfaceColor)
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .semantics {
                    contentDescription = ObjectDetailConstants.DepartmentContentDescriptionTitle
                },
        )

        Text(
            text = ObjectDetailConstants.classification(data.classification),
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onSecondary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = surfaceColor)
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .semantics {
                    contentDescription = ObjectDetailConstants.ClassificationContentDescriptionTitle
                },
        )
        Text(
            text = ObjectDetailConstants.culture(data.culture),
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onSecondary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = surfaceColor)
                .padding(horizontal = 24.dp, vertical = 12.dp),
        )
        Text(
            text = ObjectDetailConstants.portfolio(data.portfolio),
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onSecondary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = surfaceColor)
                .padding(horizontal = 24.dp, vertical = 12.dp),
        )
        Text(
            text = ObjectDetailConstants.objectDate(data.objectDate),
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onSecondary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = surfaceColor)
                .padding(horizontal = 24.dp, vertical = 12.dp),
        )

        Text(
            text = ObjectDetailConstants.ArtistDisplayBio,
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onSecondary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = surfaceColor)
                .padding(horizontal = 24.dp, vertical = 12.dp),
        )
        Text(
            text = data.artistDisplayBio ?: "-",
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onSecondary,
            ),
            maxLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = surfaceColor)
                .padding(start = 24.dp, end = 24.dp, bottom = 12.dp),
        )

        data.tagModels?.let {
            TagsContent(tags = it)
        }

        data.additionalImages?.let {
            ObjectAdditionalImageContent(images = it, surfaceColor)
        }

        Spacer(
            modifier = Modifier
                .requiredHeight(configuration.screenWidthDp.dp - 200.dp)
                .fillMaxWidth()
                .background(surfaceColor)
                .semantics {
                    contentDescription = "key"
                },
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ObjectDetailContentPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        ObjectDetailContent(
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
        )
    }
}
