package com.tamas.jablonkai.podcast.ui.screen.podcastdetails

import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.rounded.Podcasts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skydoves.landscapist.glide.GlideImage
import com.tamas.jablonkai.podcast.R
import com.tamas.jablonkai.podcast.data.model.Episode
import com.tamas.jablonkai.podcast.data.model.Podcast
import com.tamas.jablonkai.podcast.ui.theme.Dimens
import com.tamas.jablonkai.podcast.ui.widget.ErrorWidget
import com.tamas.jablonkai.podcast.ui.widget.LoadingWidget
import java.text.DateFormat
import java.util.*
import kotlin.random.Random.Default.nextLong

@Composable
fun PodcastDetailsScreen(
    viewModel: PodcastDetailsViewModel,
    onUpPress: () -> Unit
) {
    val viewState by viewModel.state.collectAsState()

    Scaffold(
        topBar = { AppBar(onUpPress) }) {
        when (viewState) {
            is PodcastDetailsState.Error -> ErrorWidget()
            is PodcastDetailsState.Loaded -> ContentLoaded(viewState as PodcastDetailsState.Loaded)
            is PodcastDetailsState.Loading -> LoadingWidget()
        }
    }
}

@Composable
private fun ContentLoaded(
    state: PodcastDetailsState.Loaded,
    modifier: Modifier = Modifier,
) {
    val podcast = state.podcast

    LazyColumn(
        modifier = modifier
            .padding(horizontal = Dimens.defaultPadding)
    ) {
        item {
            HeaderItem(podcast, modifier)
        }
        items(20) { i ->
            EpisodeListItem(createFakeEpisode(i))
        }
    }
}

@Composable
fun HeaderItem(
    podcast: Podcast,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.padding(vertical = Dimens.innerPadding)) {
        Column {
            Row {
                GlideImage(
                    imageModel = podcast.scaledLogoUrl,
                    modifier = modifier.size(Dimens.imageSize, Dimens.imageSize),
                    placeHolder = Icons.Rounded.Podcasts,
                    error = Icons.Rounded.Podcasts
                )
                Column(
                    modifier = modifier
                        .padding(start = Dimens.defaultPadding)
                ) {
                    Text(podcast.title, style = MaterialTheme.typography.h6)
                    Text(podcast.author, style = MaterialTheme.typography.body1)
                    Icon(
                        Icons.Rounded.Podcasts, contentDescription = null, modifier = modifier
                            .size(Dimens.iconSize, Dimens.iconSize)
                    )
                }
            }
            Text(
                podcast.description,
                maxLines = 3,
                modifier = modifier.padding(top = Dimens.innerPadding),
                style = MaterialTheme.typography.body2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun EpisodeListItem(
    episode: Episode,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier
            .padding(vertical = Dimens.smallPadding)
            .fillMaxWidth()
            .clickable(
                onClick = {}
            )
    ) {
        val (icon, title, pubDate, length, downloadIcon, divider) = createRefs()
        Icon(Icons.Rounded.Podcasts, contentDescription = null, modifier = modifier
            .size(Dimens.iconSize, Dimens.iconSize)
            .constrainAs(icon) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            })
        Text(episode.title, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = modifier
            .constrainAs(title) {
                top.linkTo(parent.top, margin = Dimens.smallPadding)
                start.linkTo(icon.end)
                end.linkTo(downloadIcon.start)
                width = Dimension.fillToConstraints
            }
            .padding(start = Dimens.innerPadding, end = Dimens.innerPadding))
        Text(
            DateFormat.getDateInstance().format(Date(episode.pubDate)), modifier = modifier
                .constrainAs(pubDate) {
                    top.linkTo(title.bottom)
                    start.linkTo(icon.end)
                    end.linkTo(downloadIcon.start)
                    width = Dimension.fillToConstraints
                }
                .padding(start = Dimens.innerPadding, end = Dimens.innerPadding))
        Text(DateUtils.formatElapsedTime(episode.length), modifier = modifier
            .constrainAs(length) {
                top.linkTo(pubDate.bottom)
                start.linkTo(icon.end)
                end.linkTo(downloadIcon.start)
                width = Dimension.fillToConstraints
            }
            .padding(start = Dimens.innerPadding, end = Dimens.innerPadding))
        Icon(
            Icons.Filled.Download,
            contentDescription = null,
            modifier = modifier.constrainAs(downloadIcon) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            })
        Divider(modifier = Modifier.constrainAs(divider) {
            end.linkTo(parent.end)
            start.linkTo(parent.start)
            top.linkTo(length.bottom)
            width = Dimension.fillToConstraints
        })
    }
}

@Composable
private fun AppBar(onUpPress: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { onUpPress() }) {
                Icon(Icons.Filled.ArrowBack, "")
            }
        },
        title = {
            Text(text = stringResource(id = R.string.details))
        }
    )
}

private fun createFakeEpisode(i: Int): Episode {
    val pubDate = Calendar.getInstance()
    pubDate.add(Calendar.WEEK_OF_YEAR, -i)
    return Episode(
        title = "episode $i",
        pubDate = pubDate.timeInMillis,
        length = nextLong(5000)
    )
}
