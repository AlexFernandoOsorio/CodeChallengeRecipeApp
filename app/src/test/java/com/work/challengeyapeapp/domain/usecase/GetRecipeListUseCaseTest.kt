package com.work.challengeyapeapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.work.challengeyapeapp.core.FlowResult
import com.work.challengeyapeapp.coroutineRule.CoroutineTestRule
import com.work.challengeyapeapp.domain.model.RecipeModel
import com.work.challengeyapeapp.domain.repositories.RecipeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GetRecipeListUseCaseTest {

    @MockK
    private lateinit var recipeRepository: RecipeRepository

    private lateinit var getRecipeListUseCase: GetRecipeListUseCase

    private val expected = listOf(
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


    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getRecipeListUseCase = GetRecipeListUseCase(recipeRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given I obtain recipe list from API, When I try to obtain it on use case, Then I verify if was called on repository`() =
        coroutineTestRule.runBlockingTest {
            // Given
            coEvery { recipeRepository.getRecipeListFromApi(any()) } returns expected

            // When
            val value = mutableListOf<FlowResult<List<RecipeModel>>>()
            getRecipeListUseCase.getRecipeListFromAPI("pork").collect {
                value.add(it)
            }

            // Then
            coVerify { recipeRepository.getRecipeListFromApi("pork") }

        }
}
