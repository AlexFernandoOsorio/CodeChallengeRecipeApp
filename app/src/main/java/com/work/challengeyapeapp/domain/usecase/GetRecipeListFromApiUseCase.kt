package com.work.challengeyapeapp.domain.usecase

import com.work.challengeyapeapp.core.FlowResult
import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.repositories.RecipeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecipeListFromApiUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    //En este caso de uso obtenemos una lista de recetas(en este caso es una lista con tamano grande) desde la API
    //El resultado de este caso de uso es un FlowResult que puede ser de 3 tipos: Loading, Success o Error
    //El parametro de entrada es el nombre de la receta
    operator fun invoke(param: String) = flow<FlowResult<List<RecipeModel>>> {
        emit(FlowResult.Loading())
        //llamamos al metodo getRecipeListFromApi del repositorio
        val recipeList = runCatching { recipeRepository.getRecipeListFromApi(param) }
        recipeList.onSuccess {
            //caso de exito
            // en este caso se retorna un flow con una lista de recetas
            emit(FlowResult.Success(it))
        }.onFailure {
            //caso de error
            emit(FlowResult.Error(it.message.toString()))
        }
    }
}
