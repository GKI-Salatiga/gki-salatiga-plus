/**
 * AGPL-3.0-licensed
 * Copyright (C) GKI Salatiga 2024
 * Written by Samarthya Lykamanuella (github.com/groaking)
 *
 * ---
 * Attempts to download the latest JSON file from online source and update the app's data.
 */

package org.gkisalatiga.plus.services

import android.content.Context
import org.gkisalatiga.plus.db.Main
import org.gkisalatiga.plus.db.Gallery
import org.gkisalatiga.plus.db.GalleryCompanion
import org.gkisalatiga.plus.db.MainCompanion
import org.gkisalatiga.plus.db.Modules
import org.gkisalatiga.plus.db.ModulesCompanion
import org.gkisalatiga.plus.db.Static
import org.gkisalatiga.plus.db.StaticCompanion
import org.gkisalatiga.plus.lib.Downloader
import org.gkisalatiga.plus.lib.Logger
import org.gkisalatiga.plus.lib.LoggerType
import org.json.JSONObject
import java.io.InputStream
import java.net.UnknownHostException
import java.util.concurrent.Executors

class DataUpdater(private val ctx: Context) {

    companion object {
        // The location of the remote feeds.json file to check for updates.
        private const val FEEDS_JSON_SOURCE = "https://raw.githubusercontent.com/gkisalatiga/gkisplus-data/main/v2/data/feeds.json"
    }

    private fun getLastGalleryUpdate() : Int {
        return Gallery(ctx).getGalleryMetadata().getInt("last-update")
    }

    private fun getLastMainDataUpdate() : Int {
        return Main(ctx).getMainMetadata().getInt("last-update")
    }

    private fun getLastModulesDataUpdate() : Int {
        return Modules(ctx).getModulesMetadata().getInt("last-update")
    }

    private fun getLastStaticUpdate() : Int {
        return Static(ctx).getStaticMetadata().getInt("last-update")
    }

    /**
     * Get the cloud-stored feed information
     * in order to determine whether we should update the data or not.
     */
    private fun getMostRecentFeeds() : JSONObject {
        Logger.logUpdate({}, "Fetching the update feeds ...")
        val streamInput: InputStream = java.net.URL(FEEDS_JSON_SOURCE).openStream()
        val inputAsString: String = streamInput.bufferedReader().use { it.readText() }

        // Dumps the JSON string, and compare them with the currently cached JSON last update values.
        Logger.logDump({}, inputAsString)
        Logger.logTest({}, "Last Main Data Update: ${getLastMainDataUpdate()}")
        Logger.logTest({}, "Last Modules Data Update: ${getLastModulesDataUpdate()}")
        Logger.logTest({}, "Last Gallery Data Update: ${getLastGalleryUpdate()}")
        Logger.logTest({}, "Last Static Data Update: ${getLastStaticUpdate()}")

        // Returns the feeds data.
        return JSONObject(inputAsString).getJSONObject("feeds")
    }

    fun updateData() {
        // Upon successful data download, we manage the app's internal variable storage
        // according to the downloaded JSON file's schema.
        // We also make any appropriate settings accordingly.
        // ---
        // This is all done in a multi-thread so that we do not interrupt the main GUI.
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            Logger.logUpdate({}, "Attempting to update and assign the latest JSON files ...")

            try {
                // Retrieving the feed JSON file to check if update is even necessary.
                val feedJSONObject = getMostRecentFeeds()

                // Set the flag to "false" to signal that we need to have the new data now,
                // then make the attempt to download the JSON files.
                if (getLastMainDataUpdate() < feedJSONObject.getInt("last-main-update")) {
                    Logger.logUpdate({}, "Updating main data ...", LoggerType.INFO)
                    MainCompanion.mutableIsDataInitialized.value = false
                    Downloader(ctx).initMainData(autoReloadGlobalData = true)
                } else {
                    Logger.logUpdate({}, "Main data is up-to-date!", LoggerType.INFO)
                }

                if (getLastModulesDataUpdate() < feedJSONObject.getInt("last-modules-update")) {
                    Logger.logUpdate({}, "Updating modules data ...", LoggerType.INFO)
                    ModulesCompanion.mutableIsDataInitialized.value = false
                    Downloader(ctx).initModulesData(autoReloadGlobalData = true)
                } else {
                    Logger.logUpdate({}, "Modules data is up-to-date!", LoggerType.INFO)
                }

                if (getLastGalleryUpdate() < feedJSONObject.getInt("last-gallery-update")) {
                    Logger.logUpdate({}, "Updating gallery data ...", LoggerType.INFO)
                    GalleryCompanion.mutableIsDataInitialized.value = false
                    Downloader(ctx).initGalleryData(autoReloadGlobalData = true)
                } else {
                    Logger.logUpdate({}, "Gallery data is up-to-date!", LoggerType.INFO)
                }

                if (getLastStaticUpdate() < feedJSONObject.getInt("last-static-update")) {
                    Logger.logUpdate({}, "Updating static data ...", LoggerType.INFO)
                    StaticCompanion.mutableIsDataInitialized.value = false
                    Downloader(ctx).initStaticData(autoReloadGlobalData = true)
                } else {
                    Logger.logUpdate({}, "Static data is up-to-date!", LoggerType.INFO)
                }

            } catch (e: UnknownHostException) {
                Logger.logTest({}, "Network error! Cannot retrieve the latest feeds JSON file data!", LoggerType.ERROR)
            }

            // Assign the JSON data globally.
            MainCompanion.jsonRoot = Main(ctx).getMainData()
            ModulesCompanion.jsonRoot = Modules(ctx).getModulesData()
            GalleryCompanion.jsonRoot = Gallery(ctx).getGalleryData()
            StaticCompanion.jsonRoot = Static(ctx).getStaticData()

            // End the thread.
            executor.shutdown()

        }  // --- end of executor.execute()
    }  // --- end of fun updateData()

}