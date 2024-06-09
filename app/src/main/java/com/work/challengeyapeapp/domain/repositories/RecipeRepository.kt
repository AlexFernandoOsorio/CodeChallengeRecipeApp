package com.work.challengeyapeapp.domain.repositories

import com.work.challengeyapeapp.domain.model.RecipeModel

interface RecipeRepository {

    suspend fun getRecipeListFromApi(name: String): List<RecipeModel>

    suspend fun getRecipeFromApi(id: String): List<RecipeModel>

    suspend fun insertRecipeToLocal(recipe: RecipeModel)
}
