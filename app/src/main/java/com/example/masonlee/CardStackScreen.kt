package com.example.masonlee

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

data class Person(val id: Int, val name: String, val color: Color)

@Composable
fun CardStackScreen() {
    var people by remember { 
        mutableStateOf((1..10).map { Person(it, "Profile #$it", Color(kotlin.random.Random.nextInt())) }) 
    }
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        people.take(2).reversed().forEachIndexed { index, person ->
            SwipeableCard(
                person = person,
                isTopCard = index == 1 || people.size == 1,
                onSwiped = { 
                    people = people.drop(1)
                }
            )
        }
        
        if (people.isEmpty()) {
            Text("No more profiles!", fontSize = 20.sp, color = Color.Gray)
        }
    }
}

@Composable
fun SwipeableCard(
    person: Person,
    isTopCard: Boolean,
    onSwiped: () -> Unit
) {
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    
    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .graphicsLayer {
                rotationZ = offsetX.value / 20
                scaleX = if (isTopCard) 1f else 0.9f
                scaleY = if (isTopCard) 1f else 0.9f
            }
            .width(320.dp)
            .height(500.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(person.color.copy(alpha = 0.8f))
            .pointerInput(isTopCard) {
                if (!isTopCard) return@pointerInput
                detectDragGestures(
                    onDragEnd = {
                        if (kotlin.math.abs(offsetX.value) > 400) {
                            scope.launch {
                                offsetX.animateTo(if (offsetX.value > 0) 1000f else -1000f)
                                onSwiped()
                            }
                        } else {
                            scope.launch {
                                launch { offsetX.animateTo(0f, spring(Spring.DampingRatioMediumBouncy)) }
                                launch { offsetY.animateTo(0f, spring(Spring.DampingRatioMediumBouncy)) }
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount.x)
                            offsetY.snapTo(offsetY.value + dragAmount.y)
                        }
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier.align(Alignment.BottomStart).padding(24.dp)
        ) {
            Text(person.name, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text("Swipe left to skip, right to like", color = Color.White.copy(alpha = 0.8f))
        }

        // Like/Skip Indicators
        val alpha = (kotlin.math.abs(offsetX.value) / 200f).coerceIn(0f, 1f)
        if (offsetX.value > 0) {
            Icon(
                Icons.Default.Favorite, null,
                tint = Color.Green.copy(alpha = alpha),
                modifier = Modifier.size(100.dp).align(Alignment.Center)
            )
        } else if (offsetX.value < 0) {
            Icon(
                Icons.Default.Close, null,
                tint = Color.Red.copy(alpha = alpha),
                modifier = Modifier.size(100.dp).align(Alignment.Center)
            )
        }
    }
}
