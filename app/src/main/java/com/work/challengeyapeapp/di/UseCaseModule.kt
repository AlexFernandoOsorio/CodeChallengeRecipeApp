package com.work.challengeyapeapp.di

import com.work.challengeyapeapp.domain.repositories.RecipeRepository
import com.work.challengeyapeapp.domain.usecase.GetRecipeFromApiUseCase
import com.work.challengeyapeapp.domain.usecase.GetRecipeListFromApiUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun provideGetRecipeListFromApiUseCase(repository: RecipeRepository): GetRecipeListFromApiUseCase {
        return GetRecipeListFromApiUseCase(repository)
    }

    @Provides
    fun provideGetRecipeFromApiUseCase(repository: RecipeRepository): GetRecipeFromApiUseCase {
        return GetRecipeFromApiUseCase(repository)
    }
}
