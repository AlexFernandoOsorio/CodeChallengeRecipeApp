package com.work.challengeyapeapp.domain.usecase

import com.work.challengeyapeapp.core.FlowResult
import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.repositories.RecipeRepository
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecipeFromApiUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    // Este caso de uso se encarga de obtener una lista de recetas(en este caso solo es un elemento) desde la API
    // El resultado de este caso de uso es un FlowResult que puede ser de 3 tipos: Loading, Success o Error
    // El parametro de entrada es el nombre de la receta
    operator fun invoke(param: String) = flow<FlowResult<List<RecipeModel>>> {
        emit(FlowResult.Loading())
        //llamamos al metodo getRecipeFromApi del repositorio
        val recipe = runCatching { recipeRepository.getRecipeFromApi(param) }
        recipe.onSuccess {
            //caso de exito
            //En este caso se retorna un flow con una lista de recetas pero con un solo elemento
            emit(FlowResult.Success(it))
        }.onFailure {
            //caso de error
            emit(FlowResult.Error(it.message.toString()))
        }
    }.flatMapConcat { result ->
        // En caso se obtenga una receta satisfacctoriamente
        // Encadenamos una nueva corrutina para insertar la receta en la base de datos local
        when (result) {
            is FlowResult.Success -> {
                val recipeList = result.data
                recipeList.let {
                    flow {
                        if (recipeList != null) {
                            //insertamos la receta en la base de datos local
                            recipeRepository.insertRecipeToLocal(recipeList[0])
                        }
                        emit(FlowResult.Success(recipeList))
                    }
                }
            }

            else -> {
                flow {
                    emit(result)
                }
            }
        }
    }
}
