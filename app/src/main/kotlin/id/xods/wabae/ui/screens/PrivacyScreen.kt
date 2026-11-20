package id.xods.wabae.ui.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.rounded.PhoneDisabled
import androidx.compose.material.icons.rounded.PhoneLocked
import androidx.compose.material.icons.rounded.RemoveRedEye
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.SignalCellularConnectedNoInternet4Bar
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.xods.wabae.data.PrefsManager

@Composable
fun PrivacyScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // State Variables
    var ghostMode by remember {
        mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_GHOST_MODE, false))
    }
    var freezeLastSeen by remember {
        mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_FREEZE_LAST_SEEN, false))
    }
    var hideOnline by remember {
        mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_HIDE_ONLINE, false))
    }
    var hideRead by remember {
        mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_HIDE_READ, false))
    }
    var blueOnReply by remember {
        mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_BLUE_ON_REPLY, false))
    }
    var hideDelivered by remember {
        mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_HIDE_DELIVERED, false))
    }
    var hideTyping by remember {
        mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_HIDE_TYPING, false))
    }
    var hideRecording by remember {
        mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_HIDE_RECORDING, false))
    }
    var ghostStatusView by remember {
        mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_GHOST_STATUS_VIEW, false))
    }
    var antiViewOnce by remember {
        mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_ANTI_VIEW_ONCE, true))
    }
    var antiDelete by remember {
        mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_ANTI_DELETE, false))
    }
    var blockCalls by remember {
        mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_BLOCK_UNKNOWN_CALLS, false))
    }
    var callRejectType by remember {
        mutableStateOf(PrefsManager.getLocalString(context, PrefsManager.KEY_CALL_REJECT_TYPE, "no_internet"))
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card: Mode Hantu (Ghost Mode)
        item {
            GhostModeHeroCard(
                enabled = ghostMode,
                onCheckedChange = {
                    ghostMode = it
                    PrefsManager.setLocalBoolean(context, PrefsManager.KEY_GHOST_MODE, it)
                }
            )
        }

        // Section: Privasi Online & Kehadiran
        item {
            BentoSectionCard(title = "Kehadiran & Status Online") {
                PrivacyToggleItem(
                    title = "Bekukan Terakhir Dilihat",
                    subtitle = "Waktu terakhir dilihat terkunci permanen di masa lalu",
                    icon = Icons.Rounded.VisibilityOff,
                    checked = freezeLastSeen,
                    onCheckedChange = {
                        freezeLastSeen = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_FREEZE_LAST_SEEN, it)
                    }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                PrivacyToggleItem(
                    title = "Sembunyikan Status Online",
                    subtitle = "Kamu tidak akan terlihat online meskipun sedang aktif",
                    icon = Icons.Rounded.Visibility,
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
                    title = "Sembunyikan Merekam Audio",
                    subtitle = "Hilangkan tulisan 'merekam audio...' saat merekam vn",
                    icon = Icons.Rounded.Mic,
                    checked = hideRecording,
                    onCheckedChange = {
                        hideRecording = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_HIDE_RECORDING, it)
                    }
                )
            }
        }

        // Section: Story & Media Proteksi
        item {
            BentoSectionCard(title = "Story & Proteksi Media") {
                PrivacyToggleItem(
                    title = "Lihat Status Tanpa Ketahuan",
                    subtitle = "Nama kamu tidak akan muncul di daftar penonton status",
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

        // Section: Keamanan Panggilan (Desain Bento Baru Khas WaBae)
        item {
            BentoSectionCard(title = "Filter & Penolakan Panggilan") {
                PrivacyToggleItem(
                    title = "Blokir Panggilan Masuk",
                    subtitle = "Otomatis tolak panggilan WhatsApp sesuai skenario yang kamu pilih",
                    icon = Icons.Rounded.Call,
                    checked = blockCalls,
                    onCheckedChange = {
                        blockCalls = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_BLOCK_UNKNOWN_CALLS, it)
                    }
                )

                AnimatedVisibility(
                    visible = blockCalls,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, start = 8.dp, end = 8.dp, bottom = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Skenario Penolakan Panggilan",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        CallScenarioCard(
                            title = "Tidak Ada Internet (Memanggil...)",
                            subtitle = "Penelpon melihat status 'Memanggil...' seolah HP kamu mati kuota",
                            icon = Icons.Rounded.WifiOff,
                            selected = callRejectType == "no_internet",
                            onClick = {
                                callRejectType = "no_internet"
                                PrefsManager.setLocalString(context, PrefsManager.KEY_CALL_REJECT_TYPE, "no_internet")
                            }
                        )

                        CallScenarioCard(
                            title = "Sedang Sibuk (Panggilan Lain)",
                            subtitle = "Penelpon menerima nada sibuk seolah kamu lagi telponan",
                            icon = Icons.Rounded.PhoneLocked,
                            selected = callRejectType == "busy",
                            onClick = {
                                callRejectType = "busy"
                                PrefsManager.setLocalString(context, PrefsManager.KEY_CALL_REJECT_TYPE, "busy")
                            }
                        )

                        CallScenarioCard(
                            title = "Panggilan Ditolak (Declined)",
                            subtitle = "Panggilan langsung terputus dengan status ditolak biasa",
                            icon = Icons.Rounded.PhoneDisabled,
                            selected = callRejectType == "declined",
                            onClick = {
                                callRejectType = "declined"
                                PrefsManager.setLocalString(context, PrefsManager.KEY_CALL_REJECT_TYPE, "declined")
                            }
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun CallScenarioCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
    val bgColor = if (selected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f) else MaterialTheme.colorScheme.surface

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = bgColor,
        border = BorderStroke(if (selected) 2.dp else 1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                modifier = Modifier.size(38.dp),
                shape = CircleShape,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            RadioButton(
                selected = selected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary
                )
            )
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
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (enabled) Icons.Rounded.Security else Icons.Rounded.VisibilityOff,
                                contentDescription = null,
                                tint = if (enabled) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Column {
                        Text(
                            text = "Mode Hantu (Ghost Mode)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (enabled) "Semua pelacak privasi dinonaktifkan" else "Aktifkan privasi total instan",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Switch(
                    checked = enabled,
                    onCheckedChange = onCheckedChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    }
}

@Composable
private fun BentoSectionCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            content()
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
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )

            Column(modifier = Modifier.padding(end = 12.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
}
