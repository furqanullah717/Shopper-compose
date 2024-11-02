package com.codewithfk.shopper.feature

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.codewithfk.shopper.MainActivity
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

class LoginFlowTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @After
    fun tearDown() {
        composeTestRule.activity.deleteSharedPreferences("user")
    }
    @Test
    fun loginWithCorrectCredentials() {
        waitForLoginScreen()
        composeTestRule.onNodeWithTag("emailField").performTextInput("codewithfk@gmail.com")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("123456")
        composeTestRule.onNodeWithTag("loginButton").performClick()
        waitForHomeScreen()
    }

    @Test
    fun loginWithWrongCredentials() {
        waitForLoginScreen()
        composeTestRule.onNodeWithTag("emailField").performTextInput("wrongCredintials@gmail.com")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("123456")
        composeTestRule.onNodeWithTag("loginButton").performClick()
        waitForErrorScreen()
    }

    private fun waitForErrorScreen() {
        composeTestRule.waitUntil(timeoutMillis = 30000) {
            composeTestRule.onAllNodesWithTag("errorMsg", useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("errorMsg").assertIsDisplayed()
    }

    private fun waitForLoginScreen() {
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithTag("loginButton", useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun waitForHomeScreen() {
        composeTestRule.waitUntil(timeoutMillis = 30000) {
            composeTestRule.onAllNodesWithTag("homeScreen", useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("homeScreen").assertIsDisplayed()
    }

}