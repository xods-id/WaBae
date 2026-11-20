package com.wmods.wppenhacer.xposed.core

import com.wmods.wppenhacer.xposed.features.general.AntiRevoke
import com.wmods.wppenhacer.xposed.features.general.CallType
import com.wmods.wppenhacer.xposed.features.general.PropsHook
import com.wmods.wppenhacer.xposed.features.media.DownloadProfile
import com.wmods.wppenhacer.xposed.features.media.DownloadViewOnce
import com.wmods.wppenhacer.xposed.features.media.MediaPreview
import com.wmods.wppenhacer.xposed.features.media.MediaQuality
import com.wmods.wppenhacer.xposed.features.media.StatusDownload
import com.wmods.wppenhacer.xposed.features.others.CopyStatus
import com.wmods.wppenhacer.xposed.features.others.DebugFeature
import com.wmods.wppenhacer.xposed.features.others.MinorFixes
import com.wmods.wppenhacer.xposed.features.privacy.AntiWa
import com.wmods.wppenhacer.xposed.features.privacy.CallPrivacy
import com.wmods.wppenhacer.xposed.features.privacy.DndMode
import com.wmods.wppenhacer.xposed.features.privacy.FreezeLastSeen
import com.wmods.wppenhacer.xposed.features.privacy.HideChat
import com.wmods.wppenhacer.xposed.features.privacy.HideSeen
import com.wmods.wppenhacer.xposed.features.privacy.LockedChatsEnhancer
import com.wmods.wppenhacer.xposed.features.privacy.TagMessage
import com.wmods.wppenhacer.xposed.features.privacy.TypingPrivacy
import com.wmods.wppenhacer.xposed.features.privacy.ViewOnce
import com.wmods.wppenhacer.xposed.features.providers.ContextMenuActionProvider
import com.wmods.wppenhacer.xposed.features.providers.MenuStatusProvider

/** Privacy & Media features installed by [FeatureLoader], in load order. */
internal object FeatureRegistry {
    val features: List<Class<out Feature>> = listOf(
        PropsHook::class.java,
        DebugFeature::class.java,
        MinorFixes::class.java,
        MenuStatusProvider::class.java,
        ContextMenuActionProvider::class.java,

        // Privacy
        AntiRevoke::class.java,
        CallPrivacy::class.java,
        CallType::class.java,
        DndMode::class.java,
        FreezeLastSeen::class.java,
        TypingPrivacy::class.java,
        HideChat::class.java,
        HideSeen::class.java,
        TagMessage::class.java,
        AntiWa::class.java,
        LockedChatsEnhancer::class.java,
        ViewOnce::class.java,

        // Media
        StatusDownload::class.java,
        DownloadViewOnce::class.java,
        DownloadProfile::class.java,
        MediaQuality::class.java,
        MediaPreview::class.java,
        CopyStatus::class.java
    )
}
