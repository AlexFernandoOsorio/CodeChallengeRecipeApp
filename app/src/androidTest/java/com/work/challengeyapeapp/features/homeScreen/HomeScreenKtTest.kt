package com.work.challengeyapeapp.features.homeScreen

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.work.challengeyapeapp.MainActivity
import com.work.challengeyapeapp.domain.model.RecipeModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeScreen_displaysCorrectly() {
        val viewModel: HomeScreenViewModel = mockk()

        coEvery { viewModel.getRecipesList("Test") }
        composeTestRule.setContent {
            HomeScreen(viewModel = viewModel, navController = mockk(relaxed = true))
        }

        composeTestRule.onNodeWithText("Test Recipe").assertExists()
        composeTestRule.onNodeWithText("Test Description").assertExists()
    }
}