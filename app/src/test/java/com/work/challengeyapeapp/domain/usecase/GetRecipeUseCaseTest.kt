package com.work.challengeyapeapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.work.challengeyapeapp.core.FlowResult
import com.work.challengeyapeapp.coroutineRule.CoroutineTestRule
import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.repositories.RecipeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class GetRecipeUseCaseTest {

    @RelaxedMockK
    private lateinit var recipeRepository: RecipeRepository
    private lateinit var getRecipeUseCase: GetRecipeUseCase

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getRecipeUseCase = GetRecipeUseCase(recipeRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when invoke is called then verify it was called on RecipeRepository`() =
        coroutineTestRule.runBlockingTest {
            // Given
            val param = "1"
            val expected = listOf(
                RecipeModel(
                    "1",
                    "Pork",
                    "Pork description",
                    "Pork image",
                    "Pork type",
                    emptyList(),
                    "Pork ingredients"
                )
            )
            coEvery { recipeRepository.getRecipeFromApi(param) } returns expected

            // When
            val value = mutableListOf<FlowResult<List<RecipeModel>>>()
            getRecipeUseCase.getRecipeFromAPI(param).collect {
                value.add(it)
            }

            // Then
            coVerify { recipeRepository.getRecipeFromApi(param) }

        }
}
