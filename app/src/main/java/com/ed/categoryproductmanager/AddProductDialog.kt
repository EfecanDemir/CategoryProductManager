package com.ed.sqlitecategoryexample

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.io.File
import java.io.IOException

@Composable
fun AddProductDialog(
    state: ProductState,
    onEvent: (ProductEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(ProductEvent.HideDialog)
        },
        title = { Text(text = "Add product") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                var expanded by remember { mutableStateOf(false) }
                val categoryDataList = mutableListOf<String>()
                try {
                    val file = File("savedList.txt")
                    file.bufferedReader().useLines { lines ->
                        lines.forEach { line ->
                            categoryDataList.add(line)
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                var selectedText by remember { mutableStateOf(state.category) }

                val icon = if (expanded)
                    Icons.Filled.KeyboardArrowUp
                else
                    Icons.Filled.KeyboardArrowDown


                Column() {
                    TextField(
                        value = selectedText,
                        onValueChange = {
                            onEvent(ProductEvent.SetProductCategoryName(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->

                            },
                        label = { Text("Categories") },
                        readOnly = true,
                        trailingIcon = {
                            Icon(icon, "contentDescription",
                                Modifier.clickable { expanded = !expanded })
                        }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categoryDataList.forEach { label ->
                            DropdownMenuItem(onClick = {
                                selectedText = label
                                onEvent(ProductEvent.SetProductCategoryName(label))
                                expanded = false
                            }) {
                                Text(text = label)
                            }
                        }
                    }
                }
                TextField(
                    value = state.name,
                    onValueChange = {
                        onEvent(ProductEvent.SetProductName(it))
                    },
                    placeholder = {
                        Text(text = "Product Name")
                    }
                )
                TextField(
                    value = state.stock,
                    onValueChange = {
                        onEvent(ProductEvent.SetProductStock(it))
                    },keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = {
                        Text(text = "Stock")
                    }
                )
                TextField(
                    value = state.price,
                    onValueChange = {
                        onEvent(ProductEvent.SetProductPrice(it))
                },keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = {
                        Text(text = "Price")
                    }
                )
            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    onEvent(ProductEvent.SaveProduct)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}