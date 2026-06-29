package com.example.masonlee

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScrollAnimationScreen() {
    val items = remember { (1..20).toList() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Scroll-Driven Animation",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(items) { index, id ->
                AnimatedScrollItem(index, id)
            }
        }
    }
}

@Composable
fun AnimatedScrollItem(index: Int, id: Int) {
    var visible by remember { mutableStateOf(false) }
    
    // 컴포저블이 화면에 나타날 때 애니메이션 시작
    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { 100 },
            animationSpec = tween(durationMillis = 500, delayMillis = (index % 5) * 100)
        ) + fadeIn(animationSpec = tween(500))
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().height(100.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFF4081).copy(alpha = 0.1f)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = androidx.compose.ui.Alignment.CenterStart
            ) {
                Text(
                    text = "Animated Item #$id",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
