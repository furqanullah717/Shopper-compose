package com.codewithfk.shopper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.codewithfk.domain.model.Product
import com.codewithfk.shopper.navigation.NavRoutes.CartScreen
import com.codewithfk.shopper.navigation.NavRoutes.HomeScreen
import com.codewithfk.shopper.navigation.ProductDetailNavType
import com.codewithfk.shopper.navigation.NavRoutes.ProductDetails
import com.codewithfk.shopper.navigation.NavRoutes.ProfileScreen
import com.codewithfk.shopper.ui.feature.cart.CartScreen
import com.codewithfk.shopper.ui.feature.home.HomeScreen
import com.codewithfk.shopper.ui.feature.product_details.ProductDetailsScreen
import com.codewithfk.shopper.ui.theme.ShopperTheme
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopperTheme {
                val showBottomNav = remember {
                    mutableStateOf(false)
                }
                val navController = rememberNavController()
                SharedTransitionLayout {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            AnimatedVisibility(visible = showBottomNav.value) {
                                BottomNavigationBar(navController)
                            }
                        }
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        ) {
                            NavHost(navController = navController, startDestination = HomeScreen) {
                                composable<HomeScreen> {
                                    HomeScreen(navController, this)
                                    showBottomNav.value = true
                                }
                                composable<CartScreen> {
                                    showBottomNav.value = true
                                    CartScreen(navController)
                                }
                                composable<ProfileScreen> {
                                    showBottomNav.value = true
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        Text(text = "Profile")
                                    }
                                }
                                composable<ProductDetails>(
                                    typeMap = mapOf(typeOf<Product>() to ProductDetailNavType),
                                ) {
                                    showBottomNav.value = false
                                    val arg = it.toRoute<ProductDetails>()
                                    ProductDetailsScreen(
                                        navController = navController,
                                        product = arg.product,
                                        animatedVisibilityScope = this
                                    )
                                }
                            }
                        }
                    }
                }


            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        //current route
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        val items = listOf(
            BottomNavItems.Home,
            BottomNavItems.Cart,
            BottomNavItems.Profile
        )

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute?.substringBefore("?") == item.route::class.qualifiedName,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { startRoute ->
                            popUpTo(startRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(text = item.title) },
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(if (currentRoute == item.route) MaterialTheme.colorScheme.primary else Color.Gray)
                    )
                }, colors = NavigationBarItemDefaults.colors().copy(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = Color.Gray,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}

sealed class BottomNavItems(val route: Any, val title: String, val icon: Int) {
    object Home : BottomNavItems(HomeScreen, "Home", icon = R.drawable.ic_home)
    object Cart : BottomNavItems(CartScreen, "Cart", icon = R.drawable.ic_cart)
    object Profile : BottomNavItems(ProfileScreen, "Profile", icon = R.drawable.ic_profile_bn)
}