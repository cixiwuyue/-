package cn.shicang.app.ui.v410

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/**
 * UI foundation for Manye 4.1.0.
 *
 * This file is intentionally isolated from app-specific repositories and navigation.
 * Wire callbacks to the existing ViewModels; do not replace existing persistence logic.
 */

enum class ArchiveType(val label: String) {
    ALL("全部"), IMAGE("图片"), VIDEO("视频"), FILE("文件")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactArchiveFilters(
    query: String,
    onQueryChange: (String) -> Unit,
    selectedType: ArchiveType,
    onTypeChange: (ArchiveType) -> Unit,
    onApplyAdvancedFilters: (time: String, tags: String, collection: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var sheetVisible by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.weight(1f),
                singleLine = true,
                shape = RoundedCornerShape(18.dp),
                placeholder = { Text("搜索档案") }
            )
            TextButton(onClick = { sheetVisible = true }) {
                Text("筛选")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ArchiveType.entries.forEach { type ->
                FilterChip(
                    selected = selectedType == type,
                    onClick = { onTypeChange(type) },
                    label = { Text(type.label) }
                )
            }
        }
    }

    if (sheetVisible) {
        AdvancedArchiveFilterSheet(
            onDismiss = { sheetVisible = false },
            onApply = { time, tags, collection ->
                onApplyAdvancedFilters(time, tags, collection)
                sheetVisible = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdvancedArchiveFilterSheet(
    onDismiss: () -> Unit,
    onApply: (String, String, String) -> Unit
) {
    var time by rememberSaveable { mutableStateOf("") }
    var tags by rememberSaveable { mutableStateOf("") }
    var collection by rememberSaveable { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("高级筛选", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("时间范围") },
                placeholder = { Text("例如：最近 30 天") },
                singleLine = true
            )
            OutlinedTextField(
                value = tags,
                onValueChange = { tags = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("标签") },
                placeholder = { Text("多个标签可用逗号分隔") },
                singleLine = true
            )
            OutlinedTextField(
                value = collection,
                onValueChange = { collection = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("集合") },
                singleLine = true
            )
            Button(
                onClick = { onApply(time.trim(), tags.trim(), collection.trim()) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("应用筛选")
            }
        }
    }
}

/**
 * Keep the save action visible above the IME. The editor receives the remaining height
 * and has a minimum usable height; BasicTextField manages its own multiline scrolling.
 *
 * The host Activity should use adjustResize (or equivalent edge-to-edge inset handling)
 * and must not apply imePadding twice at both parent and child levels.
 */
@Composable
fun DerivativeNoteEditorLayout(
    title: String,
    onTitleChange: (String) -> Unit,
    body: String,
    onBodyChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text("新建衍生笔记", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("标题") },
            singleLine = true
        )
        Spacer(Modifier.height(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .heightIn(min = 160.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(14.dp)) {
                if (body.isEmpty()) {
                    Text(
                        "记录此刻的想法…",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                BasicTextField(
                    value = body,
                    onValueChange = onBodyChange,
                    modifier = Modifier.fillMaxSize(),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("保存笔记")
        }
    }
}

data class DuplicateArchiveFile(
    val id: String,
    val name: String,
    val sizeLabel: String,
    val mimeType: String
)

/**
 * thumbnail is supplied by the app layer (Coil for images, extracted video frame for
 * videos, and a file-type icon for documents). This keeps storage/media dependencies
 * out of this reusable UI layer.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DuplicateGroupSheet(
    files: List<DuplicateArchiveFile>,
    onDismiss: () -> Unit,
    onOpenArchive: (String) -> Unit,
    thumbnail: @Composable (DuplicateArchiveFile) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFile by remember { mutableStateOf<DuplicateArchiveFile?>(null) }

    BackHandler(enabled = selectedFile != null) {
        selectedFile = null
    }

    ModalBottomSheet(
        onDismissRequest = {
            if (selectedFile != null) selectedFile = null else onDismiss()
        },
        modifier = modifier
    ) {
        val detail = selectedFile
        if (detail == null) {
            Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Text("重复文件组", style = MaterialTheme.typography.titleLarge)
                LazyColumn {
                    items(files, key = { it.id }) { file ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedFile = file }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            ) {
                                thumbnail(file)
                            }
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(
                                    file.name,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    file.sizeLabel,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            TextButton(onClick = { selectedFile = file }) {
                                Text("查看档案")
                            }
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(onClick = { selectedFile = null }) {
                        Text("‹ 返回")
                    }
                    Text("档案详情", style = MaterialTheme.typography.titleLarge)
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 180.dp)
                        .clip(RoundedCornerShape(14.dp))
                ) {
                    thumbnail(detail)
                }
                Spacer(Modifier.height(12.dp))
                Text(detail.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    detail.sizeLabel,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { onOpenArchive(detail.id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("打开完整档案")
                }
            }
        }
    }
}
