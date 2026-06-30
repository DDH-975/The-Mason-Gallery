package com.example.masonlee

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeActionScreen() {
    var items by remember { mutableStateOf((1..15).map { "Message #$it" }) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            "Swipe to Action",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        
        LazyColumn {
            items(items, key = { it }) { item ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            items = items.filter { i -> i != item }
                            true
                        } else false
                    }
                )

                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                SwipeToDismissBoxValue.EndToStart -> Color.Red
                                else -> Color.LightGray
                            }, label = ""
                        )
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.White
                            )
                        }
                    }
                ) {
                    ListItem(
                        headlineContent = { Text(item) },
                        supportingContent = { Text("옆으로 밀어서 삭제해보세요") },
                        leadingContent = { Icon(Icons.Default.Email, null) },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                    )
                }
                HorizontalDivider()
            }
        }
    }
}
