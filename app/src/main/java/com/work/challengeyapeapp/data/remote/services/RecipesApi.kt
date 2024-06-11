package com.work.challengeyapeapp.data.remote.services

import com.work.challengeyapeapp.BuildConfig
import com.work.challengeyapeapp.data.remote.response.RecipeListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesAPi {

    //Lista de una receta por id{numero de la receta}
    @GET(BuildConfig.END_POINT_RECIPE_ID)
    suspend fun getRecipesById(@Query("i") recipeId: String): RecipeListResponse

    //Lista de recetas por nombre{nombre de la receta}
    @GET(BuildConfig.END_POINT_RECIPE_NAME)
    suspend fun getRecipesListByLetter(@Query("s") recipeName: String): RecipeListResponse

}
