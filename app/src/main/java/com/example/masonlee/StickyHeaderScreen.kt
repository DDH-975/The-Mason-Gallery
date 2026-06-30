package com.example.masonlee

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StickyHeaderScreen() {
    val grouped = (1..10).associateWith { group ->
        (1..5).map { item -> "Group $group - Item $item" }
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        grouped.forEach { (group, items) ->
            stickyHeader {
                Text(
                    text = "Category $group",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5))
                        .padding(16.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
            }

            items(items) { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = item, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = Color.LightGray)
                }
            }
        }
    }
}
