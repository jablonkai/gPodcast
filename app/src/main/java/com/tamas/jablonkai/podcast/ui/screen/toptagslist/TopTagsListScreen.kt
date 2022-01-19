package com.tamas.jablonkai.podcast.ui.screen.toptagslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Podcasts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import com.skydoves.landscapist.glide.GlideImage
import com.tamas.jablonkai.podcast.data.model.Tag
import com.tamas.jablonkai.podcast.ui.theme.Dimens
import com.tamas.jablonkai.podcast.ui.widget.EmptyWidget
import com.tamas.jablonkai.podcast.ui.widget.ErrorWidget
import com.tamas.jablonkai.podcast.ui.widget.LoadingWidget

@Composable
fun TopTagListScreen(
    viewModel: TopTagsListViewModel,
    onPodcastClick: (String) -> Unit
) {
    val viewState by viewModel.state.collectAsState()

    when (viewState) {
        is TopTagsListState.Error -> ErrorWidget()
        is TopTagsListState.Loaded -> ContentLoaded(
            viewState as TopTagsListState.Loaded,
            onPodcastClick
        )
        is TopTagsListState.Loading -> LoadingWidget()
        is TopTagsListState.Empty -> EmptyWidget()
    }
}

@Composable
fun ContentLoaded(
    state: TopTagsListState.Loaded,
    onPodcastClick: (String) -> Unit,
) {
    LazyColumn(Modifier.fillMaxHeight()) {
        items(state.tags.size) { index ->
            PodcastListRow(
                state.tags[index],
                onPodcastClick
            )
        }
    }
}

@Composable
fun PodcastListRow(
    tag: Tag,
    onPodcastClick: (String) -> Unit,
) {
    Text(text = tag.title, style = MaterialTheme.typography.h6)
    LazyRow(Modifier.fillMaxWidth()) {
        items(tag.podcasts.size) { index ->
            val podcast = tag.podcasts[index]
            Column(modifier = Modifier
                .size(Dimens.imageSize, Dimens.imageSize + Dimens.iconSize)
                .padding(Dimens.smallPadding)
                .clickable(
                    onClick = {
                        onPodcastClick(podcast.url)
                    }
                )) {
                GlideImage(
                    imageModel = podcast.scaledLogoUrl,
                    modifier = Modifier.size(Dimens.imageSize, Dimens.imageSize),
                    placeHolder = Icons.Rounded.Podcasts,
                    error = Icons.Rounded.Podcasts,
                    contentScale = ContentScale.Fit
                )
                Text(podcast.title, maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}
