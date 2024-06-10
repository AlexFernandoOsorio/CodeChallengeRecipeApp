package com.work.challengeyapeapp.features.homeScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.work.challengeyapeapp.core.FlowResult
import com.work.challengeyapeapp.core.GlobalConstants
import com.work.challengeyapeapp.domain.usecase.GetRecipeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase
) : ViewModel() {

    //inicializamos el estado
    private val _recipeListState = mutableStateOf(HomeScreenStateHolder())
    val recipeListState: State<HomeScreenStateHolder> get() = _recipeListState

    //inicializamos el estado
    private val _recipeName: MutableStateFlow<String> = MutableStateFlow("")
    val recipeName: StateFlow<String> get() = _recipeName

    //funcion para setear el query
    fun setQuery(param: String) {
        _recipeName.value = param
    }

    //llamamos a la funcion getRecipesList en la inicializacion del viewmodel/caso inicial
    init {
        viewModelScope.launch {
            _recipeName.debounce(GlobalConstants.TIME_OUT).collectLatest {
                getRecipesList(it)
            }
        }
    }

    // funcion para obtener la lista de recetas
    suspend fun getRecipesList(name: String) {
        getRecipeListUseCase(name).onEach {
            when (it) {
                is FlowResult.Loading -> {
                    //si esta cargando
                    _recipeListState.value = HomeScreenStateHolder(isLoading = true)
                }

                is FlowResult.Success -> {
                    //si es exitoso, al estado le seteamos la data del resultado
                    _recipeListState.value = HomeScreenStateHolder(data = it.data)
                }

                is FlowResult.Error -> {
                    //Si hay un error, al estado le seteamos el error
                    _recipeListState.value = HomeScreenStateHolder(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }
}
