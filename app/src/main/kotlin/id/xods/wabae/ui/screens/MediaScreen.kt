package id.xods.wabae.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Headphones
import androidx.compose.material.icons.rounded.HighQuality
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.RemoveRedEye
import androidx.compose.material.icons.rounded.SensorsOff
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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
fun MediaScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var imageQuality by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_IMAGE_QUALITY, true)) }
    var videoQuality by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_VIDEO_QUALITY, true)) }
    var video60Fps by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_VIDEO_60FPS, true)) }
    var downloadStatus by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_DOWNLOAD_STATUS, true)) }
    var downloadViewOnce by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_DOWNLOAD_VIEW_ONCE, true)) }
    var disableProximity by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_DISABLE_PROXIMITY)) }
    var mediaPreview by remember { mutableStateOf(PrefsManager.getLocalBoolean(context, PrefsManager.KEY_MEDIA_PREVIEW, true)) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section: Kualitas Media
        item {
            MediaBentoSection(title = "Kualitas Pengiriman Media") {
                MediaToggleItem(
                    title = "Foto Kualitas Penuh (HD)",
                    subtitle = "Kirim foto resolusi asli tanpa kompresi buram dari WhatsApp",
                    icon = Icons.Rounded.Image,
                    checked = imageQuality,
                    onCheckedChange = {
                        imageQuality = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_IMAGE_QUALITY, it)
                    }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                MediaToggleItem(
                    title = "Video Kualitas Asli",
                    subtitle = "Pertahankan bitrate dan resolusi video asli saat dikirim",
                    icon = Icons.Rounded.Videocam,
                    checked = videoQuality,
                    onCheckedChange = {
                        videoQuality = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_VIDEO_QUALITY, it)
                    }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                MediaToggleItem(
                    title = "Kirim Video 60 FPS",
                    subtitle = "Bypass batas frame rate agar video tetap berjalan mulus di 60 FPS",
                    icon = Icons.Rounded.HighQuality,
                    checked = video60Fps,
                    onCheckedChange = {
                        video60Fps = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_VIDEO_60FPS, it)
                    }
                )
            }
        }

        // Section: Pengunduh Media
        item {
            MediaBentoSection(title = "Pengunduh Media") {
                MediaToggleItem(
                    title = "Download Cerita / Status",
                    subtitle = "Tampilkan tombol simpan langsung di tampilan cerita teman",
                    icon = Icons.Rounded.Download,
                    checked = downloadStatus,
                    onCheckedChange = {
                        downloadStatus = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_DOWNLOAD_STATUS, it)
                    }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                MediaToggleItem(
                    title = "Download Media Sekali Lihat",
                    subtitle = "Simpan foto & video sekali lihat langsung ke memori galeri",
                    icon = Icons.Rounded.RemoveRedEye,
                    checked = downloadViewOnce,
                    onCheckedChange = {
                        downloadViewOnce = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_DOWNLOAD_VIEW_ONCE, it)
                    }
                )
            }
        }

        // Section: Audio & Sensor
        item {
            MediaBentoSection(title = "Audio & Sensor Proximity") {
                MediaToggleItem(
                    title = "Matikan Sensor Jarak (Proximity)",
                    subtitle = "Layar tidak akan mati mendadak saat memutar pesan suara / VN",
                    icon = Icons.Rounded.SensorsOff,
                    checked = disableProximity,
                    onCheckedChange = {
                        disableProximity = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_DISABLE_PROXIMITY, it)
                    }
                )
            }
        }

        // Section: Fitur Tambahan
        item {
            MediaBentoSection(title = "Pratinjau Chat") {
                MediaToggleItem(
                    title = "Pratinjau Media Langsung",
                    subtitle = "Tampilkan pratinjau media secara instan di dalam bubble obrolan",
                    icon = Icons.Rounded.Headphones,
                    checked = mediaPreview,
                    onCheckedChange = {
                        mediaPreview = it
                        PrefsManager.setLocalBoolean(context, PrefsManager.KEY_MEDIA_PREVIEW, it)
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
private fun MediaBentoSection(
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
private fun MediaToggleItem(
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
