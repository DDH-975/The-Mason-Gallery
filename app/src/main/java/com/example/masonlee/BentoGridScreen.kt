package com.example.masonlee

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BentoGridScreen() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Large Header Card
        item(span = { GridItemSpan(4) }) {
            BentoCard(
                title = "Statistics",
                color = Color(0xFF6366F1),
                modifier = Modifier.height(160.dp)
            ) {
                AnimatedCounter(targetValue = 1254, label = "Active Users")
            }
        }

        // Medium Square Cards
        item(span = { GridItemSpan(2) }) {
            BentoCard(title = "Health", color = Color(0xFFEC4899), modifier = Modifier.height(180.dp)) {
                Icon(Icons.Default.Favorite, null, tint = Color.White, modifier = Modifier.size(48.dp))
            }
        }
        item(span = { GridItemSpan(2) }) {
            BentoCard(title = "Weather", color = Color(0xFF3B82F6), modifier = Modifier.height(180.dp)) {
                Text("24°C", color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Bold)
                Text("Mostly Sunny", color = Color.White.copy(alpha = 0.7f))
            }
        }

        // Small Square Cards
        item(span = { GridItemSpan(1) }) {
            BentoCard(title = "Music", color = Color(0xFFF59E0B), modifier = Modifier.height(84.dp)) {
                Icon(Icons.Default.PlayArrow, null, tint = Color.White)
            }
        }
        item(span = { GridItemSpan(1) }) {
            BentoCard(title = "WiFi", color = Color(0xFF10B981), modifier = Modifier.height(84.dp)) {
                Icon(Icons.Default.Build, null, tint = Color.White)
            }
        }
        
        // Tall Vertical Card
        item(span = { GridItemSpan(2) }) {
            BentoCard(title = "Notifications", color = Color(0xFF8B5CF6), modifier = Modifier.height(180.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(3) { 
                        Box(Modifier.fillMaxWidth().height(20.dp).background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(4.dp)))
                    }
                }
            }
        }
    }
}

@Composable
fun BentoCard(
    title: String,
    color: Color,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(color)
            .padding(16.dp)
    ) {
        Column {
            Text(title, color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun AnimatedCounter(targetValue: Int, label: String) {
    var count by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        val animation = Animatable(0f)
        animation.animateTo(
            targetValue.toFloat(),
            animationSpec = tween(2000, easing = FastOutSlowInEasing)
        ) {
            count = value.toInt()
        }
    }
    Column {
        Text("$count", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Black)
        Text(label, color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
    }
}
