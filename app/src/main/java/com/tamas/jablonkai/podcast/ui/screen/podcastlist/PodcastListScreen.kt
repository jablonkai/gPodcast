package com.tamas.jablonkai.podcast.ui.screen.podcastlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Podcasts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.tamas.jablonkai.podcast.ui.theme.Dimens
import com.tamas.jablonkai.podcast.ui.widget.EmptyWidget
import com.tamas.jablonkai.podcast.ui.widget.ErrorWidget
import com.tamas.jablonkai.podcast.ui.widget.LoadingWidget

@Composable
fun PodcastListScreen(
    viewModel: PodcastListViewModel,
    onPodcastClick: (String) -> Unit
) {
    val viewState by viewModel.state.collectAsState()

    when (viewState) {
        is PodcastListState.Error -> ErrorWidget()
        is PodcastListState.Loaded -> ContentLoaded(
            viewState as PodcastListState.Loaded,
            onPodcastClick
        )
        is PodcastListState.Loading -> LoadingWidget()
        is PodcastListState.Empty -> EmptyWidget()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentLoaded(
    state: PodcastListState.Loaded,
    onPodcastClick: (String) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp - 2 * Dimens.defaultPadding.value
    val n = (screenWidth / Dimens.imageSize.value).toInt()
    val imageSize = (screenWidth / n).dp

    LazyVerticalGrid(
        cells = GridCells.Adaptive(imageSize),
        contentPadding = PaddingValues(all = Dimens.defaultPadding),
        content = {
            items(state.podcasts.size) { index ->
                val podcast = state.podcasts[index]
                Column(modifier = Modifier
                    .padding(Dimens.smallPadding)
                    .clickable(
                        onClick = {
                            onPodcastClick(podcast.url)
                        }
                    )) {
                    GlideImage(
                        imageModel = podcast.scaledLogoUrl,
                        modifier = Modifier.size(imageSize, imageSize),
                        placeHolder = Icons.Rounded.Podcasts,
                        error = Icons.Rounded.Podcasts,
                        contentScale = ContentScale.Fit
                    )
                    Text(podcast.title, maxLines = 2, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    )
}
