package com.codewithfk.shopper.ui.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import com.codewithfk.shopper.R
import com.codewithfk.shopper.navigation.LoginScreen

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = koinViewModel()
) {

    val uiState = viewModel.profileState
    val menuItems = listOf(
        Pair("Profile", R.drawable.ic_userprofile),
        Pair("Setting", R.drawable.ic_settings),
        Pair("Contact", R.drawable.ic_contact),
        Pair("Share App", R.drawable.ic_share),
        Pair("Help", R.drawable.ic_help)
    )

    ProfileScreenContent(
        uiState = uiState.value,
        onSignOutClicked = {
            viewModel.logout()
            navController.navigate(LoginScreen) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        },
        menuItems = menuItems
    )
}

@Composable
private fun ProfileScreenContent(
    uiState: UserProfile,
    menuItems: List<Pair<String, Int>>,
    onSignOutClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        UserInfoBar(
            userName = uiState.userName,
            userEmail = uiState.userEmail,
            userProfile = uiState.userProfile
        )

        Spacer(modifier = Modifier.height(32.dp))

        menuItems.forEach { menuItem ->
            ProfileMenuItem(
                text = menuItem.first,
                iconRes = menuItem.second,
                onClick = { }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Sign Out",
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF7043),
            modifier = Modifier
                .clickable { onSignOutClicked() }
                .padding(bottom = 32.dp)
        )
    }
}

@Composable
private fun UserInfoBar(
    userName: String,
    userEmail: String,
    userProfile: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = userProfile),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = userName,
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = userEmail,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            color = Color.Gray
        )
    }
}

@Composable
fun ProfileMenuItem(
    text: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF5F5F5))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )


        Icon(
            painter = painterResource(R.drawable.ic_profile_arrow) ,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewProfileScreen() {
    ProfileScreenContent(
        uiState = UserProfile(
            userName = "Mark Adam",
            userEmail = "Sunny_Koelpin45@hotmail.com"
        ),
        onSignOutClicked = {},
        menuItems = listOf(
            Pair("Profile", R.drawable.ic_userprofile),
            Pair("Setting", R.drawable.ic_settings),
            Pair("Contact", R.drawable.ic_contact),
            Pair("Share App", R.drawable.ic_share),
            Pair("Help", R.drawable.ic_help)
        )
    )
}