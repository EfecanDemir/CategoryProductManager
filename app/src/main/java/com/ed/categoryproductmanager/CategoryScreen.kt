package com.ed.sqlitecategoryexample

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File
import java.io.IOException

@Composable
fun CategoryScreen(
    state: CategoryState,
    onEvent: (CategoryEvent) -> Unit
) {
    val categoryList: MutableList<String> = mutableListOf()
    for (category in state.categories) {
        categoryList.add(category.categoryName)
    }
    val file = File("savedList.txt")
    try {
        val writer = file.bufferedWriter()
        categoryList.forEach { item ->
            writer.write(item)
            writer.newLine()
        }
        writer.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(CategoryEvent.ShowDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add product"
                )
            }
        },
    ) { _ ->
        if(state.isAddingCategory) {
            AddCategoryDialog(state = state, onEvent = onEvent)
        }
        Text(
            text = "All Categories",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(140.dp, 4.dp, 8.dp, 8.dp),
            fontWeight = FontWeight.Bold,
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                }
            }
            items(state.categories) { category ->
                Row(
                    modifier = Modifier.fillMaxWidth().background(color = Color.LightGray)
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Category Name:")
                                }
                                append(" ${category.categoryName}")},
                            fontSize = 20.sp
                        )
                    }
                    IconButton(onClick = {
                        onEvent(CategoryEvent.DeleteCategory(category))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete category"
                        )
                    }
                }
            }
        }
    }
}