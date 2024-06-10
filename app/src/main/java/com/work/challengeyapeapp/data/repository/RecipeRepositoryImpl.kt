package com.work.challengeyapeapp.data.repository


import com.work.challengeyapeapp.data.local.dao.RecipeDao
import com.work.challengeyapeapp.data.mapper.toModel
import com.work.challengeyapeapp.data.mapper.toRecipeEntity
import com.work.challengeyapeapp.data.remote.services.RecipesAPi
import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.repositories.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipesApi: RecipesAPi,
    private val recipeDao: RecipeDao
) : RecipeRepository {

    //suspend fun para recuperar una lista de recetas de la api
    override suspend fun getRecipeListFromApi(name: String): List<RecipeModel> {
        return recipesApi.getRecipesListByLetter(name).toModel()
    }

    //suspend fun para recuperar una receta mediante id de la api
    override suspend fun getRecipeFromApi(id: String): List<RecipeModel> {
        return recipesApi.getRecipesById(id).toModel()
    }

    //suspend fun para insertar una receta a la base de datos local
    override suspend fun insertRecipeToLocal(recipe: RecipeModel) {
        recipeDao.insertRecipe(recipe.toRecipeEntity())
    }
}
