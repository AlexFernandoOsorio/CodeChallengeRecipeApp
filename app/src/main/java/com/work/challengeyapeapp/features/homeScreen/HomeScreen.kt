package com.work.challengeyapeapp.features.homeScreen

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.work.challengeyapeapp.features.navigation.AppScreens
import com.work.challengeyapeapp.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navController: NavController
) {

    val recipeName = viewModel.recipeName.collectAsState()
    val listState = rememberLazyListState()

    Scaffold(topBar = {
        HomeScreenTopBar()
    }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            SearchBox(searchText = recipeName.value, viewModel = viewModel)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.size_8)))
            SearchResultsList(viewModel, listState, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(
    searchText: String,
    viewModel: HomeScreenViewModel
) {
    TextField(
        value = searchText,
        onValueChange = { viewModel.setQuery(it) },
        label = { Text(text = stringResource(R.string.homeScreen_search)) },
        leadingIcon = {
            Icon(
                Icons.Outlined.Search,
                contentDescription = stringResource(R.string.homeScreen_content)
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
        ),
        modifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.size_12))
            .padding(top = dimensionResource(R.dimen.size_20))
            .heightIn(max = dimensionResource(R.dimen.size_80))
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen.size_12)),
        textStyle = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
fun SearchResultsList(
    viewModel: HomeScreenViewModel,
    listState: LazyListState,
    navController: NavController
) {

    val resultState = viewModel.recipeListState.value
    //si esta cargando mostramos el progressbar
    if (resultState.isLoading) {
        LoadingScreen()
    }
    //si hay un error mostramos el mensaje, este mensaje contiene el error de la llamada a la api
    if (resultState.error?.isNotBlank() == true) {
        ErrorScreen(resultState.error)
    }
    //si el homeState tiene datos mostramos la lista
    resultState.data?.let { recipeListData ->
        Modifier
            .padding(top = dimensionResource(R.dimen.size_18))
            .padding(horizontal = dimensionResource(R.dimen.size_18))
        LazyColumn(state = listState) {
            items(recipeListData.size) { index ->
                Card(
                    shape = RoundedCornerShape(dimensionResource(R.dimen.size_8)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = dimensionResource(R.dimen.size_2)
                    ),
                    modifier = Modifier
                        .padding(top = dimensionResource(R.dimen.size_18))
                        .padding(horizontal = dimensionResource(R.dimen.size_18))
                        .clickable(
                            onClick = {
                                navController.navigate(
                                    AppScreens.DetailScreen.route + "/${recipeListData[index].id}"
                                )
                            }
                        )
                )
                {
                    Row(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(recipeListData[index].image)
                                .crossfade(true)
                                .build(),
                            contentDescription = stringResource(R.string.homeScreen_content),
                            modifier = Modifier
                                .height(dimensionResource(id = R.dimen.size_110))
                                .width(dimensionResource(id = R.dimen.size_100)),
                            contentScale = ContentScale.Crop,
                        )
                        Column(
                            modifier = Modifier
                                .padding(start = dimensionResource(id = R.dimen.size_10))
                                .fillMaxHeight()
                                .align(CenterVertically),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = recipeListData[index].name,
                                color = Color.Black,
                                fontSize = dimensionResource(R.dimen.size_14_sp).value.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = recipeListData[index].category,
                                color = Color.Black,
                                fontSize = dimensionResource(R.dimen.size_14_sp).value.sp
                            )
                            Spacer(Modifier.height(10.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(R.string.homeScreen_recipe_label) + recipeListData[index].id,
                                    color = Color.Black,
                                    fontSize = dimensionResource(R.dimen.size_14_sp).value.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.homeScreen_title),
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.size_40)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(error: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.size_60)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = error)
    }
}
