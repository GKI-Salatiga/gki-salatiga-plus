/**
 * AGPL-3.0-licensed
 * Copyright (C) GKI Salatiga 2024
 * Written by Samarthya Lykamanuella (github.com/groaking)
 * ---
 *
 * Implements a global declaration of variables, which can be accessed across classes.
 * SOURCE: https://tutorial.eyehunts.com/android/declare-android-global-variable-kotlin-example/
 * SOURCE: https://stackoverflow.com/a/52844621
 */

package org.gkisalatiga.plus.global

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.mutableStateOf
import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.ANNOTATION_CLASS
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.CONSTRUCTOR
import kotlin.annotation.AnnotationTarget.EXPRESSION
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.annotation.AnnotationTarget.FILE
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.LOCAL_VARIABLE
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.PROPERTY_GETTER
import kotlin.annotation.AnnotationTarget.PROPERTY_SETTER
import kotlin.annotation.AnnotationTarget.TYPE
import kotlin.annotation.AnnotationTarget.TYPEALIAS
import kotlin.annotation.AnnotationTarget.TYPE_PARAMETER
import kotlin.annotation.AnnotationTarget.VALUE_PARAMETER

class GlobalCompanion : Application() {

    // Initializing the data schema of the app that will be shared across composables
    // and that will course the navigation of screens.
    companion object {

        /* ------------------------------------------------------------------------------------ */
        /* App-wide pre-defined values and URLs. */
        const val ABOUT_CONTACT_MAIL = "dev.gkisalatiga@gmail.com"
        const val APP_CHANGELOG_URL = "https://github.com/gkisalatiga/gkisalatiga-foss/blob/main/CHANGELOG.md"
        const val APP_FULL_LICENSE = "https://github.com/gkisalatiga/gkisalatiga-foss/blob/main/LICENSE"
        const val APP_SOURCE_CODE_URL = "https://github.com/gkisalatiga/gkisalatiga-foss"

        /* ------------------------------------------------------------------------------------ */
        /* Debugging toggles that can be set before app builds. */

        // Whether to enable the easter egg feature of the app and display it to the user.
        const val DEBUG_ENABLE_EASTER_EGG = true

        // Whether to display the debugger's toast.
        const val DEBUG_ENABLE_TOAST = true

        // Whether to display the debugger's logcat logging.
        const val DEBUG_ENABLE_LOG_CAT = true
        const val DEBUG_ENABLE_LOG_CAT_BOOT = true
        const val DEBUG_ENABLE_LOG_CAT_CONN_TEST = true
        const val DEBUG_ENABLE_LOG_CAT_DOWNLOADER = true
        const val DEBUG_ENABLE_LOG_CAT_DEEP_LINK = true
        const val DEBUG_ENABLE_LOG_CAT_DUMP = true
        const val DEBUG_ENABLE_LOG_CAT_INIT = true
        const val DEBUG_ENABLE_LOG_CAT_LOCAL_STORAGE = true
        const val DEBUG_ENABLE_LOG_CAT_PDF = true
        const val DEBUG_ENABLE_LOG_CAT_PERSISTENT_LOGGER = true
        const val DEBUG_ENABLE_LOG_CAT_PREFERENCES = true
        const val DEBUG_ENABLE_LOG_CAT_RAPID_TEST = true
        const val DEBUG_ENABLE_LOG_CAT_TEST = true
        const val DEBUG_ENABLE_LOG_CAT_UPDATER = true
        const val DEBUG_ENABLE_LOG_CAT_WORKER = true

        // Whether to display extraneous information in various screens.
        const val DEBUG_SHOW_INFO_PDF_LOCAL_PATH_INFO = true

        // Whether to hide the splash screen.
        const val DEBUG_DISABLE_SPLASH_SCREEN = false

        /* ------------------------------------------------------------------------------------ */
        /* Global states of the app. */

        // The status of internet connection.
        var isConnectedToInternet = mutableStateOf(false)

        // Current app's bars (both status bar and navigation bar) state of visibility.
        val isPhoneBarsVisible = mutableStateOf(true)

        // Current app's screen orientation.
        val isPortraitMode = mutableStateOf(true)

        // Whether the app is running in background.
        val isRunningInBackground = mutableStateOf(false)

    }
}