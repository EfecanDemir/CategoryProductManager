package com.ed.categoryproductmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.ed.categoryproductmanager.ui.theme.CategoryProductManagerTheme
import com.ed.sqlitecategoryexample.ProductDatabase
import com.ed.sqlitecategoryexample.ProductScreen
import com.ed.sqlitecategoryexample.ProductViewModel

class ProductActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ProductDatabase::class.java,
            "products.db"
        ).build()
    }
    private val viewModel by viewModels<ProductViewModel>(
        factoryProducer = {
            object: ViewModelProvider.Factory{
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return ProductViewModel(db.dao) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CategoryProductManagerTheme {
                val state by viewModel.state.collectAsState()
                ProductScreen(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}

