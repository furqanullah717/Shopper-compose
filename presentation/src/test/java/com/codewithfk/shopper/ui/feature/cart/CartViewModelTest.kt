package com.codewithfk.shopper.ui.feature.cart

import com.codewithfk.domain.model.CartItemModel
import com.codewithfk.domain.model.CartModel
import com.codewithfk.domain.model.UserDomainModel
import com.codewithfk.domain.network.ResultWrapper
import com.codewithfk.domain.usecase.DeleteProductUseCase
import com.codewithfk.domain.usecase.GetCartUseCase
import com.codewithfk.domain.usecase.UpdateQuantityUseCase
import com.codewithfk.shopper.ShopperSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest : KoinTest {

    val cartUserCase: GetCartUseCase = mock()
    val updateQuantityUseCase: UpdateQuantityUseCase = mock()
    val deleteProductUseCase: DeleteProductUseCase = mock()
    val shopperSession: ShopperSession = mock()

    val classToTest: CartViewModel by inject()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            module {
                single { cartUserCase }
                single { updateQuantityUseCase }
                single { deleteProductUseCase }
                single { shopperSession }
                single { CartViewModel(get(), get(), get(), get()) }
            }
        )
    }

    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        whenever(shopperSession.getUser()).thenReturn(
            UserDomainModel(
                1,
                "test@test.com",
                "test@test.com",
                "Test User"
            )
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun whenIncrementQuantityCalledItShouldIncrementTheModel() = runTest {
        val cartItem = CartItemModel(1, 1, 1.0, "", 2, "Test Product")
        val expectedCartItem = CartItemModel(1, 1, 1.0, "", 3, "Test Product")
        whenever(updateQuantityUseCase.execute(any(), any())).thenReturn(
            ResultWrapper.Success(
                CartModel(listOf(cartItem), "Success")
            )
        )
        classToTest.incrementQuantity(cartItem)
        verify(updateQuantityUseCase).execute(expectedCartItem, 1)
    }

    @Test
    fun whenIncrementQuantityIs10ItShouldNotIncrementTheModel() = runTest {
        val cartItem = CartItemModel(1, 1, 1.0, "", 10, "Test Product")
        whenever(updateQuantityUseCase.execute(any(), any())).thenReturn(
            ResultWrapper.Success(
                CartModel(listOf(cartItem), "Success")
            )
        )
        classToTest.incrementQuantity(cartItem)
        verify(updateQuantityUseCase, never()).execute(any(), any())
    }

    @Test
    fun decrementQuantity() = runTest {
        val cartItem = CartItemModel(1, 1, 1.0, "", 2, "Test Product")
        val expectedCartItem = CartItemModel(1, 1, 1.0, "", 1, "Test Product")
        whenever(updateQuantityUseCase.execute(any(), any())).thenReturn(
            ResultWrapper.Success(
                CartModel(listOf(cartItem), "Success")
            )
        )
        classToTest.decrementQuantity(cartItem)
        verify(updateQuantityUseCase).execute(expectedCartItem, 1)
    }
}