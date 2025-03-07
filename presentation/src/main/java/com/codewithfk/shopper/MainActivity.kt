package com.codewithfk.shopper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.codewithfk.shopper.model.UiProductModel
import com.codewithfk.shopper.navigation.AllProductsScreen
import com.codewithfk.shopper.navigation.CartScreen
import com.codewithfk.shopper.navigation.CartSummaryScreen
import com.codewithfk.shopper.navigation.HomeScreen
import com.codewithfk.shopper.navigation.LoginScreen
import com.codewithfk.shopper.navigation.OrdersScreen
import com.codewithfk.shopper.navigation.ProductDetails
import com.codewithfk.shopper.navigation.ProfileScreen
import com.codewithfk.shopper.navigation.RegisterScreen
import com.codewithfk.shopper.navigation.UserAddressRoute
import com.codewithfk.shopper.navigation.UserAddressRouteWrapper
import com.codewithfk.shopper.navigation.productNavType
import com.codewithfk.shopper.navigation.userAddressNavType
import com.codewithfk.shopper.ui.feature.account.login.LoginScreen
import com.codewithfk.shopper.ui.feature.account.register.RegisterScreen
import com.codewithfk.shopper.ui.feature.all_products.AllProductsScreen
import com.codewithfk.shopper.ui.feature.cart.CartScreen
import com.codewithfk.shopper.ui.feature.home.HomeScreen
import com.codewithfk.shopper.ui.feature.orders.OrdersScreen
import com.codewithfk.shopper.ui.feature.product_details.ProductDetailsScreen
import com.codewithfk.shopper.ui.feature.summary.CartSummaryScreen
import com.codewithfk.shopper.ui.feature.user_address.UserAddressScreen
import com.codewithfk.shopper.ui.theme.ShopperTheme
import org.koin.android.ext.android.inject
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val shopperSession : ShopperSession by inject()
            val shouldShowFab = remember{
                mutableStateOf(true)
            }
            ShopperTheme {
                val shouldShowBottomNav = remember {
                    mutableStateOf(true)
                }
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AnimatedVisibility(visible = shouldShowBottomNav.value, enter = fadeIn()) {
                            BottomNavigationBar(navController)
                        }

                    },
                    floatingActionButton = {
                        AnimatedVisibility(visible = shouldShowFab.value, enter = fadeIn(), exit = fadeOut()) {
                            FloatingActionButton(
                                onClick = {
                                    navController.navigate(CartScreen)
                                },
                                shape = CircleShape,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_cart),
                                    contentDescription = null,
                                )
                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {

                        NavHost(
                            navController = navController,
                            startDestination = if (shopperSession.getUser() != null) {
                                HomeScreen
                            } else {
                                LoginScreen
                            }
                        ) {

                            composable<LoginScreen> {
                                shouldShowBottomNav.value = false
                                shouldShowFab.value = false
                                LoginScreen(navController)
                            }
                            composable<RegisterScreen> {
                                shouldShowBottomNav.value = false
                                shouldShowFab.value = false
                                RegisterScreen(navController)
                            }
                            composable<HomeScreen> {
                                HomeScreen(navController)
                                shouldShowFab.value = true
                                shouldShowBottomNav.value = true
                            }
                            composable<AllProductsScreen>{
                                AllProductsScreen(navController)
                                shouldShowBottomNav.value = false
                                shouldShowFab.value = true
                            }
                            composable<CartScreen> {
                                shouldShowBottomNav.value = false
                                shouldShowFab.value = false
                                CartScreen(navController)
                            }
                            composable<OrdersScreen> {
                                shouldShowFab.value = false
                                shouldShowBottomNav.value = true
                                OrdersScreen()
                            }
                            composable<ProfileScreen> {
                                shouldShowBottomNav.value = true
                                shouldShowFab.value = false
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Text(text = "Profile")
                                }
                            }
                            composable<CartSummaryScreen> {
                                shouldShowBottomNav.value = false
                                shouldShowFab.value = false
                                CartSummaryScreen(navController = navController)
                            }
                            composable<ProductDetails>(
                                typeMap = mapOf(typeOf<UiProductModel>() to productNavType)
                            ) {
                                shouldShowBottomNav.value = false
                                shouldShowFab.value = false
                                val productRoute = it.toRoute<ProductDetails>()
                                ProductDetailsScreen(navController, productRoute.product)
                            }
                            composable<UserAddressRoute>(
                                typeMap = mapOf(typeOf<UserAddressRouteWrapper>() to userAddressNavType)
                            ) {
                                shouldShowBottomNav.value = false
                                shouldShowFab.value = false
                                val userAddressRoute = it.toRoute<UserAddressRoute>()
                                UserAddressScreen(
                                    navController = navController,
                                    userAddress = userAddressRoute.userAddressWrapper.userAddress
                                )
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
            BottomNavItems.Orders,
            BottomNavItems.Profile
        )

        items.forEach { item ->
            val isSelected = currentRoute?.substringBefore("?") == item.route::class.qualifiedName
            NavigationBarItem(
                selected = isSelected,
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
                        colorFilter = ColorFilter.tint(if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray)
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
    object Orders : BottomNavItems(OrdersScreen, "Orders", icon = R.drawable.ic_orders)
    object Profile : BottomNavItems(ProfileScreen, "Profile", icon = R.drawable.ic_profile_bn)
}