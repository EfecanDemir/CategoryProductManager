package com.ed.categoryproductmanager

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.ed.categoryproductmanager.ui.theme.CategoryProductManagerTheme
import com.ed.sqlitecategoryexample.ProductDatabase
import com.ed.sqlitecategoryexample.ProductScreen
import com.ed.sqlitecategoryexample.ProductViewModel
import java.util.jar.Manifest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CategoryProductManagerTheme {
                MyAppContent()
            }
        }
    }
}

@Composable
fun MyAppContent(){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Category Product Manager App",
            fontSize = 24.sp,
            color = Black,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )
        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                val productScreen = Intent(context, ProductActivity::class.java)
                context.startActivity(productScreen)
            }
        ) {
            Text(text = "Add Product / View Product List")
        }

        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                val categoryScreen = Intent(context, CategoryActivity::class.java)
                context.startActivity(categoryScreen)
            }
        ) {
            Text(text = "Add Category / View Category List")
        }
    }
}