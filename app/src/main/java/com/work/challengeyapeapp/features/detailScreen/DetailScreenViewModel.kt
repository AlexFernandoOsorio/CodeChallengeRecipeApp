package com.work.challengeyapeapp.features.detailScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.work.challengeyapeapp.core.FlowResult
import com.work.challengeyapeapp.domain.usecase.GetRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // inicializamos el estado DetailState
    private val _recipeState = mutableStateOf(DetailScreenStateHolder())
    val recipeState: State<DetailScreenStateHolder> get() = _recipeState

    // llamamos a la funcion getRecipeDetail en la inicializacion del viewmodel/caso inicial
    init {
        savedStateHandle.getLiveData<String>("id").observeForever {
            it?.let {
                viewModelScope.launch {
                    getRecipeDetail(it)
                }
            }
        }
    }

    // Funcion para obtener la receta
    suspend fun getRecipeDetail(id: String) {
        getRecipeUseCase(id).onEach {
            when (it) {
                is FlowResult.Loading -> {
                    //si esta cargando
                    _recipeState.value = DetailScreenStateHolder(isLoading = true)
                }

                is FlowResult.Success -> {
                    //si es exitoso, al estado le seteamos la data del resultado
                    _recipeState.value = DetailScreenStateHolder(data = it.data)
                }

                is FlowResult.Error -> {
                    //Si hay un error, al estado le seteamos el error
                    _recipeState.value = DetailScreenStateHolder(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }
}
