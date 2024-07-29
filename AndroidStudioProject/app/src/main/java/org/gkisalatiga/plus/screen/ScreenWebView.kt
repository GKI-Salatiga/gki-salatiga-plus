/**
 * AGPL-3.0-licensed
 * Copyright (C) GKI Salatiga 2024
 * Written by Samarthya Lykamanuella (github.com/groaking)
 *
 * ---
 * Display a composable "web view" and load the URL passed by the navigation parameter.
 */

package org.gkisalatiga.plus.screen

import android.annotation.SuppressLint
import android.content.ClipData
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import org.gkisalatiga.plus.R

import org.gkisalatiga.plus.global.GlobalSchema
import org.gkisalatiga.plus.lib.StringFormatter

class ScreenWebView() : ComponentActivity() {

    // The trigger to open an URL in an external browser.
    private var doTriggerBrowserOpen = mutableStateOf(false)

    // Controls, from an outside composable, whether to display the link confirmation dialog.
    private var showLinkConfirmationDialog = mutableStateOf(false)

    @Composable
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    public fun getComposable() {
        Scaffold (
            topBar = { this.getTopBar() }
                ) {

            // Display the necessary content.
            Box ( Modifier.padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()) ) {
                getMainContent()
            }
        }

        // Display the external link open confirm dialog.
        getLinkConfirmationDialog()

        // Ensure that when we are at the first screen upon clicking "back",
        // the app is exited instead of continuing to navigate back to the previous screens.
        // SOURCE: https://stackoverflow.com/a/69151539
        BackHandler {
            GlobalSchema.pushScreen.value = GlobalSchema.popBackScreen.value
        }

        // Handles opening URLs in external browser.
        key(doTriggerBrowserOpen.value) {
            if (doTriggerBrowserOpen.value) {
                // Opens in an external browser.
                // SOURCE: https://stackoverflow.com/a/69103918
                LocalUriHandler.current.openUri(GlobalSchema.webViewTargetURL)

                doTriggerBrowserOpen.value = false
            }
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    private fun getMainContent() {
        // Declare a string that contains a url.
        val destURL = GlobalSchema.webViewTargetURL

        /* Displaying the web view.
         * SOURCE: https://medium.com/@kevinnzou/using-webview-in-jetpack-compose-bbf5991cfd14 */
        // Adding a WebView inside AndroidView with layout as full screen
        AndroidView(factory = {
            WebView(it).apply {
                val wv = this
                wv.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                // Custom WebViewClient to disable arbitrary opening an external website.
                // SOURCE: https://stackoverflow.com/a/62166792
                wv.webViewClient = object : WebViewClient() {

                    // The filter JavaScript command.
                    val jsBody = """
                            /* Hides navigations in YKB. */
                            document.getElementsByClassName('siteinfo-footer')[0].style.display='none';
                            document.getElementsByClassName('navbar-header')[0].style.display='none';
                            document.getElementsByClassName('rightbar-devotion')[0].style.display='none';
                            document.getElementById('multiple-ajax-calendar-2').style.display='none';
                            document.getElementById('header').style.display='none';
                        """.trimIndent()

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        url: String?
                    ): Boolean {
                        // Prevents redirection from Google Drive into Google login page.
                        Log.d("Groaker-Test", "Redirection URL: ${url.toString()}")
                        if (url!!.startsWith("https://drive.google.com")) {
                            wv.loadUrl(StringFormatter().getGoogleDrivePreview(url!!))
                        }
                        return false
                    }

                    // Evaluating javascript.
                    // SOURCE: https://stackoverflow.com/a/51822916
                    override fun onPageFinished(view: WebView?, url: String?) {
                        wv.loadUrl("javascript:(function() { $jsBody })()")
                    }

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        wv.loadUrl("javascript:(function() { $jsBody })()")
                    }

                    override fun onLoadResource(view: WebView?, url: String?) {
                        wv.loadUrl("javascript:(function() { $jsBody })()")
                    }

                }

                // Disable text selection and haptic feedback caused by long press.
                // This also disables copy-pasting of HTML text.
                // SOURCE: https://stackoverflow.com/a/12793740
                wv.setOnLongClickListener(OnLongClickListener { true })
                wv.isLongClickable = false
                wv.isHapticFeedbackEnabled = false

                // Enables JavaScript.
                // SOURCE: https://stackoverflow.com/a/69373543
                wv.settings.javaScriptEnabled = true

                // Enable pinching and zooming.
                // SOURCE: https://stackoverflow.com/a/7172165
                wv.settings.builtInZoomControls = true
            }
        }, update = {
            it.loadUrl(destURL)
        })
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun getTopBar() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            title = {
                Text(
                    GlobalSchema.webViewTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    GlobalSchema.pushScreen.value = GlobalSchema.popBackScreen.value
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = ""
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    showLinkConfirmationDialog.value = true
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.OpenInNew,
                        contentDescription = ""
                    )
                }
            },
            scrollBehavior = scrollBehavior
        )
    }

    /**
     * This function displays the confirmation dialog that asks the user
     * whether the user wants to proceed opening a certain link.
     * SOURCE: https://www.composables.com/tutorials/dialogs
     * SOURCE: https://developer.android.com/develop/ui/compose/components/dialog
     */
    @Composable
    private fun getLinkConfirmationDialog() {
        val ctx = LocalContext.current
        val notificationText = stringResource(R.string.webview_visit_link_link_copied)
        if (showLinkConfirmationDialog.value) {
            AlertDialog(
                onDismissRequest = { showLinkConfirmationDialog.value = false },
                title = { Text(stringResource(R.string.webview_visit_link_confirmation_title), fontWeight = FontWeight.Bold, fontSize = 24.sp) },
                text = {
                    Column {
                        Text(stringResource(R.string.webview_visit_link_confirmation_subtitle) )
                        Surface (modifier = Modifier.fillMaxWidth(), color = Color.Transparent, onClick = {
                            // Attempt to copy text to clipboard.
                            // SOURCE: https://www.geeksforgeeks.org/clipboard-in-android/
                            val clipData = ClipData.newPlainText("text", GlobalSchema.webViewTargetURL)
                            GlobalSchema.clipManager!!.setPrimaryClip(clipData)

                            Toast.makeText(ctx, notificationText, Toast.LENGTH_SHORT).show()
                        }) {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = GlobalSchema.webViewTargetURL,
                                onValueChange = { /* NOTHING */ },
                                label = { Text("-") },
                                enabled = false,
                            )
                        }
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLinkConfirmationDialog.value = false }) {
                        Text(stringResource(R.string.webview_visit_link_cancel_btn).uppercase())
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        showLinkConfirmationDialog.value = false
                        doTriggerBrowserOpen.value = true
                    }) {
                        Text(stringResource(R.string.webview_visit_link_proceed_btn).uppercase())
                    }
                }
            )
        }
    }

}