package com.work.challengeyapeapp.domain.usecase

import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.repositories.RecipeRepository
import javax.inject.Inject

class InsertRecipeToDBUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    //Este caso de uso inserta una receta en la base de datos local
    suspend operator fun invoke(recipe: RecipeModel) {
        recipeRepository.insertRecipeToLocal(recipe)
    }
}
