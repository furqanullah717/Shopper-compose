package com.codewithfk.shopper.ui.feature.product_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.codewithfk.domain.model.Product
import com.codewithfk.shopper.R
import com.codewithfk.shopper.ui.theme.ShopperTheme

@Composable
fun ProductDetailsScreen(navController: NavController, product: Product) {

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color = Color.LightGray.copy(alpha = 0.3f))
                    .padding(8.dp)
                    .align(alignment = androidx.compose.ui.Alignment.TopStart),
                contentScale = ContentScale.Inside
            )
            Image(
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 16.dp, end = 16.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color = Color.LightGray.copy(alpha = 0.3f))
                    .padding(8.dp)
                    .align(alignment = androidx.compose.ui.Alignment.TopEnd),
                contentScale = ContentScale.Inside
            )
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    modifier = Modifier.weight(1f),
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Row(
                modifier = Modifier.padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.ic_star), contentDescription = null)
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "4.5",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "(10 Reviews)",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 16.dp),
                color = Color.Gray,
                minLines = 4,
                maxLines = 8
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = "Size",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 16.dp)
            )
            Row(modifier = Modifier.padding(start = 8.dp)) {
                for (i in 1..3) {
                    SelectionType(title = " $i ")
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }

            Spacer(modifier = Modifier.size(16.dp))
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                Button(onClick = { /*TODO*/ }, modifier = Modifier.weight(0.75f)) {
                    Text(text = "Buy Now")
                }
                Spacer(modifier = Modifier.size(8.dp))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(0.25f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.3f))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cart),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun SelectionType(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .padding(start = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ProductDetailsScreenPreview() {
    ShopperTheme {
        ProductDetailsScreen(
            navController = rememberNavController(), product = Product(
                id = 1,
                title = "Product Title",
                description = "Product Description lorem ipsum Contains a lot of text and information about the product. and it is a long description." + "Product Description lorem ipsum Contains a lot of text and information about the product. and it is a long description." + "Product Description lorem ipsum Contains a lot of text and information about the product. and it is a long description.",
                price = 100.0,
                image = "https://picsum.photos/200/300",
                category = "electronics",
            )
        )
    }
}