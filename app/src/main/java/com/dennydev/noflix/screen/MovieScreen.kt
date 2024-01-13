package com.dennydev.noflix.screen

import android.content.Context
import android.content.pm.ActivityInfo
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.isVisible
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dennydev.noflix.components.shimmerEffect
import com.dennydev.noflix.model.common.ApiResponse
import com.dennydev.noflix.viewmodel.MovieViewModel

@androidx.annotation.OptIn(UnstableApi::class) @OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    navController: NavHostController,
    movieViewModel: MovieViewModel = hiltViewModel(),
    idMovie: String
) {
    val movie by movieViewModel.movie
    val lifecycleOwner = LocalLifecycleOwner.current
    var lifeCycle by remember{
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    val context = LocalContext.current

    var fullScreenMode by remember{ mutableStateOf(false) }

    LaunchedEffect(key1 = movie){
        movie.data?.data?.let {
            movieViewModel.playMovie(Uri.parse(it.url))
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if(event==Lifecycle.Event.ON_CREATE){
                movieViewModel.movie(idMovie)
            }
            lifeCycle = event
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    BackHandler {
        if(fullScreenMode) {
            val activity = context as ComponentActivity
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }else{
            navController.popBackStack()
        }
    }

    Scaffold(modifier = Modifier
        .fillMaxSize(), topBar = {
            if(!fullScreenMode) {
                CenterAlignedTopAppBar(title = {
                    Text(text = "Movie")
                }, navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "back")
                    }
                })
            }
    }) { values ->
        LazyColumn(modifier= Modifier
            .fillMaxSize()
            .padding(values)){

            if(movie is ApiResponse.Loading){
                item {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .background(shimmerEffect())
                    )
                }

                item {
                    Row(modifier=Modifier.padding(vertical=18.dp, horizontal=12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(110.dp)
                                .background(shimmerEffect())
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column() {
                                Box(
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(24.dp)
                                        .background(
                                            shimmerEffect()
                                        )
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Box(
                                    modifier = Modifier
                                        .width(48.dp)
                                        .height(16.dp)
                                        .background(
                                            shimmerEffect()
                                        )
                                )
                            }
                    }
                }

                item{
                    Column(
                        modifier=Modifier.padding(12.dp)
                    ) {
                        Box(modifier= Modifier
                            .width(60.dp)
                            .height(24.dp)
                            .background(shimmerEffect()))
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(modifier= Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .background(shimmerEffect()))
                        Spacer(modifier = Modifier
                            .height(4.dp))
                        Box(modifier= Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .background(shimmerEffect()))
                        Spacer(modifier = Modifier
                            .height(4.dp))
                        Box(modifier= Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .background(shimmerEffect()))
                        Spacer(modifier = Modifier
                            .height(4.dp))
                    }
                }
            }

            movie.data?.data?.let {
                item {
                    AndroidView(factory = {
                        PlayerView(it).apply {
                            player = movieViewModel.getPlayer()
                            useController = true
                            setShowNextButton(false)
                            setShowPreviousButton(false)
                            setShowFastForwardButton(false)
                            setShowRewindButton(false)
                            setShowSubtitleButton(false)
                            setFullscreenButtonClickListener { fullScreen ->
                                fullScreenMode = fullScreen
                                with(context) {
                                    val activity = this as ComponentActivity
                                    activity.requestedOrientation =
                                        if (fullScreen) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                                }
                            }
                            val playerBar =
                                rootView.findViewById<View>(androidx.media3.ui.R.id.exo_controls_background)
                            val bottomBar =
                                rootView.findViewById<View>(androidx.media3.ui.R.id.exo_bottom_bar)
                            bottomBar.background.alpha = 100
                            playerBar.background.alpha = 60
                        }
                    }, update = {
                        when (lifeCycle) {
                            Lifecycle.Event.ON_PAUSE -> {
                                it.player?.pause()
                            }

                            else -> Unit
                        }
                    }, modifier = if (fullScreenMode) {
                        Modifier
                            .fillParentMaxSize()
                    } else {
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                    }
                    )
                }

                if(!fullScreenMode) {

                    item {
                        Row(
                            modifier = Modifier.padding(vertical = 18.dp, horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = it.thumbnail),
                                contentDescription = "Image",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(110.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column() {
                                Text(it.title, style = MaterialTheme.typography.titleLarge)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(it.release_year, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }

                    item {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text("Description:", style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(it.description, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

fun Context.requestOrientation(isFullScreenBoolean: Boolean){

}