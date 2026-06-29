package com.example.masonlee

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun ExpandingCardScreen() {
    val items = remember { (1..10).toList() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Expanding Cards",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { id ->
                ExpandingCard(id)
            }
        }
    }
}

@Composable
fun ExpandingCard(id: Int) {
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
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF03DAC5).copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Item #$id",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            if (expanded) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "이 카드는 클릭 시 확장되는 레이아웃입니다. " +
                            "상세 내용을 여기에 보여줄 수 있으며, " +
                            "애니메이션을 통해 부드럽게 펼쳐지는 효과를 줍니다. " +
                            "Spring 애니메이션이 적용되어 튕기는 듯한 느낌이 납니다.",
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                )
                Text(
                    text = "\n다시 클릭하면 줄어듭니다.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            } else {
                Text(
                    text = "클릭해서 자세히 보기...",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
