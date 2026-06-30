package com.example.masonlee

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScreen() {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { showBottomSheet = true }) {
            Text("설정창 열기 (Bottom Sheet)")
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                // 시트 내부 컨텐츠
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 64.dp, start = 32.dp, end = 32.dp)
                ) {
                    Text("상세 설정", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(16.dp))
                    ListItem(
                        headlineContent = { Text("알림 설정") },
                        trailingContent = { Switch(checked = true, onCheckedChange = {}) }
                    )
                    ListItem(
                        headlineContent = { Text("다크 모드") },
                        trailingContent = { Switch(checked = false, onCheckedChange = {}) }
                    )
                    Button(
                        onClick = { showBottomSheet = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("닫기")
                    }
                }
            }
        }
    }
}
