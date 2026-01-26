package com.codewithfk.shopper.ui.feature.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.shopper.R
import com.codewithfk.shopper.ShopperSession
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val shopperSession: ShopperSession
) : ViewModel() {

    val user = shopperSession.getUser()
    private val _profileState = mutableStateOf(UserProfile())
    val profileState: State<UserProfile> = _profileState

    private val _logoutState = mutableStateOf(false)

    init {
        getUserProfile()
    }

    private fun getUserProfile() {
        _profileState.value = UserProfile(
            userName = user?.name ?: "Guest User",
            userEmail = user?.email ?: "No email",
            userProfile = R.drawable.ic_profile
        )
    }

    fun logout() {
        viewModelScope.launch {
            shopperSession.deleteUser()
        }
    }
}

data class UserProfile(
    val userName: String = "",
    val userEmail: String = "",
    val userProfile: Int = 0
)