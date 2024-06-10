package com.work.challengeyapeapp.domain.usecase

import com.work.challengeyapeapp.core.FlowResult
import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.repositories.RecipeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecipeListUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    //En este caso de uso obtenemos una lista de recetas por letra
    operator fun invoke(param: String) = flow<FlowResult<List<RecipeModel>>> {
        emit(FlowResult.Loading())
        //llamamos al metodo getRecipeListFromApi del repositorio
        val recipeList = runCatching { recipeRepository.getRecipeListFromApi(param) }
        recipeList.onSuccess {
            //caso de exito
            emit(FlowResult.Success(it))
        }.onFailure {
            //caso de error
            emit(FlowResult.Error(it.message.toString()))
        }
    }
}
