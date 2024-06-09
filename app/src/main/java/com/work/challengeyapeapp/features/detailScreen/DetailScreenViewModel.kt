package com.work.challengeyapeapp.features.detailScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.work.challengeyapeapp.core.FlowResult
import com.work.challengeyapeapp.domain.usecase.GetRecipeFromApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getRecipeFromApiUseCase: GetRecipeFromApiUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // inicializamos el estado DetailState
    private val _recipeState = mutableStateOf(DetailScreenStateHolder())
    val recipeState: State<DetailScreenStateHolder> get() = _recipeState

    // llamamos a la funcion getRecipeDetail en la inicializacion del viewmodel/caso inicial
    init {
        savedStateHandle.getLiveData<String>("id").observeForever {
            it?.let {
                getRecipeDetail(it)
            }
        }
    }

    // Funcion para obtener la receta
    // llamamos al caso de uso getRecipeFromApiUseCase y nos retornara un FlowResult
    // que puede ser de tipo Loading, Success o Error
    private fun getRecipeDetail(id: String) {
        getRecipeFromApiUseCase(id).onEach {
            when (it) {
                is FlowResult.Loading -> {
                    //si esta cargando
                    _recipeState.value = DetailScreenStateHolder(isLoading = true)
                }

                is FlowResult.Success -> {
                    //si es exitoso, al estado le seteamos la data del resultado
                    //En este caso el resultado es una lista con un solo elemeto
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
