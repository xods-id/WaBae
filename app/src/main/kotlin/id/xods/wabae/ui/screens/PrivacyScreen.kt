package id.xods.wabae.ui.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.DoneAll
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.RemoveRedEye
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.xods.wabae.data.PrefsManager

@Composable
fun PrivacyScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // States
    var ghostMode by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_GHOST_MODE)) }
    var freezeLastSeen by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_FREEZE_LAST_SEEN)) }
    var hideOnline by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_HIDE_ONLINE)) }
    var hideRead by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_HIDE_READ)) }
    var blueOnReply by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_BLUE_ON_REPLY)) }
    var hideDelivered by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_HIDE_DELIVERED)) }
    var hideTyping by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_HIDE_TYPING)) }
    var hideRecording by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_HIDE_RECORDING)) }
    var ghostStatusView by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_GHOST_STATUS_VIEW)) }
    var antiViewOnce by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_ANTI_VIEW_ONCE)) }
    var antiDelete by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_ANTI_DELETE, true)) }
    var blockUnknownCalls by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_BLOCK_UNKNOWN_CALLS)) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card: Ghost Mode (Mode Siluman)
        item {
            GhostModeHeroCard(
                enabled = ghostMode,
                onCheckedChange = { isChecked ->
                    ghostMode = isChecked
                    PrefsManager.setLocalBoolean(context, PrefsManager.KEY_GHOST_MODE, isChecked)
                }
            )
        }

        // Section: Kehadiran & Status
        item {
            BentoSectionCard(title = "Kehadiran & Status") {
                PrivacyToggleItem(
                    title = "Bekukan Terakhir Dilihat",
                    subtitle = "Waktu terakhir online terkunci di jam terakhir kamu buka WA",
                    icon = Icons.Rounded.Lock,
                    checked = freezeLastSeen,
                    onCheckedChange = {
                        freezeLastSeen = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_FREEZE_LAST_SEEN, it)
                    }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                PrivacyToggleItem(
                    title = "Sembunyikan Status Online",
                    subtitle = "Orang lain tidak akan melihat tulisan online saat kamu buka WA",
                    icon = Icons.Rounded.VisibilityOff,
                    checked = hideOnline,
                    onCheckedChange = {
                        hideOnline = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_HIDE_ONLINE, it)
                    }
                )
            }
        }

        // Section: Tanda Baca & Interaksi Chat
        item {
            BentoSectionCard(title = "Tanda Baca & Chat") {
                PrivacyToggleItem(
                    title = "Centang Biru Pas Dibalas",
                    subtitle = "Centang tetap abu-abu sampai kamu mengetik & mengirim balasan",
                    icon = Icons.Rounded.DoneAll,
                    checked = blueOnReply,
                    onCheckedChange = {
                        blueOnReply = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_BLUE_ON_REPLY, it)
                    }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                PrivacyToggleItem(
                    title = "Sembunyikan Centang Biru",
                    subtitle = "Jangan kirim tanda pesan sudah dibaca ke pengirim",
                    icon = Icons.Rounded.Check,
                    checked = hideRead,
                    onCheckedChange = {
                        hideRead = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_HIDE_READ, it)
                    }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                PrivacyToggleItem(
                    title = "Sembunyikan Centang Dua",
                    subtitle = "Pesan masuk tetap bertanda centang satu sampai kamu buka",
                    icon = Icons.Rounded.DoneAll,
                    checked = hideDelivered,
                    onCheckedChange = {
                        hideDelivered = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_HIDE_DELIVERED, it)
                    }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                PrivacyToggleItem(
                    title = "Sembunyikan Sedang Mengetik",
                    subtitle = "Hilangkan tulisan 'sedang mengetik...' saat kamu ngetik pesan",
                    icon = Icons.Rounded.Edit,
                    checked = hideTyping,
                    onCheckedChange = {
                        hideTyping = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_HIDE_TYPING, it)
                    }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                PrivacyToggleItem(
                    title = "Sembunyikan Merekam Suara",
                    subtitle = "Hilangkan indikator 'merekam audio...' saat kamu rekam VN",
                    icon = Icons.Rounded.Mic,
                    checked = hideRecording,
                    onCheckedChange = {
                        hideRecording = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_HIDE_RECORDING, it)
                    }
                )
            }
        }

        // Section: Cerita & Media
        item {
            BentoSectionCard(title = "Cerita & Media") {
                PrivacyToggleItem(
                    title = "Liat Status Tanpa Ketahuan",
                    subtitle = "Nama kamu tidak akan muncul di daftar orang yang melihat cerita",
                    icon = Icons.Rounded.RemoveRedEye,
                    checked = ghostStatusView,
                    onCheckedChange = {
                        ghostStatusView = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_GHOST_STATUS_VIEW, it)
                    }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                PrivacyToggleItem(
                    title = "Bypass Sekali Lihat (View Once)",
                    subtitle = "Foto dan video 1x bisa dibuka berulang kali tanpa batas",
                    icon = Icons.Rounded.Security,
                    checked = antiViewOnce,
                    onCheckedChange = {
                        antiViewOnce = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_ANTI_VIEW_ONCE, it)
                    }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                PrivacyToggleItem(
                    title = "Anti Hapus Pesan",
                    subtitle = "Pesan yang ditarik oleh pengirim tetap dapat kamu baca",
                    icon = Icons.Rounded.Lock,
                    checked = antiDelete,
                    onCheckedChange = {
                        antiDelete = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_ANTI_DELETE, it)
                    }
                )
            }
        }

        // Section: Panggilan
        item {
            BentoSectionCard(title = "Keamanan Panggilan") {
                PrivacyToggleItem(
                    title = "Blokir Panggilan Tak Dikenal",
                    subtitle = "Tolak otomatis panggilan dari nomor yang belum tersimpan di kontak",
                    icon = Icons.Rounded.Call,
                    checked = blockUnknownCalls,
                    onCheckedChange = {
                        blockUnknownCalls = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_BLOCK_UNKNOWN_CALLS, it)
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun GhostModeHeroCard(
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (enabled) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Rounded.Security,
                                contentDescription = null,
                                tint = if (enabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Column {
                        Text(
                            text = "Mode Siluman (Ghost)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (enabled) "Aktif: Privasi Penuh" else "Nonaktif",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                        )
                    }
                }

                Switch(
                    checked = enabled,
                    onCheckedChange = onCheckedChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                        checkedTrackColor = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Aktifkan untuk menjadi tak terlihat: sembunyikan status online, freeze last seen, dan liat status tanpa jejak secara serentak.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun BentoSectionCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 4.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
private fun PrivacyToggleItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (checked) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                } else {
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                },
                modifier = Modifier.size(38.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.size(8.dp))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
