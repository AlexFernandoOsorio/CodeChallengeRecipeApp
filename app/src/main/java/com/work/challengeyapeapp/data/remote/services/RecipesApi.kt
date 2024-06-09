package com.work.challengeyapeapp.data.remote.services

import com.work.challengeyapeapp.data.remote.response.RecipeListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesAPi {

    //Lista de una receta por id{numero de la receta}
    @GET("lookup.php")
    suspend fun getRecipesById(@Query("i") recipeId: String): RecipeListResponse

    //Lista de recetas por nombre{nombre de la receta}
    @GET("search.php")
    suspend fun getRecipesByName(@Query("s") recipeName: String): RecipeListResponse

}
