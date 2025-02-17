package com.dergoogler.mmrl.utils.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import androidx.core.app.ShareCompat

val Context.tmpDir
    get() = cacheDir.resolve("tmp")
        .apply {
            if (!exists()) mkdirs()
        }

fun Context.openUrl(url: String) {
    Intent.parseUri(url, Intent.URI_INTENT_SCHEME).apply {
        startActivity(this)
    }
}

// TODO: add colors from theme
fun Context.launchCustomTab(url: String) {
    val colorSchemeParams = CustomTabColorSchemeParams.Builder()
        .build()

    val customTabsIntent = CustomTabsIntent.Builder()
        .setDefaultColorSchemeParams(colorSchemeParams)
        .build()

    customTabsIntent.launchUrl(this, Uri.parse(url))
}

@get:Composable
val Context.currentComposer
    get(): Composer? {
        return try {
            val composerField = Class.forName("androidx.compose.runtime.ComposerKt")
                .getDeclaredField("currentComposer")
            composerField.isAccessible = true
            composerField.get(null) as? Composer
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

fun Context.shareText(text: String) {
    ShareCompat.IntentBuilder(this)
        .setType("text/plain")
        .setText(text)
        .startChooser()
}