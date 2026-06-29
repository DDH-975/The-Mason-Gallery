package com.example.masonlee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masonlee.ui.theme.MasonleeTheme
import kotlin.random.Random

enum class Screen(val title: String) {
    Masonry("Masonry"),
    Carousel("Carousel"),
    Expanding("Expanding"),
    Scroll("Scroll")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MasonleeTheme {
                var currentScreen by remember { mutableStateOf(Screen.Masonry) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            Screen.entries.forEach { screen ->
                                NavigationBarItem(
                                    selected = currentScreen == screen,
                                    onClick = { currentScreen = screen },
                                    label = { Text(screen.title) },
                                    icon = { 
                                        Text(text = screen.title.take(1), fontWeight = FontWeight.Bold)
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentScreen) {
                            Screen.Masonry -> MasonryLayout()
                            Screen.Carousel -> CarouselScreen()
                            Screen.Expanding -> ExpandingCardScreen()
                            Screen.Scroll -> ScrollAnimationScreen()
                        }
                    }
                }
            }
        }
    }
}

data class MasonryItem(
    val id: Int,
    val color: Color,
    val title: String,
    val description: String
)

@Composable
fun MasonryLayout(modifier: Modifier = Modifier) {
    val descriptions = listOf(
        "짧은 설명입니다.",
        "이 카드는 조금 더 긴 설명을 가지고 있어서 높이가 더 길어질 것입니다.",
        "메이슨리 레이아웃은 컨텐츠의 높이에 따라 자동으로 배치됩니다.",
        "Jetpack Compose!",
        "StaggeredGrid를 사용하면 핀터레스트 같은 UI를 쉽게 만들 수 있습니다."
    )

    val items = (1..20).map {
        MasonryItem(
            id = it,
            color = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1f),
            title = "Item $it",
            description = descriptions.random()
        )
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp
    ) {
        items(items) { item ->
            MasonryCard(item)
        }
    }
}

@Composable
fun MasonryCard(item: MasonryItem) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = item.color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.description,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "상세 보기 모드입니다. 메이슨리 그리드 내에서 다른 카드들이 자연스럽게 밀려나는 것을 확인해보세요.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            } else {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.description.take(20) + "...",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MasonryPreview() {
    MasonleeTheme {
        MasonryLayout()
    }
}
