package com.work.challengeyapeapp.features.detailScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.work.challengeyapeapp.core.GlobalConstants
import com.work.challengeyapeapp.features.navigation.AppScreens
import com.work.challengeyapeapp.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    id: String,
    viewModel: DetailScreenViewModel,
    navController: NavController
) {

    val resultState = viewModel.recipeState.value

    Scaffold(
        topBar = {
            DetailScreenTopBar(navController)
        }
    ) {
        //si el estado es loading mostramos un progressbar
        if (resultState.isLoading) {
            LoadingScreen()
        }
        //si el estado es error mostramos un mensaje de error
        if (resultState.error.isNotBlank()) {
            ErrorScreen(id)
        }
        //si el estado es success mostramos los datos
        resultState.data?.let { recipeList ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentPadding = PaddingValues(
                    horizontal = dimensionResource(R.dimen.size_18),
                    vertical = dimensionResource(R.dimen.size_18)
                ),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.size_18))
            ) {
                item {
                    Image(
                        painter = rememberAsyncImagePainter(recipeList[0].image),
                        contentDescription = stringResource(R.string.title_detail),
                        modifier = Modifier
                            .fillMaxSize()
                            .size(dimensionResource(R.dimen.size_250)),
                        contentScale = ContentScale.Crop
                    )
                }

                item {
                    Text(
                        text = recipeList[0].name,
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                        color = colorResource(R.color.purple_500),
                        textAlign = TextAlign.Center,
                        maxLines = GlobalConstants.MAX_LINES,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.detail_category) + recipeList[0].category,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        maxLines = GlobalConstants.MAX_LINES,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.detailScreen_Description),
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                        color = colorResource(R.color.purple_500),
                        maxLines = GlobalConstants.MAX_LINES,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Text(
                        text = recipeList[0].description,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    //mostramos el boton para ir al mapa
                    Button(
                        onClick = {
                            navController.navigate(
                                AppScreens.MapScreen.route + "/${recipeList[0].location}"
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(dimensionResource(R.dimen.size_8)),
                        enabled = !resultState.isLoading
                    ) {
                        Text(
                            text = stringResource(R.string.detail_map),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.size_6))
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = dimensionResource(R.dimen.size_100)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenTopBar(navController: NavController) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.title_detail), color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
            }
        }
    )
}

@Composable
fun ErrorScreen(id: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = dimensionResource(R.dimen.size_60)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = GlobalConstants.WITH_OUT_SEARCH_RECIPE + id
        )
    }
}
