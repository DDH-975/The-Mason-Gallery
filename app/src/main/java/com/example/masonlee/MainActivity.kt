package com.example.masonlee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masonlee.ui.theme.MasonleeTheme
import kotlin.random.Random

enum class LayoutType(val title: String, val description: String) {
    Home("Main Menu", "모든 UI 패턴 보기"),
    Masonry("Masonry + Expanding", "핀터레스트 스타일 + 확장 카드"),
    Carousel("Carousel Pager", "가로 슬라이드 및 포커스 애니메이션"),
    Expanding("Expanding List", "클릭 시 부드럽게 펼쳐지는 리스트"),
    ScrollAnim("Scroll Animation", "스크롤에 반응하는 아이템 등장 효과"),
    StickyHeader("Sticky Headers", "카테고리별 상단 고정 헤더"),
    BottomSheet("Bottom Sheet", "하단에서 올라오는 설정/상세창"),
    Shimmer("Shimmer Loading", "데이터 로드 중 반짝이는 스켈레톤 효과"),
    Collapsing("Collapsing Header", "스크롤 시 줄어드는 상단바"),
    Parallax("Parallax Effect", "깊이감이 느껴지는 배경 효과"),
    Swipe("Swipe Action", "리스트 아이템 스와이프 조작"),
    Bento("Bento Grid", "애플 스타일 대시보드 레이아웃"),
    CardStack("Card Stack", "틴더 스타일 스와이프 인터랙션"),
    GlassMusic("Glassmorphism", "네온 빛의 유리질 뮤직 플레이어")
}

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MasonleeTheme {
                var currentLayout by remember { mutableStateOf(LayoutType.Home) }

                // 뒤로가기 제어
                BackHandler(enabled = currentLayout != LayoutType.Home) {
                    currentLayout = LayoutType.Home
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(currentLayout.title) },
                            navigationIcon = {
                                if (currentLayout != LayoutType.Home) {
                                    IconButton(onClick = { currentLayout = LayoutType.Home }) {
                                        Text("<", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentLayout) {
                            LayoutType.Home -> MainMenu { currentLayout = it }
                            LayoutType.Masonry -> MasonryLayout()
                            LayoutType.Carousel -> CarouselScreen()
                            LayoutType.Expanding -> ExpandingCardScreen()
                            LayoutType.ScrollAnim -> ScrollAnimationScreen()
                            LayoutType.StickyHeader -> StickyHeaderScreen()
                            LayoutType.BottomSheet -> BottomSheetScreen()
                            LayoutType.Shimmer -> ShimmerScreen()
                            LayoutType.Collapsing -> CollapsingHeaderScreen()
                            LayoutType.Parallax -> ParallaxScreen()
                            LayoutType.Swipe -> SwipeActionScreen()
                            LayoutType.Bento -> BentoGridScreen()
                            LayoutType.CardStack -> CardStackScreen()
                            LayoutType.GlassMusic -> GlassmorphismScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainMenu(onSelect: (LayoutType) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(LayoutType.entries.filter { it != LayoutType.Home }) { type ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(type) },
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = type.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(text = type.description, fontSize = 14.sp, color = Color.Gray)
                    }
                    Icon(
                        modifier = Modifier.padding(end = 4.dp),
                        imageVector = Icons.Default.Build, // Icons.Default.ArrowForward 대신 기본 아이콘 사용
                        contentDescription = null
                    )
                }
            }
        }
    }
}

// --- Masonry Layout Implementation ---

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
