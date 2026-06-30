package com.example.masonlee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ParallaxScreen() {
    val scrollState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        // 배경 레이어 (패럴랙스 효과)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .graphicsLayer {
                    // 스크롤 속도의 절반으로 이동하여 깊이감 생성
                    translationY = -scrollState.firstVisibleItemScrollOffset.toFloat() * 0.5f
                }
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF1A237E), Color(0xFF3949AB))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "PARALLAX",
                    color = Color.White.copy(alpha = 0.3f),
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        // 리스트 레이어
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Spacer(modifier = Modifier.height(300.dp))
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                        )
                        .padding(24.dp)
                ) {
                    Column {
                        Text(
                            "컨텐츠 영역",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "배경보다 리스트가 더 빨리 움직이면서 입체감을 줍니다. " +
                                    "디자인 중심의 앱에서 많이 사용하는 효과입니다.",
                            color = Color.Gray
                        )
                    }
                }
            }
            items(20) { index ->
                ListItem(
                    modifier = Modifier.background(Color.White),
                    headlineContent = { Text("Information Item $index") }
                )
                HorizontalDivider()
            }
        }
    }
}
