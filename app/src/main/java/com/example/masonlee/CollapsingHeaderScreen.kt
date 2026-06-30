package com.example.masonlee

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CollapsingHeaderScreen() {
    val headerHeight = 200.dp
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    
    val scrollState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(top = headerHeight)
        ) {
            items(50) { index ->
                ListItem(
                    headlineContent = { Text("List Item $index") },
                    supportingContent = { Text("Secondary text for item $index") }
                )
                HorizontalDivider()
            }
        }

        // 스크롤 위치에 따라 헤더 이동 및 알파값 조절
        val alpha by remember {
            derivedStateOf {
                if (scrollState.firstVisibleItemIndex > 0) 0f 
                else 1f - (scrollState.firstVisibleItemScrollOffset.toFloat() / headerHeightPx).coerceIn(0f, 1f)
            }
        }
        
        val translationY by remember {
            derivedStateOf {
                if (scrollState.firstVisibleItemIndex > 0) -headerHeightPx 
                else -scrollState.firstVisibleItemScrollOffset.toFloat()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
                .background(MaterialTheme.colorScheme.primary)
                .graphicsLayer {
                    this.translationY = translationY
                },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Collapsing Header",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.graphicsLayer { this.alpha = alpha }
                )
                if (alpha > 0.5f) {
                    Text(
                        text = "스크롤하면 헤더가 사라집니다",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
