package com.example.masonlee

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GlassmorphismScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAnim by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "glow"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A)) // Deep Dark Background
    ) {
        // Neon Background Blur Circles
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-50).dp, y = 100.dp)
                .blur(80.dp)
                .background(Color(0xFF8B5CF6).copy(alpha = 0.3f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 50.dp, y = (-50).dp)
                .blur(80.dp)
                .background(Color(0xFFEC4899).copy(alpha = 0.3f), CircleShape)
        )

        // Glass Player Card
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
                .fillMaxWidth()
                .height(450.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White.copy(alpha = 0.1f))
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        listOf(Color.White.copy(alpha = 0.3f), Color.Transparent)
                    ),
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Album Art Placeholder
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.linearGradient(listOf(Color(0xFFEC4899), Color(0xFF8B5CF6)))
                        )
                        .drawBehind {
                            // Neon Glow Effect
                            drawCircle(
                                color = Color(0xFFEC4899).copy(alpha = 0.2f * glowAnim),
                                radius = size.minDimension * 0.8f * glowAnim
                            )
                        }
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Neon Nights", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("Cyberpunk Dreams", color = Color.White.copy(alpha = 0.6f), fontSize = 16.sp)
                }

                Slider(
                    value = 0.4f,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color(0xFFEC4899),
                        inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.FastRewind, null, tint = Color.White, modifier = Modifier.size(32.dp))
                    }
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color.White.copy(alpha = 0.15f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.PlayArrow, null, tint = Color.White, modifier = Modifier.size(40.dp))
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.FastForward, null, tint = Color.White, modifier = Modifier.size(32.dp))
                    }
                }
            }
        }
    }
}
