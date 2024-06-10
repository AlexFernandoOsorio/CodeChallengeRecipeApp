package com.work.challengeyapeapp.domain.usecase

import com.work.challengeyapeapp.core.FlowResult
import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.repositories.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    // Este caso de uso se encarga de obtener informacion de una receta por ID
    suspend fun getRecipeFromAPI(param: String): Flow<FlowResult<List<RecipeModel>>> =
        flow<FlowResult<List<RecipeModel>>> {
            emit(FlowResult.Loading())
            //llamamos al metodo getRecipeFromApi del repositorio
            val recipe = runCatching { recipeRepository.getRecipeFromApi(param) }
            recipe.onSuccess {
                //caso de exito
                emit(FlowResult.Success(it))
            }.onFailure {
                //caso de error
                emit(FlowResult.Error(it.message.toString()))
            }
        }
}
